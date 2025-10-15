package com.fatec.itu.agendasalas.dto;

public class UsuarioResponseDTO {
    private long id;
    private String nome;
    private String email;
    private long cargoId;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public long getCargoId() {
        return cargoId;
    }
    public void setCargoId(long cargoId) {
        this.cargoId = cargoId;
    }

    
}
