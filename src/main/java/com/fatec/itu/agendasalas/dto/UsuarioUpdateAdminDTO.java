package com.fatec.itu.agendasalas.dto;

import lombok.Getter;
import lombok.Setter;

//aqui somente admins poderão usar, pois não queremos que um usuario mude seu cargo.
@Getter
@Setter
public class UsuarioUpdateAdminDTO {
    private String nome;
    private String email;
    private Long cargoId;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getCargoId() {
        return cargoId;
    }
    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }


    
}
