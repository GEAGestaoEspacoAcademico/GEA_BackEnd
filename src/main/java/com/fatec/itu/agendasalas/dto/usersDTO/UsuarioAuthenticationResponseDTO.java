package com.fatec.itu.agendasalas.dto.usersDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAuthenticationResponseDTO {

    private Long id;
    private String nome;
    private String cargo;
    //private String token; -> usar com JWT.

    public UsuarioAuthenticationResponseDTO(String nome, Long id, String cargo){
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }
}
