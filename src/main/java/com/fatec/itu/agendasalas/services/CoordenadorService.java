package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorCreationDTO;
import com.fatec.itu.agendasalas.dto.coordenadores.CoordenadorResponseDTO;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
public class CoordenadorService {

	@Autowired
	private CoordenadorRepository coordenadorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CargoRepository cargoRepository;

    @PersistenceContext
    private EntityManager entityManager;

	@Transactional
	public Coordenador promoverParaCoordenador(CoordenadorCreationDTO dto) {
		Usuario usuario = usuarioRepository.findById(dto.coordenadorUsuarioId())
				.orElseThrow(() -> new EntityNotFoundException("Usuário de id: " + dto.coordenadorUsuarioId() + " não encontrado"));
		if (coordenadorRepository.existsById(usuario.getId())) {
			throw new RuntimeException("Usuário já é coordenador");
		}
		usuario.setCargo(cargoRepository.findByNome("COORDENADOR")
				.orElseThrow(() -> new EntityNotFoundException("Cargo COORDENADOR não encontrado")));
		usuarioRepository.save(usuario);

        entityManager.createNativeQuery(
                "INSERT INTO coordenadores (user_id, registro_coordenacao) VALUES (:userId, :registro)"
        )
        .setParameter("userId", usuario.getId())
        .setParameter("registro", dto.registroCoordenacao())
        .executeUpdate();

        return coordenadorRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Falha ao promover coordenador"));
	}

	@Transactional(readOnly = true)
	public List<CoordenadorResponseDTO> listarCoordenadores() {
		return coordenadorRepository.findAll().stream()
		.map(this::toResponseDTO)
		.toList();
	}

	@Transactional(readOnly = true)
	public CoordenadorResponseDTO buscarPorId(Long id) {
		Coordenador coordenador = coordenadorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Coordenador de id: " + id + " não encontrado"));
		return toResponseDTO(coordenador);
	}


	@Transactional
	public void despromoverCoordenador(Long id) {
		coordenadorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Coordenador de id: " + id + " não encontrado"));
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário de id: " + id + " não encontrado"));
		usuario.setCargo(cargoRepository.findByNome("USER")
				.orElseThrow(() -> new EntityNotFoundException("Cargo USER não encontrado")));
		usuarioRepository.save(usuario);
		coordenadorRepository.deleteById(id);
	}

	
    private CoordenadorResponseDTO toResponseDTO(Coordenador c) {
        return new CoordenadorResponseDTO(c.getId(), c.getNome(), c.getEmail(),
                c.getRegistroCoordenacao(), c.getCargo() != null ? c.getCargo().getId() : null);
    }

 	
    public CoordenadorResponseDTO buscarPorRegistro(Long registro) {
        Coordenador coordenador =
                coordenadorRepository.findByRegistroCoordenacao(registro).orElseThrow(
                        () -> new EntityNotFoundException("Coordenador com registro: " + registro + " não encontrado"));
        return (toResponseDTO(coordenador));
    }
}
