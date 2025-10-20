package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder cryptPasswordEncoder;

    @Autowired
    private CargoRepository cargoRepository;

    public UsuarioResponseDTO cadastrarUsuario(UsuarioCreationDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.getLogin(), usuarioDTO.getEmail(), usuarioDTO.getNome());
        String senhaCriptografada = cryptPasswordEncoder.encode(usuarioDTO.getSenha());
        usuario.setSenha(senhaCriptografada);
        Cargo cargo = cargoRepository.findByNome("USER")
                .orElseThrow(() -> new RuntimeException("CARGO USER NÃO ENCONTRADO"));
        usuario.setCargo(cargo);

        usuarioRepository.save(usuario);
        return conversaoUsuarioParaResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosResponseDTO = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            UsuarioResponseDTO usuarioResponseDTO = conversaoUsuarioParaResponseDTO(usuario);
            listaUsuariosResponseDTO.add(usuarioResponseDTO);
        }
        return listaUsuariosResponseDTO;
    }

    private UsuarioResponseDTO conversaoUsuarioParaResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setCargoId(usuario.getCargo().getId());
        return responseDTO;
    }

    public UsuarioResponseDTO buscarUsuarioPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return conversaoUsuarioParaResponseDTO(usuario);
    }

    public void atualizarUsuario(UsuarioUpdateAdminDTO usuarioUpdateAdminDTO, Long id) {
        Usuario auxiliar = usuarioRepository.getReferenceById(id);

        if (usuarioUpdateAdminDTO.getNome() != null)
            auxiliar.setNome(usuarioUpdateAdminDTO.getNome());
        if (usuarioUpdateAdminDTO.getEmail() != null) {
            if (!usuarioRepository.existsByEmailAndIdNot(usuarioUpdateAdminDTO.getEmail(), id)) {
                auxiliar.setEmail(usuarioUpdateAdminDTO.getEmail());
            } else {
                throw new RuntimeException("Tentando usar email já cadastrado");
            }
        }
        if (usuarioUpdateAdminDTO.getCargoId() != null) {
            Cargo cargo = cargoRepository.findById(usuarioUpdateAdminDTO.getCargoId())
                    .orElseThrow(() -> new RuntimeException("Não encontrado cargo desejado"));
            auxiliar.setCargo(cargo);
        }

        usuarioRepository.save(auxiliar);
    }
}
