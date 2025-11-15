package com.fatec.itu.agendasalas.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationByAuxiliarDocenteDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationComRecorrenciaDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.exceptions.DisciplinaNaoEncontradaException;
import com.fatec.itu.agendasalas.exceptions.SalaNaoEncontradaException;
import com.fatec.itu.agendasalas.exceptions.UsuarioNaoEncontradoException;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class AgendamentoAulaService {

    @Autowired
    private AgendamentoAulaRepository agendamentoAulaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private JanelasHorarioRepository janelasHorarioRepository;

    @Autowired
    private RecorrenciaRepository recorrenciaRepository;

    @Autowired
    private AgendamentoConflitoService agendamentoConflitoService;

    @Transactional
    //método para a tela Agendar Sala da Matéria dos ADS
    public void criarAgendamentoAulaComRecorrencia(AgendamentoAulaCreationComRecorrenciaDTO agendamentoAulaCreationComRecorrenciaDTO){
        LocalDate dataInicial = agendamentoAulaCreationComRecorrenciaDTO.dataInicio();
        LocalDate dataFinal = agendamentoAulaCreationComRecorrenciaDTO.dataFim();
        List<Long> janelasHorarioId = agendamentoAulaCreationComRecorrenciaDTO.janelasHorarioId();
        Disciplina disciplina = disciplinaRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.disciplinaId()).orElseThrow(()->new RuntimeException("aa"));
        Sala sala = salaRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.salaId()).orElseThrow(()-> new RuntimeException("aa"));
        Recorrencia recorrencia = new Recorrencia(dataInicial, dataFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);
        Usuario usuario = usuarioRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.usuarioId()).orElseThrow(()-> new RuntimeException("AA"));
        DayOfWeek diaDaSemana = agendamentoAulaCreationComRecorrenciaDTO.diaDaSemana().toJavaDay();
        while(!dataInicial.isAfter(dataFinal)){
            if(dataInicial.getDayOfWeek()==diaDaSemana){
                for(Long janelaId:janelasHorarioId){
                    

                    JanelasHorario janelaHorario = janelasHorarioRepository.findById(janelaId).orElseThrow(()-> new RuntimeException("AA"));
                    if(agendamentoConflitoService.existeAgendamentoNoHorario(sala.getId(), dataInicial, janelaId)){
                        throw new ConflitoAoAgendarException(sala.getNome(), dataInicial, janelaHorario.getHoraInicio(), janelaHorario.getHoraFim());
                    }

                    AgendamentoAula agendamentoAula = new AgendamentoAula();
                    agendamentoAula.setUsuario(usuario);
                    agendamentoAula.setSala(sala);
                    agendamentoAula.setDisciplina(disciplina);
                    agendamentoAula.setData(dataInicial);
                    agendamentoAula.setJanelasHorario(janelaHorario);
                    agendamentoAula.preencherDiaDaSemana();
                    agendamentoAula.setIsEvento(false);
                    agendamentoAula.setRecorrencia(recorrenciaSalva);
                    agendamentoAula.setStatus("ATIVO");
                    agendamentoAulaRepository.save(agendamentoAula);

                }
            }
            dataInicial = dataInicial.plusDays(1);
        }
    }

    @Transactional
    //Esse método se refere ao Agendar Aula pelo AD
    public List<AgendamentoAulaResponseDTO> criarAgendamentoAulaByAD(AgendamentoAulaCreationByAuxiliarDocenteDTO dto) {
        
        List<AgendamentoAula> agendamentosRealizados = new ArrayList<>();
        List<AgendamentoAulaResponseDTO> listaResposta = new ArrayList<>();

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.usuarioId()));
        
        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new SalaNaoEncontradaException(dto.salaId()));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(dto.disciplinaId()));

        LocalDate data = dto.data();

        Recorrencia recorrencia = new Recorrencia(data, data);

        List<JanelasHorario> janelasHorarios = janelasHorarioRepository.findAllById(dto.janelasHorarioId());
        String solicitante = dto.solicitante();

        for(JanelasHorario janela : janelasHorarios){
            if(agendamentoConflitoService.existeAgendamentoNoHorario(sala.getId(), data, janela.getId())){
                throw new ConflitoAoAgendarException(sala.getNome(), data, janela.getHoraInicio(), janela.getHoraFim());
            }
            AgendamentoAula agendamento = new AgendamentoAula();
            agendamento.setUsuario(usuario);
            agendamento.setSala(sala);
            agendamento.setDisciplina(disciplina);
            agendamento.setData(data);
            agendamento.setJanelasHorario(janela);
            agendamento.setIsEvento(false);
            agendamento.setStatus("ATIVO");
            agendamento.setRecorrencia(recorrencia);
            agendamento.setSolicitante(solicitante);
            agendamento.preencherDiaDaSemana();
            AgendamentoAula saved = agendamentoAulaRepository.save(agendamento);
            agendamentosRealizados.add(saved);
        }

        listaResposta = agendamentosRealizados
            .stream()
            .map(this::converterParaResponseDTO)
            .collect(Collectors.toList());
        
        return listaResposta;
  
    }

    public List<AgendamentoAulaResponseDTO> listarTodos() {
        return agendamentoAulaRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoAulaResponseDTO buscarPorId(Long id) {
        AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento de aula não encontrado com ID: " + id));
        return converterParaResponseDTO(agendamento);
    }

    public List<AgendamentoAulaResponseDTO> buscarPorDisciplina(Integer disciplinaId) {
        return agendamentoAulaRepository.findByDisciplinaId(disciplinaId).stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AgendamentoAulaResponseDTO> buscarPorProfessor(Integer professorId) {
        return agendamentoAulaRepository.findByProfessorId(professorId).stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgendamentoAulaResponseDTO atualizarAgendamentoAula(Long id, AgendamentoAulaCreationDTO dto) {
        AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento de aula não encontrado com ID: " + id));

        if (dto.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.usuarioId()));
            agendamento.setUsuario(usuario);
        }

        if (dto.salaId() != null) {
            Sala sala = salaRepository.findById(dto.salaId())
                    .orElseThrow(() -> new RuntimeException("Sala não encontrada com ID: " + dto.salaId()));
            agendamento.setSala(sala);
        }

        if (dto.disciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                    .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + dto.disciplinaId()));
            agendamento.setDisciplina(disciplina);
        }

        if (dto.data() != null) agendamento.setData(dto.data());
      
        if (dto.janelasHorarioId() != null){
            JanelasHorario janelasHorario = janelasHorarioRepository.findById(dto.janelasHorarioId()).orElseThrow(()-> new RuntimeException("Janela de horários inválida"));
            agendamento.setJanelasHorario(janelasHorario);
        } 
        agendamento.setIsEvento(false);

        AgendamentoAula updated = agendamentoAulaRepository.save(agendamento);
        return converterParaResponseDTO(updated);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void excluirAgendamentoAula(Long id) {
        if (!agendamentoAulaRepository.existsById(id)) {
            throw new RuntimeException("Agendamento de aula não encontrado com ID: " + id);
        }
        agendamentoAulaRepository.deleteById(id);
      
    }

    private AgendamentoAulaResponseDTO converterParaResponseDTO(AgendamentoAula agendamento) {
    return new AgendamentoAulaResponseDTO(
        agendamento.getId(),
        agendamento.getUsuario() != null ? agendamento.getUsuario().getNome() : null,
        agendamento.getSala() != null ? agendamento.getSala().getNome() : null,
        agendamento.getDisciplina() != null ? agendamento.getDisciplina().getId() : null,
        agendamento.getDisciplina() != null ? agendamento.getDisciplina().getNome() : null,
        agendamento.getDisciplina() != null ? agendamento.getDisciplina().getSemestre() : null,
        agendamento.getDisciplina() != null && agendamento.getDisciplina().getCurso() != null
            ? agendamento.getDisciplina().getCurso().getNomeCurso()
            : null,
        agendamento.getDisciplina() != null && agendamento.getDisciplina().getProfessor() != null
            ? agendamento.getDisciplina().getProfessor().getNome()
            : "Não atribuído",
        agendamento.getData(),
        agendamento.getDiaDaSemana(),
        agendamento.getJanelasHorario().getHoraInicio(),
        agendamento.getJanelasHorario().getHoraFim(),
        agendamento.getIsEvento().booleanValue()
        );
    }

    @Transactional
    public AgendamentoAulaResponseDTO criarAgendamentoAula(AgendamentoAulaCreationDTO dto) {
        
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.usuarioId()));

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada com ID: " + dto.salaId()));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + dto.disciplinaId()));

        JanelasHorario janelasHorario = janelasHorarioRepository.findById(dto.janelasHorarioId()).orElseThrow(()-> new RuntimeException("Janela de horários inválida"));


        AgendamentoAula agendamento = new AgendamentoAula();
        agendamento.setUsuario(usuario);
        agendamento.setSala(sala);
        agendamento.setDisciplina(disciplina);
        agendamento.setData(dto.data());
        agendamento.setJanelasHorario(janelasHorario);
        agendamento.setIsEvento(dto.isEvento());

        
        AgendamentoAula saved = agendamentoAulaRepository.save(agendamento);

        
        return converterParaResponseDTO(saved);
    }

}