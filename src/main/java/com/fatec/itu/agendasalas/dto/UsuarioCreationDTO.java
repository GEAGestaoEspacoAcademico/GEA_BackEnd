package com.fatec.itu.agendasalas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreationDTO {
    
    private Long id;
    
    private String nome;
    private String email;
    private String login;
    private String senha;

    
}
