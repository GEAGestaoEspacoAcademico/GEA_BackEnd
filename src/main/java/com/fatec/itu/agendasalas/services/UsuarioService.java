package com.fatec.itu.agendasalas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioAlterarSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioRedefinirSenhaDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioFuncionarioDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioUpdateAdminDTO;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.entity.PasswordResetToken;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.entity.Coordenador;
import com.fatec.itu.agendasalas.entity.Professor;
import com.fatec.itu.agendasalas.entity.Secretaria;
import com.fatec.itu.agendasalas.exceptions.EmailJaCadastradoException;
import com.fatec.itu.agendasalas.exceptions.SenhasNaoConferemException;
import com.fatec.itu.agendasalas.interfaces.UsuarioCadastravel;
import com.fatec.itu.agendasalas.repositories.CargoRepository;
import com.fatec.itu.agendasalas.repositories.PasswordResetTokenRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.DisciplinaRepository;
import com.fatec.itu.agendasalas.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import com.fatec.itu.agendasalas.exceptions.usuarios.UsuarioNaoEncontradoException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDeletarAgendamentoException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDesvincularDisciplinaException;
import com.fatec.itu.agendasalas.exceptions.usuarios.FalhaAoDesvincularCursoException;

@Service
public class UsuarioService implements UsuarioCadastravel<UsuarioCreationDTO, UsuarioResponseDTO> {

    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncryptService passwordEncryptService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
  
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

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

   public void redefinirSenhaByAD(Long usuarioId, UsuarioRedefinirSenhaDTO dto) {

    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

    String novaSenha = dto.novaSenha();

    passwordEncryptService.validarSenha(novaSenha);

    if (passwordEncryptService.matches(novaSenha, usuario.getSenha())) {
        throw new RuntimeException("A nova senha não pode ser igual à senha atual.");
    }

    String senhaCriptografada = passwordEncryptService.criptografarSenha(novaSenha);
    usuario.setSenha(senhaCriptografada);
    usuarioRepository.save(usuario);
}



    public Usuario buscarUsuarioPeloEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        return usuario;  
    }


    public void redefinirSenha(UsuarioRedefinirSenhaDTO dto) {
       if(!dto.senha().equals(dto.repetirSenha())){
            throw new SenhasNaoConferemException();
       }
       passwordEncryptService.validarSenha(dto.senha());
       
       PasswordResetToken passToken = passwordResetTokenRepository.findByToken(dto.token());
       Usuario usuario = passToken.getUsuario();
       usuario.setSenha(passwordEncryptService.criptografarSenha(dto.senha()));
       usuarioRepository.save(usuario);
       passwordResetTokenRepository.delete(passToken); 
    }

    @Transactional
    public void deletarUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

        List<com.fatec.itu.agendasalas.entity.Agendamento> agendamentos = agendamentoRepository.findByUsuarioId(usuarioId);
        if(agendamentos != null && !agendamentos.isEmpty()){
            try{
                agendamentoRepository.deleteAll(agendamentos);
            }catch(Exception e){
                throw new FalhaAoDeletarAgendamentoException(usuarioId, e);
            }
        }

        List<com.fatec.itu.agendasalas.entity.Disciplina> disciplinas = disciplinaRepository.findByProfessorId(usuarioId);
        if(disciplinas != null && !disciplinas.isEmpty()){
            try{
                for(com.fatec.itu.agendasalas.entity.Disciplina d : disciplinas){
                    d.setProfessor(null);
                }
                disciplinaRepository.saveAll(disciplinas);
            }catch(Exception e){
                throw new FalhaAoDesvincularDisciplinaException(usuarioId, e);
            }
        }

        List<com.fatec.itu.agendasalas.entity.Curso> cursos = cursoRepository.findByCoordenadorId(usuarioId);
        if(cursos != null && !cursos.isEmpty()){
            try{
                for(com.fatec.itu.agendasalas.entity.Curso c : cursos){
                    c.setCoordenador(null);
                }
                cursoRepository.saveAll(cursos);
            }catch(Exception e){
                throw new FalhaAoDesvincularCursoException(usuarioId, e);
            }
        }

        try{
            usuarioRepository.delete(usuario);
        }catch(Exception e){
            throw new RuntimeException("Falha ao deletar usuário id=" + usuarioId, e);
        }
    }
}
