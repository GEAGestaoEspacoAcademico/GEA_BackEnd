package com.fatec.itu.agendasalas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioCreationDTO {
    
    private Long id;
    
    private String nome;
    private String email;
    private String login;
    private String senha;

    
}
