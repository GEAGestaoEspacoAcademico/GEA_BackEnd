package com.fatec.itu.agendasalas.interfaces;

import jakarta.mail.MessagingException;

public interface UsuarioCadastravel<Request, Response> {    
     public Response cadastrarUsuario(Request usuarioCadastro) throws MessagingException;
}
