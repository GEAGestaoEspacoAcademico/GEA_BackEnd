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

import jakarta.persistence.EntityNotFoundException;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    private CursoListDTO converteCursoParaDTO(Curso curso) {
        return new CursoListDTO(curso.getId(), curso.getNomeCurso(), curso.getCoordenador().getId(), curso.getSigla(), curso.getCoordenador().getId(),
                curso.getCoordenador().getNome());
    }

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoListDTO criar(CursoCreateDTO curso) {
        Curso novoCurso = new Curso();

        novoCurso.setNomeCurso(curso.cursoNome());
        novoCurso.setCoordenador(coordenadorRepository.findById(curso.coordenadorId()).orElseThrow());
        novoCurso.setSigla(curso.cursoSigla());

        Curso cursoSalvo = cursoRepository.save(novoCurso);

        return converteCursoParaDTO(cursoSalvo);
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
                .orElseThrow(() -> new EntityNotFoundException("Curso de id: " + id + " não encontrado"));
        return converteCursoParaDTO(cursoEncontrado);
    }

    public CursoListDTO atualizar(Long id, CursoCreateDTO novoCurso) {
        Curso atual = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso de id: " + id + " não encontrado"));
        atual.setNomeCurso(novoCurso.cursoNome());
        Coordenador coordenadorEncontrado = coordenadorRepository.findById(novoCurso.coordenadorId()).orElseThrow(()-> new EntityNotFoundException("Coordenador de id: " + id + " não encontrado"));
        atual.setCoordenador(coordenadorEncontrado);
        atual.setSigla(novoCurso.cursoSigla());

        Curso cursoAtualizado = cursoRepository.save(atual);

        return converteCursoParaDTO(cursoAtualizado);
    }

    public void excluir(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new EntityNotFoundException("Curso de id: " + id + " não encontrado");
        }
        cursoRepository.deleteById(id);
    }
}