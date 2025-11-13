package com.fatec.itu.agendasalas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
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
        agendamento.setDataInicio(dto.dataInicio());
        agendamento.setDataFim(dto.dataFim());
        agendamento.setJanelasHorario(janelasHorario);
        agendamento.setTipo(dto.tipoAgendamento());

        
        AgendamentoAula saved = agendamentoAulaRepository.save(agendamento);

        
        return converterParaResponseDTO(saved);
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

        if (dto.dataInicio() != null) agendamento.setDataInicio(dto.dataInicio());
        if (dto.dataFim() != null) agendamento.setDataFim(dto.dataFim());
        if (dto.janelasHorarioId() != null){
            JanelasHorario janelasHorario = janelasHorarioRepository.findById(dto.janelasHorarioId()).orElseThrow(()-> new RuntimeException("Janela de horários inválida"));
            agendamento.setJanelasHorario(janelasHorario);
        } 
        if(dto.tipoAgendamento()!=null) agendamento.setTipo(dto.tipoAgendamento());

        AgendamentoAula updated = agendamentoAulaRepository.save(agendamento);
        return converterParaResponseDTO(updated);
    }

    @Transactional
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
        agendamento.getDataInicio(),
        agendamento.getDataFim(),
        agendamento.getDiaDaSemana(),
        agendamento.getJanelasHorario().getHoraInicio(),
        agendamento.getJanelasHorario().getHoraFim(),
        agendamento.getTipo()
        );
    }

    
    private boolean verificarConflitoComEvento(){
        
    }
}