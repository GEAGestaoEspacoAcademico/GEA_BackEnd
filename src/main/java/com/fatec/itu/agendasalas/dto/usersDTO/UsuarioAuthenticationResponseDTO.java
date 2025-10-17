package com.fatec.itu.agendasalas.dto.usersDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAuthenticationResponseDTO {

    private String nome;
    //private String token; -> usar com JWT.

    public UsuarioAuthenticationResponseDTO(String nome){
        this.nome = nome;
    }
}
