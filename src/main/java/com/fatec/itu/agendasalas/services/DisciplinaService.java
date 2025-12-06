package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaCreateDTO;
import com.fatec.itu.agendasalas.dto.disciplinas.DisciplinaListDTO;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.entity.Disciplina;
import com.fatec.itu.agendasalas.entity.Semestre;
import com.fatec.itu.agendasalas.repositories.CursoRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.SemestreRepository;

import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoCancelado;
import com.fatec.itu.agendasalas.repositories.AgendamentoAulaRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoCanceladoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DisciplinaListDTO criar(DisciplinaCreateDTO dto) {

    Curso curso = cursoRepository.findById(dto.cursoId())
            .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

    Semestre semestre = semestreRepository.findById(dto.semestreId())
            .orElseThrow(() -> new RuntimeException("Semestre não encontrado"));

    Disciplina nova = new Disciplina();
    nova.setNome(dto.disciplinaNome());
    nova.setCurso(curso);
    nova.setSemestre(semestre);

    nova = disciplinaRepository.save(nova);

    return new DisciplinaListDTO(
            nova.getId(),
            nova.getNome(),
            nova.getSemestre().getId(),
            nova.getSemestre().getNome(),
            nova.getCurso().getId(),
            nova.getCurso().getNomeCurso()
    );
}


   private List<DisciplinaListDTO> converterParaDTO(List<Disciplina> disciplinas) {
    return disciplinas.stream()
            .map(d -> new DisciplinaListDTO(
                    d.getId(),
                    d.getNome(),
                    d.getSemestre().getId(),
                    d.getSemestre().getNome(),
                    d.getCurso().getId(),
                    d.getCurso().getNomeCurso()
            ))
            .toList();
}


    public List<DisciplinaListDTO> listar() {
        return converterParaDTO(disciplinaRepository.findAll());
    }

    public List<DisciplinaListDTO> listarDisciplinasPorProfessor(Long professorId) {
        return converterParaDTO(disciplinaRepository.findByProfessorId(professorId));
    }

    public List<DisciplinaListDTO> listarDisciplinasPorCurso(Long cursoId){
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new EntityNotFoundException("Curso de id: " + cursoId + " não encontrado"));
        return converterParaDTO(disciplinaRepository.findByCursoId(curso.getId()));
    }

    public DisciplinaListDTO buscarPorId(Long id) {
    Disciplina disciplina = disciplinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Disciplina de id: " + id + " não encontrada"));

    return new DisciplinaListDTO(
        disciplina.getId(),
        disciplina.getNome(),
        disciplina.getSemestre().getId(),
        disciplina.getSemestre().getNome(),
        disciplina.getCurso().getId(),
        disciplina.getCurso().getNomeCurso()   
        );
    }


    public DisciplinaListDTO atualizar(Long id, DisciplinaCreateDTO dto) {

    Disciplina atual = disciplinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Disciplina de id: " + id + " não encontrada"));

    Curso curso = cursoRepository.findById(dto.cursoId())
            .orElseThrow();

    Semestre semestre = semestreRepository.findById(dto.semestreId())
            .orElseThrow();

    atual.setNome(dto.disciplinaNome());
    atual.setCurso(curso);
    atual.setSemestre(semestre);

    Disciplina disciplinaAtualizada = disciplinaRepository.save(atual);

    return new DisciplinaListDTO(
        disciplinaAtualizada.getId(),
        disciplinaAtualizada.getNome(),
        disciplinaAtualizada.getSemestre().getId(),
        disciplinaAtualizada.getSemestre().getNome(),
        disciplinaAtualizada.getCurso().getId(),
        disciplinaAtualizada.getCurso().getNomeCurso()
    );
    }


    @Autowired
    private AgendamentoAulaRepository agendamentoAulaRepository;

    @Autowired
    private AgendamentoCanceladoRepository agendamentoCanceladoRepository;

    public void excluir(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Disciplina de id: " + id + " não encontrada"));


        disciplina.setExcluida(true);


        disciplina.setProfessor(null);


        disciplina.setCurso(null);


        List<AgendamentoAula> agendamentos = agendamentoAulaRepository.findByDisciplinaId(id.intValue());
        for (AgendamentoAula agendamento : agendamentos) {
            AgendamentoCancelado cancelado = AgendamentoCancelado.builder()
                .agendamentoOriginalId(agendamento.getId())
                .tipoAgendamento("AULA")
                .data(agendamento.getData())
                .statusOriginal(agendamento.getStatus())
                .solicitante(agendamento.getSolicitante())
                .usuario(agendamento.getUsuario())
                .build();
            agendamentoCanceladoRepository.save(cancelado);
            agendamentoAulaRepository.delete(agendamento);
        }

        disciplinaRepository.save(disciplina);
    }

    public Disciplina findById(Long id) {
        return disciplinaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Disciplina de id: " + id + " não encontrada"));
    }

   
}
