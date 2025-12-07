package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaCreationDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaResponseDTO;
import com.fatec.itu.agendasalas.dto.secretariaDTO.SecretariaUpdateDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Secretaria;
import com.fatec.itu.agendasalas.exceptions.EmailJaCadastradoException;
import com.fatec.itu.agendasalas.exceptions.MatriculaDuplicadaSecretariaException;
import com.fatec.itu.agendasalas.exceptions.SecretariaNaoEncontradoException;
import com.fatec.itu.agendasalas.interfaces.UsuarioCadastravel;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.SecretariaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SecretariaService implements UsuarioCadastravel<SecretariaCreationDTO, SecretariaResponseDTO> {

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncryptService passwordEncryptService;

    @Autowired
    private NotificacaoService notificacaoService;

    public List<SecretariaResponseDTO> listarSecretarios() {
        return secretariaRepository.findAll().stream()
        .map(this::converterParaResponseDTO)
        .toList();
    }

    public SecretariaResponseDTO buscarSecretarioPorId(Long id){
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Secretaria com id " + id + " não encontrado"));
        return converterParaResponseDTO(secretaria);
    }

  
    public SecretariaResponseDTO atualizarSecretaria(Long id, SecretariaUpdateDTO dto) {
        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new SecretariaNaoEncontradoException(id));

        if(dto.nome()!=null && !dto.nome().isEmpty()) secretaria.setNome(dto.nome());
        if(dto.email()!=null && !dto.nome().isEmpty()) secretaria.setEmail(dto.email());
        if(dto.matricula()!=null){
            if(secretariaRepository.existsByMatricula(dto.matricula())) {
                throw new MatriculaDuplicadaSecretariaException(dto.matricula());
            }
            secretaria.setMatricula(dto.matricula());
        }
        secretariaRepository.save(secretaria);

        return converterParaResponseDTO(secretaria);
    }

    private SecretariaResponseDTO converterParaResponseDTO(Secretaria secretaria){
        return new SecretariaResponseDTO(secretaria.getId(), secretaria.getNome(), secretaria.getLogin(), secretaria.getEmail(), secretaria.getMatricula(), secretaria.getCargo().getId());
    }

    @Deprecated(since="07/12/2025", forRemoval=true)
    //use o metodo de usuarioService
    public void deletarSecretaria(Long id) {

        Secretaria secretaria = secretariaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Secretaria com id " + id + " não encontrado"));

        secretariaRepository.delete(secretaria);
    }


    private String gerarNovoLoginCasoJaExista(String login){
        String baseLogin = login;
        int suffix = 1;
        while (usuarioRepository.findByLogin(login).isPresent()) {
            suffix++;
            login = baseLogin + suffix;
        }
        return login;
    }

    @Override
    public SecretariaResponseDTO cadastrarUsuario(SecretariaCreationDTO dto) throws MessagingException {
        
        if(secretariaRepository.existsByMatricula(dto.matricula())) {
            throw new MatriculaDuplicadaSecretariaException(dto.matricula());
        }
        if(usuarioRepository.existsByEmail(dto.email())){
            throw new EmailJaCadastradoException(dto.email());
        }
       
        String login = gerarNovoLoginCasoJaExista(dto.login());
        Secretaria secretaria = new Secretaria();
        secretaria.setLogin(login);
        secretaria.setNome(dto.nome());
        secretaria.setSenha(passwordEncryptService.criptografarSenha(dto.senha()));
        secretaria.setEmail(dto.email());
        secretaria.setMatricula(dto.matricula());

        Cargo cargo = cargoRepository.findByNome("SECRETARIA")
            .orElseThrow(() -> new EntityNotFoundException("Cargo de nome: SECRETARIA não encontrado"));
        secretaria.setCargo(cargo);

        secretariaRepository.save(secretaria);
        notificacaoService.notificarCadastro(secretaria, dto.senha());

        return converterParaResponseDTO(secretaria);
    }
    
}
