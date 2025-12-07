package com.fatec.itu.agendasalas.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationByAuxiliarDocenteDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationComRecorrenciaDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaFilterDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoCancelado;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.AgendamentoComHorarioIndisponivelException;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.exceptions.DataInicialMaiorQueDataFinalException;
import com.fatec.itu.agendasalas.exceptions.DataNoPassadoException;
import com.fatec.itu.agendasalas.exceptions.JanelaHorarioPassouException;
import com.fatec.itu.agendasalas.exceptions.ProfessorJaPossuiAgendamentoEmOutraSalaException;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
import com.fatec.itu.agendasalas.repositories.ProfessorRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;
import com.fatec.itu.agendasalas.specifications.AgendamentoAulaSpecification;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;

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
    private ProfessorRepository professorRepository;

    @Autowired
    private JanelasHorarioRepository janelasHorarioRepository;

    @Autowired
    private RecorrenciaRepository recorrenciaRepository;

    @Autowired
    private AgendamentoConflitoService agendamentoConflitoService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Transactional
    //método para a tela Agendar Sala da Matéria da Secretaria
    public List<AgendamentoAulaResponseDTO> criarAgendamentoAulaComRecorrencia(AgendamentoAulaCreationComRecorrenciaDTO agendamentoAulaCreationComRecorrenciaDTO) throws MessagingException{
        List<AgendamentoAula> agendamentoAulasFeitos = new ArrayList<>();
        
        LocalDate dataInicial = agendamentoAulaCreationComRecorrenciaDTO.dataInicio();
        
        LocalDate dataFinal = agendamentoAulaCreationComRecorrenciaDTO.dataFim();
        
        if(dataInicial.isAfter(dataFinal)){
            throw new DataInicialMaiorQueDataFinalException(dataInicial, dataFinal); 
        }

        if(agendamentoConflitoService.dataNoPassado(dataInicial)){
            throw new DataNoPassadoException(dataInicial);
        }


        List<Long> janelasHorarioId = agendamentoAulaCreationComRecorrenciaDTO.janelasHorarioId();

        Disciplina disciplina = disciplinaRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.disciplinaId()).orElseThrow(()->new EntityNotFoundException("Disciplina com id: " + agendamentoAulaCreationComRecorrenciaDTO.disciplinaId() + "não encontrada"));
        Sala sala = salaRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.salaId()).orElseThrow(()-> new EntityNotFoundException("Sala com id: " + agendamentoAulaCreationComRecorrenciaDTO.salaId() + " não encontrada"));
       
        Recorrencia recorrencia = new Recorrencia(dataInicial, dataFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);

        Usuario usuario = usuarioRepository.findById(agendamentoAulaCreationComRecorrenciaDTO.usuarioId()).orElseThrow(()-> new EntityNotFoundException("Usuário com id: " + agendamentoAulaCreationComRecorrenciaDTO.usuarioId() + " não encontrado"));
        DayOfWeek diaDaSemana = agendamentoAulaCreationComRecorrenciaDTO.diaDaSemana().toJavaDay();
        while(!dataInicial.isAfter(dataFinal)){
            if(dataInicial.getDayOfWeek()==diaDaSemana){
                for(Long janelaId:janelasHorarioId){
                    

                    JanelasHorario janelaHorario = janelasHorarioRepository.findById(janelaId).orElseThrow(()-> new EntityNotFoundException("Janela de Horário com id: " + janelaId + " não encontrada"));
                    if(agendamentoConflitoService.janelaHorarioPassou(dataInicial, janelaHorario.getHoraInicio(), janelaHorario.getHoraFim())){
                        throw new JanelaHorarioPassouException(dataInicial, janelaHorario.getHoraInicio(), janelaHorario.getHoraFim());
                    }

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
                    agendamentoAula.setStatus("ATIVO");
                    agendamentoAula.setSolicitante("SECRETARIA");
                    recorrenciaSalva.addAgendamento(agendamentoAula);
                    agendamentoAulaRepository.save(agendamentoAula);
                    agendamentoAulasFeitos.add(agendamentoAula);
                }
            }
            dataInicial = dataInicial.plusDays(1);
        }
        notificacaoService.notificarAoCriarAgendamentoAulaRecorrente(recorrencia);
        return agendamentoAulasFeitos.stream()
        .map(this::converterParaResponseDTO)
        .toList();
    }

    

    @Transactional
    //Esse método se refere ao Agendar Aula pelo AD
    public List<AgendamentoAulaResponseDTO> criarAgendamentoAulaByAD(AgendamentoAulaCreationByAuxiliarDocenteDTO dto) throws MessagingException {
        
        List<AgendamentoAula> agendamentosRealizados = new ArrayList<>();
        List<AgendamentoAulaResponseDTO> listaResposta = new ArrayList<>();

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id: " + dto.usuarioId() + " não encontrado"));
        
        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala com id: " + dto.salaId() + " não encontrada"));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new EntityNotFoundException("Disciplina com id: " + dto.disciplinaId() + " não encontrada"));

        LocalDate data = dto.data();
        if(agendamentoConflitoService.dataNoPassado(data)){
                throw new DataNoPassadoException(data);
        }
        Recorrencia recorrencia = recorrenciaRepository.save(new Recorrencia(data, data));

        List<JanelasHorario> janelasHorarios = janelasHorarioRepository.findByIntervaloIdHorarios(dto.horaInicio(), dto.horaFim());
        String solicitante = dto.solicitante();

        for(JanelasHorario janela : janelasHorarios){
           if(agendamentoConflitoService.janelaHorarioPassou(data, janela.getHoraInicio(), janela.getHoraFim())){
                throw new JanelaHorarioPassouException(data, janela.getHoraInicio(), janela.getHoraFim());
           }

                
           if(agendamentoConflitoService.existeAgendamentoNoHorario(sala.getId(), data, janela.getId())){
                throw new ConflitoAoAgendarException(sala.getNome(), data, janela.getHoraInicio(), janela.getHoraFim());
            }
            
            if(agendamentoConflitoService.professorJaPossuiAgendamentoEmOutraSala(sala.getId(), data, janela.getId(), disciplina.getProfessor().getId())){
                throw new ProfessorJaPossuiAgendamentoEmOutraSalaException(data, janela.getHoraInicio(), janela.getHoraFim(), disciplina.getProfessor().getNome());
            }

            AgendamentoAula agendamento = new AgendamentoAula();
            agendamento.setUsuario(usuario);
            agendamento.setSala(sala);
            agendamento.setDisciplina(disciplina);
            agendamento.setData(data);
            agendamento.setJanelasHorario(janela);
            agendamento.setIsEvento(false);
            agendamento.setStatus("ATIVO");
            agendamento.setSolicitante(solicitante);
            agendamento.preencherDiaDaSemana();
            recorrencia.addAgendamento(agendamento);
            AgendamentoAula saved = agendamentoAulaRepository.save(agendamento);
            agendamentosRealizados.add(saved);
        }
        notificacaoService.notificarAoCriarAgendamentoAulaRecorrente(recorrencia);
        listaResposta = agendamentosRealizados
            .stream()
            .map(this::converterParaResponseDTO)
            .collect(Collectors.toList());
        
        return listaResposta;
  
    }

    @Transactional
    public Long criar(AgendamentoAulaCreationDTO dto) throws MessagingException {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                () -> new EntityNotFoundException("Usuário com id: " + dto.usuarioId() + " não encontrado"));

        if (agendamentoConflitoService.dataNoPassado(dto.data())) {
            throw new DataNoPassadoException(dto.data());
        }

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala com id: " + dto.salaId() + " não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Disciplina não encontrada com ID: " + dto.disciplinaId()));

        List<JanelasHorario> horariosDisponiveis =
                janelasHorarioRepository.findDisponiveisPorData(dto.data(), dto.salaId());

        Set<Long> idsDesejados = LongStream
                .range(dto.janelasHorarioId(), dto.janelasHorarioId() + dto.quantidade())
                .boxed()
                .collect(Collectors.toSet());

        Set<Long> idsDisponiveis = horariosDisponiveis.stream()
                .map(JanelasHorario::getId)
                .collect(Collectors.toSet());

        boolean sequenciaEstaDisponivel = idsDisponiveis.containsAll(idsDesejados);

        if (!sequenciaEstaDisponivel) {
            throw new AgendamentoComHorarioIndisponivelException(
                 "Horário indisponível para esse agendamento.");
        }

        Recorrencia recorrencia = new Recorrencia(dto.data(), dto.data());
        recorrencia = recorrenciaRepository.save(recorrencia);

        int quantidadeAulasRestantes = dto.quantidade() - 1;

        Long idPrimeiroAgendamento = null;

        for (int i = 0; i <= quantidadeAulasRestantes; i++) {
            long idDesejado = dto.janelasHorarioId() + i;

            JanelasHorario janela = horariosDisponiveis.stream()
                    .filter(j -> j.getId() == idDesejado)
                    .findFirst()
                    .orElseThrow(() -> new AgendamentoComHorarioIndisponivelException(
                            "Janela de horário inválida: " + idDesejado));
            
            if(agendamentoConflitoService.professorJaPossuiAgendamentoEmOutraSala(sala.getId(), dto.data(), janela.getId(), usuario.getId())){
                throw new ProfessorJaPossuiAgendamentoEmOutraSalaException(dto.data(), janela.getHoraInicio(), janela.getHoraFim(), usuario.getNome());
            }

            AgendamentoAula novoAgendamento = new AgendamentoAula();

            novoAgendamento.setUsuario(usuario);
            novoAgendamento.setSolicitante(usuario.getNome());
            novoAgendamento.setSala(sala);
            novoAgendamento.setDisciplina(disciplina);
            novoAgendamento.setData(dto.data());
            novoAgendamento.preencherDiaDaSemana();
            novoAgendamento.setIsEvento(dto.isEvento());
            novoAgendamento.setJanelasHorario(janela);
            novoAgendamento.setStatus("ATIVO");
            recorrencia.addAgendamento(novoAgendamento);
            
            novoAgendamento = agendamentoAulaRepository.save(novoAgendamento);

            if (i == 0) {
                idPrimeiroAgendamento = novoAgendamento.getId();
            }
        }   
        notificacaoService.notificarAoCriarAgendamentoAulaRecorrente(recorrencia);

        return idPrimeiroAgendamento;
    }


    public List<AgendamentoAulaResponseDTO> listarTodos() {
        return agendamentoAulaRepository.findAll().stream()
            .map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public AgendamentoAulaResponseDTO buscarPorId(Long id) {
        AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
            "Agendamento de aula não encontrado com ID: "+ id));
            return converterParaResponseDTO(agendamento);
    }

      
    

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void excluirAgendamentoAula(Long id) {
        if (!agendamentoAulaRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento de aula não encontrado com ID: " + id);
        }
        AgendamentoCancelado agendamentoCancelado = new AgendamentoCancelado();
        agendamentoAulaRepository.deleteById(id);
      
    }

    public List<AgendamentoAulaResponseDTO> buscarPorDisciplina(Long disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(()-> new EntityNotFoundException("Disciplina de id: " + disciplinaId + " não encontrada"));
        return agendamentoAulaRepository.findByDisciplinaId(disciplina.getId()).stream()
            .map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    public List<AgendamentoAulaResponseDTO> buscarPorProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(()-> new EntityNotFoundException("Professor de id: " + professorId + " não encontrado"));
        return agendamentoAulaRepository.findByProfessorId(professor.getId()).stream()
            .map(this::converterParaResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public AgendamentoAulaResponseDTO atualizarAgendamentoAula(Long id, AgendamentoAulaCreationDTO dto) {
        AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
            "Agendamento de aula não encontrado com ID: " + id));

            if (dto.usuarioId() != null) {
                 Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                    () -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.usuarioId()));
                agendamento.setUsuario(usuario);
            }

                if (dto.salaId() != null) {
                        Sala sala = salaRepository.findById(dto.salaId()).orElseThrow(
                                        () -> new EntityNotFoundException("Sala não encontrada com ID: "
                                                        + dto.salaId()));
                        agendamento.setSala(sala);
                }

                if (dto.disciplinaId() != null) {
                        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "Disciplina não encontrada com ID: "
                                                                        + dto.disciplinaId()));
                        agendamento.setDisciplina(disciplina);
                }
                if (dto.data() != null)
                        agendamento.setData(dto.data());
                if (dto.janelasHorarioId() != null) {
                        JanelasHorario janelasHorario = janelasHorarioRepository
                                        .findById(dto.janelasHorarioId())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "Janela de horários de id "  + dto.janelasHorarioId()  + " não encontrada"));
                        agendamento.setJanelasHorario(janelasHorario);
                }
                // agendamento.setEvento((dto.isEvento()));

                AgendamentoAula updated = agendamentoAulaRepository.save(agendamento);
                return converterParaResponseDTO(updated);
        }

    private AgendamentoAulaResponseDTO converterParaResponseDTO(AgendamentoAula agendamento) {
    Disciplina d = agendamento.getDisciplina();

    return new AgendamentoAulaResponseDTO(
        agendamento.getId(),
        agendamento.getUsuario().getNome(),
        agendamento.getSala().getId(),
        agendamento.getSala().getNome(),
        d.getId(),
        d.getNome(),
        d.getSemestre().getId(),   
        d.getSemestre().getNome(),   
        d.getCurso().getNomeCurso(),
        d.getProfessor().getNome(),
        agendamento.getData(),
        agendamento.getDiaDaSemana(),
        agendamento.getJanelasHorario().getId(),
        agendamento.getJanelasHorario().getHoraInicio(),
        agendamento.getJanelasHorario().getHoraFim(),
        agendamento.getIsEvento()
    );
}


    //Não use.
    @Deprecated(since = "07/12/2025", forRemoval=true)
    @Transactional
    public AgendamentoAulaResponseDTO criarAgendamentoAula(AgendamentoAulaCreationDTO dto) {
        
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.usuarioId()));

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada com ID: " + dto.salaId()));

        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                .orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada com ID: " + dto.disciplinaId()));

        JanelasHorario janelasHorario = janelasHorarioRepository.findById(dto.janelasHorarioId()).orElseThrow(()-> new RuntimeException("Janela de horários inválida"));

        if(agendamentoConflitoService.dataNoPassado(dto.data())){
            throw new DataNoPassadoException(dto.data());
        }

        if(agendamentoConflitoService.janelaHorarioPassou(dto.data(), janelasHorario.getHoraInicio(), janelasHorario.getHoraInicio())){
            throw new JanelaHorarioPassouException(dto.data(), janelasHorario.getHoraInicio(), janelasHorario.getHoraFim()); 
        }


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


    public Page<AgendamentoAulaResponseDTO> listarAgendamentosAulaPorFiltros(AgendamentoAulaFilterDTO filtros, int page, int limit, String sort) {
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(sort).ascending());
        Specification<AgendamentoAula> spec = AgendamentoAulaSpecification.porFiltros(filtros);
        
        return agendamentoAulaRepository.findAll(spec, pageable)
            .map(this::converterParaResponseDTO); 
    }


    public void cancelarAgendamentoPorRecorrencia(Long recorrenciaId){
        Recorrencia recorrencia = recorrenciaRepository.findById(recorrenciaId).orElseThrow(() -> new EntityNotFoundException("Recorrencia de id: " + recorrenciaId + " não encontrada"));
        recorrenciaRepository.delete(recorrencia);
    }
}


