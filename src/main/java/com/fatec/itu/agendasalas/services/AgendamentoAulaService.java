package com.fatec.itu.agendasalas.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
import com.fatec.itu.agendasalas.exceptions.AgendamentoComHorarioIndisponivelException;
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
    public List<AgendamentoAulaResponseDTO> criarAgendamentoAulaComRecorrencia(AgendamentoAulaCreationComRecorrenciaDTO agendamentoAulaCreationComRecorrenciaDTO){
        List<AgendamentoAula> agendamentoAulasFeitos = new ArrayList<>();
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
                    agendamentoAulasFeitos.add(agendamentoAula);
                }
            }
            dataInicial = dataInicial.plusDays(1);
        }
        return agendamentoAulasFeitos.stream()
        .map(this::converterParaResponseDTO)
        .toList();
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

        Recorrencia recorrencia = recorrenciaRepository.save(new Recorrencia(data, data));

        List<JanelasHorario> janelasHorarios = janelasHorarioRepository.findByIntervaloIdHorarios(dto.horaInicio(), dto.horaFim());
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

    @Transactional
    public void criar(AgendamentoAulaCreationDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com ID: "
                        + dto.usuarioId()));

                Sala sala = salaRepository.findById(dto.salaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Sala não encontrada com ID: " + dto.salaId()));

                Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Disciplina não encontrada com ID: "
                                                                + dto.disciplinaId()));

                List<JanelasHorario> horariosDisponiveis =
                                janelasHorarioRepository.findDisponiveisPorData(dto.data());

                Set<Long> idsDesejados = LongStream
                                .range(dto.janelasHorarioId(),
                                                dto.janelasHorarioId() + dto.quantidade())
                                .boxed().collect(Collectors.toSet());

                Set<Long> idsDisponiveis = horariosDisponiveis.stream()
                                .map(JanelasHorario::getId)
                                .collect(Collectors.toSet());

                boolean sequenciaEstaDisponivel = idsDisponiveis.containsAll(idsDesejados);

                if (sequenciaEstaDisponivel) {
                        int quantidadeAulasRestantes = dto.quantidade() - 1;

                        for (int i = 0; i <= quantidadeAulasRestantes; i++) {
                                long idDesejado = dto.janelasHorarioId() + i;
                                JanelasHorario janela = horariosDisponiveis.stream()
                                                .filter(j -> j.getId().longValue() == idDesejado)
                                                .findFirst()
                                                .orElseThrow(() -> new AgendamentoComHorarioIndisponivelException(
                                                                "Janela de horário inválida: " + idDesejado));

                                AgendamentoAula proximoAgendamento = new AgendamentoAula();

                                proximoAgendamento.setUsuario(usuario);
                                proximoAgendamento.setSala(sala);
                                proximoAgendamento.setDisciplina(disciplina);
                                proximoAgendamento.setData(dto.data());
                                proximoAgendamento.setIsEvento((dto.isEvento()));
                                proximoAgendamento.setJanelasHorario(janela);

                                agendamentoAulaRepository.save(proximoAgendamento);
                        }
                } else {
                        throw new AgendamentoComHorarioIndisponivelException("Horário indisponivel para esse agendamento.");
                }
        }

        public List<AgendamentoAulaResponseDTO> listarTodos() {
                return agendamentoAulaRepository.findAll().stream()
                                .map(this::converterParaResponseDTO).collect(Collectors.toList());
        }

        public AgendamentoAulaResponseDTO buscarPorId(Long id) {
                AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Agendamento de aula não encontrado com ID: "
                                                                + id));
                return converterParaResponseDTO(agendamento);
        }

      
    

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void excluirAgendamentoAula(Long id) {
        if (!agendamentoAulaRepository.existsById(id)) {
            throw new RuntimeException("Agendamento de aula não encontrado com ID: " + id);
        }
        agendamentoAulaRepository.deleteById(id);
      
    }
        public List<AgendamentoAulaResponseDTO> buscarPorDisciplina(Integer disciplinaId) {
                return agendamentoAulaRepository.findByDisciplinaId(disciplinaId).stream()
                                .map(this::converterParaResponseDTO).collect(Collectors.toList());
        }

        public List<AgendamentoAulaResponseDTO> buscarPorProfessor(Integer professorId) {
                return agendamentoAulaRepository.findByProfessorId(professorId).stream()
                                .map(this::converterParaResponseDTO).collect(Collectors.toList());
        }

        @Transactional
        public AgendamentoAulaResponseDTO atualizarAgendamentoAula(Long id,
                        AgendamentoAulaCreationDTO dto) {
                AgendamentoAula agendamento = agendamentoAulaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Agendamento de aula não encontrado com ID: "
                                                                + id));

                if (dto.usuarioId() != null) {
                        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                                        () -> new RuntimeException("Usuário não encontrado com ID: "
                                                        + dto.usuarioId()));
                        agendamento.setUsuario(usuario);
                }

                if (dto.salaId() != null) {
                        Sala sala = salaRepository.findById(dto.salaId()).orElseThrow(
                                        () -> new RuntimeException("Sala não encontrada com ID: "
                                                        + dto.salaId()));
                        agendamento.setSala(sala);
                }

                if (dto.disciplinaId() != null) {
                        Disciplina disciplina = disciplinaRepository.findById(dto.disciplinaId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Disciplina não encontrada com ID: "
                                                                        + dto.disciplinaId()));
                        agendamento.setDisciplina(disciplina);
                }
                if (dto.data() != null)
                        agendamento.setData(dto.data());
                if (dto.janelasHorarioId() != null) {
                        JanelasHorario janelasHorario = janelasHorarioRepository
                                        .findById(dto.janelasHorarioId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Janela de horários inválida"));
                        agendamento.setJanelasHorario(janelasHorario);
                }
                // agendamento.setEvento((dto.isEvento()));

                AgendamentoAula updated = agendamentoAulaRepository.save(agendamento);
                return converterParaResponseDTO(updated);
        }

    private AgendamentoAulaResponseDTO converterParaResponseDTO(AgendamentoAula agendamento) {
         return new AgendamentoAulaResponseDTO(
                 agendamento.getId(),
                 agendamento.getUsuario().getNome(),
                 agendamento.getSala().getId(),
                 agendamento.getSala().getNome(),
                 agendamento.getDisciplina().getId(),
                 agendamento.getDisciplina().getNome(),
                 agendamento.getDisciplina().getSemestre(),
                 agendamento.getDisciplina().getCurso().getNomeCurso(),
                 agendamento.getDisciplina().getProfessor().getNome(),
                 agendamento.getData(),
                 agendamento.getDiaDaSemana(),
                 agendamento.getJanelasHorario().getId(),
                 agendamento.getJanelasHorario().getHoraInicio(),
                 agendamento.getJanelasHorario().getHoraFim(),
                 agendamento.getIsEvento());
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


