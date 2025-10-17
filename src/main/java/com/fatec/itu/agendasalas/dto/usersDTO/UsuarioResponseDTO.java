package com.fatec.itu.agendasalas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Long cargoId;

    //para consultar gets de admins, sem expor senhas e login
}
