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
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
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

    @Transactional
    public AgendamentoAulaResponseDTO criarAgendamentoAula(AgendamentoAulaCreationDTO dto) {
        
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.getUsuarioId()));

        Sala sala = salaRepository.findById(dto.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada com ID: " + dto.getSalaId()));

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + dto.getDisciplinaId()));

        
        AgendamentoAula agendamento = new AgendamentoAula();
        agendamento.setUsuario(usuario);
        agendamento.setSala(sala);
        agendamento.setDisciplina(disciplina);
        agendamento.setDataInicio(dto.getDataInicio());
        agendamento.setDataFim(dto.getDataFim());
        agendamento.setDiaDaSemana(dto.getDiaDaSemana());
        agendamento.setHoraInicio(dto.getHoraInicio());
        agendamento.setHoraFim(dto.getHoraFim());

        
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

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.getUsuarioId()));
            agendamento.setUsuario(usuario);
        }

        if (dto.getSalaId() != null) {
            Sala sala = salaRepository.findById(dto.getSalaId())
                    .orElseThrow(() -> new RuntimeException("Sala não encontrada com ID: " + dto.getSalaId()));
            agendamento.setSala(sala);
        }

        if (dto.getDisciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                    .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com ID: " + dto.getDisciplinaId()));
            agendamento.setDisciplina(disciplina);
        }

        if (dto.getDataInicio() != null) agendamento.setDataInicio(dto.getDataInicio());
        if (dto.getDataFim() != null) agendamento.setDataFim(dto.getDataFim());
        if (dto.getDiaDaSemana() != null) agendamento.setDiaDaSemana(dto.getDiaDaSemana());
        if (dto.getHoraInicio() != null) agendamento.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFim() != null) agendamento.setHoraFim(dto.getHoraFim());

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
        AgendamentoAulaResponseDTO dto = new AgendamentoAulaResponseDTO();
        dto.setId(agendamento.getId());
        dto.setNomeUsuario(agendamento.getUsuario().getNome());
        dto.setNomeSala(agendamento.getSala().getNome());
        dto.setDisciplinaId(agendamento.getDisciplina().getId());
        dto.setNomeDisciplina(agendamento.getDisciplina().getNome());
        dto.setSemestre(agendamento.getDisciplina().getSemestre());
        dto.setCurso(agendamento.getDisciplina().getCurso() != null ? agendamento.getDisciplina().getCurso().getNomeCurso() : null);
        dto.setNomeProfessor(agendamento.getDisciplina().getProfessor() != null 
                ? agendamento.getDisciplina().getProfessor().getNome() 
                : "Não atribuído");
        dto.setDataInicio(agendamento.getDataInicio());
        dto.setDataFim(agendamento.getDataFim());
        dto.setDiaDaSemana(agendamento.getDiaDaSemana());
        dto.setHoraInicio(agendamento.getHoraInicio());
        dto.setHoraFim(agendamento.getHoraFim());
        dto.setTipo(agendamento.getTipo());
        return dto;
    }
}
