package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaCreationDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Secretaria;
import com.fatec.itu.agendasalas.exceptions.CargoNaoEncontradoException;
import com.fatec.itu.agendasalas.exceptions.EmailJaCadastradoException;
import com.fatec.itu.agendasalas.exceptions.MatriculaDuplicadaSecretariaException;
import com.fatec.itu.agendasalas.exceptions.SecretariaNaoEncontradoException;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.SecretariaRepository;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<SecretariaResponseDTO> listarSecretarios() {
        return secretariaRepository.findAll().stream()
        .map(this::converterParaResponseDTO)
        .toList();
    }

    public SecretariaResponseDTO buscarSecretarioPorId(Long id){
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new SecretariaNaoEncontradoException(id));
        return converterParaResponseDTO(secretaria);
    }

    public SecretariaResponseDTO cadastrarSecretaria(SecretariaCreationDTO dto) {
        // Aqui você pode validar se já existe secretaria com a mesma matricula
        if(secretariaRepository.existsByMatricula(dto.matricula())) {
            throw new MatriculaDuplicadaSecretariaException(dto.matricula());
        }
        if(usuarioRepository.existsByEmail(dto.email())){
            throw new EmailJaCadastradoException(dto.email());
        }

        Secretaria secretaria = new Secretaria();
        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setMatricula(dto.matricula());

        Cargo cargo = cargoRepository.findByNome("SECRETARIA")
            .orElseThrow(() -> new CargoNaoEncontradoException("SECRETARIA"));
        secretaria.setCargo(cargo);

        secretariaRepository.save(secretaria);

        return new SecretariaResponseDTO(
            secretaria.getId(),
            secretaria.getNome(),
            secretaria.getEmail(),
            secretaria.getMatricula(),
            secretaria.getCargo().getId()
        );
    }

    public SecretariaResponseDTO atualizarSecretaria(Long id, SecretariaCreationDTO dto) {
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new SecretariaNaoEncontradoException(id));

        secretaria.setNome(dto.nome());
        secretaria.setEmail(dto.email());
        secretaria.setMatricula(dto.matricula());

        secretariaRepository.save(secretaria);

        return new SecretariaResponseDTO(
            secretaria.getId(),
            secretaria.getNome(),
            secretaria.getEmail(),
            secretaria.getMatricula(),
            secretaria.getCargo().getId()
        );
    }

    private SecretariaResponseDTO converterParaResponseDTO(Secretaria secretaria){
        return new SecretariaResponseDTO(secretaria.getId(), secretaria.getNome(), secretaria.getEmail(), secretaria.getMatricula(), secretaria.getCargo().getId());
    }

    public void deletarSecretaria(Long id) {
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new SecretariaNaoEncontradoException(id));

        secretariaRepository.delete(secretaria);
    }

    
}
