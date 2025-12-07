package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.cursos.CursoListByProfessorDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorCreateDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorResponseDTO;
import com.fatec.itu.agendasalas.dto.professores.ProfessorUpdateDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.exceptions.EmailJaCadastradoException;
import com.fatec.itu.agendasalas.exceptions.RegistroProfessorDuplicadoException;
import com.fatec.itu.agendasalas.interfaces.UsuarioCadastravel;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.repositories.CursoRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.ProfessorRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProfessorService implements UsuarioCadastravel<ProfessorCreateDTO, ProfessorResponseDTO> {

    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PasswordEncryptService passwordEncryptService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    @Transactional
    public ProfessorResponseDTO cadastrarUsuario(ProfessorCreateDTO professorCreateDTO) throws MessagingException { 

        String login = gerarNovoLoginCasoJaExista(professorCreateDTO.login());
        
        if(usuarioService.existeEmailCadastrado(professorCreateDTO.email())){
            throw new EmailJaCadastradoException(professorCreateDTO.email());
        }

        Professor novoProfessor = new Professor(
                login,
                professorCreateDTO.email(), 
                professorCreateDTO.nome(),
                professorCreateDTO.matricula());

        Cargo cargo = cargoService.findByNome("PROFESSOR");
        
        novoProfessor.setCargo(cargo);
        novoProfessor.setSenha(passwordEncryptService.criptografarSenha(professorCreateDTO.senha()));
        professorRepository.save(novoProfessor);
        
        
        List<Disciplina> disciplinasParaAdicionar = professorCreateDTO.disciplinasId()
                    .stream()
                    .map(disciplinaService::findById)
                    .toList();

        disciplinasParaAdicionar.forEach(disciplina -> {
            disciplina.setProfessor(novoProfessor);
        });
        
        notificacaoService.notificarCadastro(novoProfessor, professorCreateDTO.senha());
        disciplinaRepository.saveAll(disciplinasParaAdicionar);
        return toResponseDTO(novoProfessor);
    }

    private String gerarNovoLoginCasoJaExista(String login){
        String baseLogin = login;
        int suffix = 1;
        while (usuarioRepository.findByLogin(login).isPresent()) {
            suffix++;
            login = baseLogin + suffix;
        }
        return login;
    }


    /********* Lista por ID *********/
    public ProfessorResponseDTO buscarPorId(Long id) {
        return toResponseDTO(professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor de " + id + " não encontrado")));
    }

    public List<ProfessorResponseDTO> listarProfessores() {
        return professorRepository.findAll().stream()
                .map(p -> new ProfessorResponseDTO(p.getId(), p.getNome(), p.getEmail(),
                        p.getRegistroProfessor(),
                        p.getCargo() != null ? p.getCargo().getId() : null))
                .toList();
    }

    public List<CursoListByProfessorDTO> listarCursosPorProfessor(Long idProfessor) {
        List<Curso> cursosEncontrados = cursoRepository.findCursosByProfessorRegistro(idProfessor);
        return cursosEncontrados.stream()
                .map(curso -> new CursoListByProfessorDTO(curso.getId(), curso.getNomeCurso(), curso.getSigla()))
                .toList();
    }

    @Transactional
    public void excluirProfessor(Long registroProfessor) {
        Professor professor = professorRepository.findByRegistroProfessor(registroProfessor)
                .orElseThrow(() ->
                    new EntityNotFoundException("Professor com Registro " + registroProfessor + " não encontrado.")
                );
        List<Disciplina> disciplinas = disciplinaRepository.findByProfessorId(professor.getId());
        for (Disciplina d : disciplinas) {
            d.setProfessor(null);
        }
        disciplinaRepository.saveAll(disciplinas);
        professorRepository.delete(professor);
    }


    private ProfessorResponseDTO toResponseDTO(Professor p) {
        return new ProfessorResponseDTO(p.getId(), p.getNome(), p.getEmail(),
                p.getRegistroProfessor(), p.getCargo() != null ? p.getCargo().getId() : null);
    }

    @Transactional
    public ProfessorResponseDTO atualizarProfessor(
        Long professorId,
        ProfessorUpdateDTO dto) {

        Professor professor = professorRepository.findById(professorId) 
                .orElseThrow(() -> new EntityNotFoundException("Professor de id: " + professorId + " não encontrado"));
        
        if (dto.nome() != null) professor.setNome(dto.nome());
        if (dto.email() != null) professor.setEmail(dto.email());
        if (dto.registroProfessor()!=null){
            if(professorRepository.existsByRegistroProfessor(dto.registroProfessor())){
                throw new RegistroProfessorDuplicadoException(professorId, dto.registroProfessor());
            }
            professor.setRegistroProfessor(dto.registroProfessor());
        }  

        if (dto.cursoParaVirarCoordenador() != null) {
            Curso curso = cursoRepository.findBySigla(dto.cursoParaVirarCoordenador())
                .orElseThrow(()-> new EntityNotFoundException("Curso de id: " + dto.cursoParaVirarCoordenador() + " não encontrado"));
            Coordenador coordenador = coordenadorRepository.findById(professor.getId())
            .orElseGet(() -> {
                Coordenador c = new Coordenador(professor.getId(), professor.getLogin(), professor.getEmail(), professor.getNome(), professor.getRegistroProfessor());
                return c;
            });
           
            //Pode ser que dê erro aqui..
            curso.setCoordenador(coordenador);
            Cargo cargo = cargoService.findByNome("COORDENADOR");
            coordenador.setCargo(cargo);
            coordenadorRepository.save(coordenador);
            cursoRepository.save(curso);
        }

        if (dto.disciplinasIds() != null) {

            List<Disciplina> disciplinasNovas = dto.disciplinasIds()
                    .stream()
                    .map(disciplinaService::findById)
                    .toList();

            Set<Long> novasIds = disciplinasNovas.stream()
                    .map(Disciplina::getId)
                    .collect(Collectors.toSet());

            List<Disciplina> disciplinasAtuais = professor.getDisciplinas();

            for(Disciplina d: disciplinasAtuais){
                if(!novasIds.contains(d.getId())){
                    d.setProfessor(null);
                    disciplinaRepository.save(d);
                }
            }

            for(Disciplina d:disciplinasNovas){
                d.setProfessor(professor);
                disciplinaRepository.save(d);
            }

            professor.setDisciplinas(disciplinaRepository.findByProfessorId(professorId));

            
        }           
         return toResponseDTO(professorRepository.save(professor));
    }    
}

   


    
