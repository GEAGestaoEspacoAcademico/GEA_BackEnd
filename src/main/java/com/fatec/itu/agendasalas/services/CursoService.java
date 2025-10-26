package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<CursoListDTO> converteCursoParaDTO(List<Curso> cursos) {
        return cursos.stream()
                .map(curso -> new CursoListDTO(curso.getId(), curso.getNomeCurso(), curso.getCoordenador().getNome()))
                .toList();
    }

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoListDTO criar(CursoCreateDTO curso) {
        Curso novoCurso = new Curso();

        novoCurso.setNomeCurso(curso.nome());
        novoCurso.setCoordenador(coordenadorRepository.findById(curso.idCoordenador()).orElseThrow());

        Curso cursoSalvo = cursoRepository.save(novoCurso);

        return new CursoListDTO(cursoSalvo.getId(), cursoSalvo.getNomeCurso(), cursoSalvo.getCoordenador().getNome());
    }

    public List<CursoListDTO> listar() {
        return converteCursoParaDTO(cursoRepository.findAll());
    }

    public CursoListDTO buscarPorId(Long id) {
        Curso cursoEncontrado = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado. Id=" + id));
        return new CursoListDTO(cursoEncontrado.getId(), cursoEncontrado.getNomeCurso(),
                cursoEncontrado.getCoordenador().getNome());
    }

    public CursoListDTO atualizar(Long id, CursoCreateDTO novoCurso) {
        Curso atual = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado. Id=" + id));
        atual.setNomeCurso(novoCurso.nome());
        Coordenador coordenadorEncontrado = coordenadorRepository.findById(novoCurso.idCoordenador()).orElseThrow();
        atual.setCoordenador(coordenadorEncontrado);

        Curso cursoAtualizado = cursoRepository.save(atual);

        return new CursoListDTO(cursoAtualizado.getId(), cursoAtualizado.getNomeCurso(),
                cursoAtualizado.getCoordenador().getNome());
    }

    public void excluir(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException("Curso não encontrado. Id=" + id);
        }
        cursoRepository.deleteById(id);
    }
}