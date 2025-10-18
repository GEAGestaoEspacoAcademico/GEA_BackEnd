package com.fatec.itu.agendasalas.services;

import com.fatec.itu.agendasalas.dto.CoordenadorCreationDTO;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CoordenadorRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
		Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		if (coordenadorRepository.existsById(usuario.getId())) {
			throw new RuntimeException("Usuário já é coordenador");
		}
		usuario.setCargo(cargoRepository.findByNome("COORDENADOR")
				.orElseThrow(() -> new RuntimeException("Cargo COORDENADOR não encontrado")));
		usuarioRepository.save(usuario);

        entityManager.createNativeQuery(
                "INSERT INTO coordenadores (user_id, registro_coordenacao) VALUES (:userId, :registro)"
        )
        .setParameter("userId", usuario.getId())
        .setParameter("registro", dto.getRegistroCoordenacao())
        .executeUpdate();

        return coordenadorRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Falha ao promover coordenador"));
	}

	@Transactional(readOnly = true)
	public List<Coordenador> listarCoordenadores() {
		return coordenadorRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Coordenador> buscarPorId(Long id) {
		return coordenadorRepository.findById(id);
	}

	@Transactional
	public void despromoverCoordenador(Long id) {
		coordenadorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		usuario.setCargo(cargoRepository.findByNome("USER")
				.orElseThrow(() -> new RuntimeException("Cargo USER não encontrado")));
		usuarioRepository.save(usuario);
		coordenadorRepository.deleteById(id);
	}

}
