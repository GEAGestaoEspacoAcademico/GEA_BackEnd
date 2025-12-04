package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.itu.agendasalas.dto.cursos.CursoCreateDTO;
import com.fatec.itu.agendasalas.dto.cursos.CursoListDTO;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Curso;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.repositories.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    private CursoListDTO converteCursoParaDTO(Curso curso) {
        return new CursoListDTO(curso.getId(), curso.getNomeCurso(), curso.getCoordenador().getId(), curso.getSigla(),
                curso.getCoordenador().getNome());
    }

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public long criarCurso(CursoCreateDTO dto) {
        Curso novoCurso = new Curso();
        novoCurso.setNomeCurso(dto.cursoNome());
        novoCurso.setSigla(dto.cursoSigla());
        novoCurso.setCoordenador(
            coordenadorRepository.findById(dto.coordenadorId())
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"))
        );

        Curso cursoSalvo = cursoRepository.save(novoCurso);
        return cursoSalvo.getId(); 
    }


    public List<CursoListDTO> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoListDTO> cursosDTO = new ArrayList<>();

        for (Curso curso : cursos) {
            cursosDTO.add(converteCursoParaDTO(curso));
        }

        return cursosDTO;
    }

    public CursoListDTO buscarPorId(Long id) {
        Curso cursoEncontrado = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return converteCursoParaDTO(cursoEncontrado);
    }

    public CursoListDTO atualizar(Long id, CursoCreateDTO novoCurso) {
        Curso atual = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado. Id=" + id));
        atual.setNomeCurso(novoCurso.cursoNome());
        Coordenador coordenadorEncontrado = coordenadorRepository.findById(novoCurso.coordenadorId()).orElseThrow();
        atual.setCoordenador(coordenadorEncontrado);
        atual.setSigla(novoCurso.cursoSigla());

        Curso cursoAtualizado = cursoRepository.save(atual);

        return converteCursoParaDTO(cursoAtualizado);
    }

    public void excluir(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado. Id=" + id);
        }
        cursoRepository.deleteById(id);
    }
}