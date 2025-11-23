package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAlterarSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioFuncionarioDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.entity.Secretaria;
import com.fatec.itu.agendasalas.exceptions.EmailJaCadastradoException;
import com.fatec.itu.agendasalas.interfaces.UsuarioCadastravel;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UsuarioCadastravel<UsuarioCreationDTO, UsuarioResponseDTO> {

    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncryptService passwordEncryptService;

    @Autowired
    private CargoRepository cargoRepository;

       @Override  
       public UsuarioResponseDTO cadastrarUsuario(UsuarioCreationDTO usuarioDTO){

        passwordEncryptService.validarSenha(usuarioDTO.usuarioSenha());

        Usuario usuario = new Usuario(usuarioDTO.usuarioLogin(), usuarioDTO.usuarioEmail(), usuarioDTO.usuarioNome());
        usuario.setSenha(passwordEncryptService.criptografarSenha(usuarioDTO.usuarioSenha()));
        Cargo cargo = cargoRepository.findByNome("USER").orElseThrow(()-> new RuntimeException("CARGO USER NÃO ENCONTRADO"));
        usuario.setCargo(cargo);

        usuarioRepository.save(usuario);
        return conversaoUsuarioParaResponseDTO(usuario);

    }
    
    public List<UsuarioFuncionarioDTO> listarFuncionarios(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioFuncionarioDTO> lista = new ArrayList<>();

        for(Usuario usuario : listaUsuarios){
            if(usuario.getCargo()==null || usuario.getCargo().getNome()==null) continue;
            String nomeCargo = usuario.getCargo().getNome().trim().toUpperCase();
            boolean isFuncionario = nomeCargo.equals("AUXILIAR_DOCENTE") || nomeCargo.equals("PROFESSOR")
                    || nomeCargo.equals("COORDENADOR") || nomeCargo.equals("SECRETARIA");
            if(!isFuncionario) continue;

            Long registroCoordenacao = null;
            Long registroProfessor = null;
            Long matricula = null;

            if(usuario instanceof Coordenador){
                registroCoordenacao = ((Coordenador) usuario).getRegistroCoordenacao();
            } else if(usuario instanceof Professor){
                registroProfessor = ((Professor) usuario).getRegistroProfessor();
            } else if(usuario instanceof Secretaria){
                matricula = ((Secretaria) usuario).getMatricula();
            }

            Long registro = null;
            if (registroCoordenacao != null) {
                registro = registroCoordenacao;
            } else if (registroProfessor != null) {
                registro = registroProfessor;
            } else if (matricula != null) {
                registro = matricula;
            }

            UsuarioFuncionarioDTO dto = new UsuarioFuncionarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                registro,
                usuario.getCargo() != null ? usuario.getCargo().getId() : null,
                usuario.getCargo() != null ? usuario.getCargo().getNome() : null
            );

            lista.add(dto);
        }

        return lista;
    }
    

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioResponseDTO> listaUsuariosResponseDTO =  new ArrayList<>(); 
        for(Usuario usuario :listaUsuarios){
            UsuarioResponseDTO usuarioResponseDTO = conversaoUsuarioParaResponseDTO(usuario); 
            listaUsuariosResponseDTO.add(usuarioResponseDTO);
        }
        return listaUsuariosResponseDTO;
    }
    
    private UsuarioResponseDTO conversaoUsuarioParaResponseDTO(Usuario usuario){
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getCargo() != null ? usuario.getCargo().getId() : null
        );
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("Usuario não encontrado"));
        return conversaoUsuarioParaResponseDTO(usuario);
        
    }

    public void atualizarUsuario(UsuarioUpdateAdminDTO usuarioUpdateAdminDTO, Long id){
        Usuario auxiliar = usuarioRepository.getReferenceById(id);
        String novoEmail = usuarioUpdateAdminDTO.usuarioEmail();
        if(usuarioUpdateAdminDTO.usuarioNome()!=null) auxiliar.setNome(usuarioUpdateAdminDTO.usuarioNome());
        
        if (novoEmail != null && !novoEmail.equals(auxiliar.getEmail())) {

            boolean emailJaUsadoPorOutroUsuario = usuarioRepository.existsByEmailAndIdNot(novoEmail, id);
            if(emailJaUsadoPorOutroUsuario){
                 throw new EmailJaCadastradoException(novoEmail);
            }
            auxiliar.setEmail(usuarioUpdateAdminDTO.usuarioEmail());
          
        }
        
        if(usuarioUpdateAdminDTO.cargoId() != null){
            Cargo cargo = cargoRepository.findById(usuarioUpdateAdminDTO.cargoId())
            .orElseThrow(()-> new RuntimeException("Não encontrado cargo desejado"));
            auxiliar.setCargo(cargo);
        }

        usuarioRepository.save(auxiliar);
    }

    public boolean existeEmailCadastrado(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void alterarSenha(Long usuarioId, UsuarioAlterarSenhaDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncryptService.matches(dto.senhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        if (!dto.novaSenha().equals(dto.repetirNovaSenha())) {
            throw new RuntimeException("A nova senha e a de confirmação não coincidem");
        }

        passwordEncryptService.validarSenha(dto.novaSenha());

        if (passwordEncryptService.matches(dto.novaSenha(), usuario.getSenha())){
            throw new RuntimeException("A nova senha não pode ser igual à última utilizada.");
        }

        usuario.setSenha(passwordEncryptService.criptografarSenha(dto.novaSenha()));

        usuarioRepository.save(usuario);
    }
}
