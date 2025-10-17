package com.fatec.itu.agendasalas.dto.usersDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//aqui somente admins poderão usar, pois não queremos que um usuario mude seu cargo.
@Getter
@Setter
@NoArgsConstructor
public class UsuarioUpdateAdminDTO {
    private String nome;
    private String email;
    private Long cargoId;


    
}
