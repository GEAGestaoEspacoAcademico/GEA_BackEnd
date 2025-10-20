package com.fatec.itu.agendasalas.dto;

public class CoordenadorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private int registroCoordenacao;
    private Long cargoId;

    public CoordenadorResponseDTO() {}

    public CoordenadorResponseDTO(Long id, String nome, String email, int registroCoordenacao, Long cargoId) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.registroCoordenacao = registroCoordenacao;
        this.cargoId = cargoId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getRegistroCoordenacao() { return registroCoordenacao; }
    public void setRegistroCoordenacao(int registroCoordenacao) { this.registroCoordenacao = registroCoordenacao; }
    public Long getCargoId() { return cargoId; }
    public void setCargoId(Long cargoId) { this.cargoId = cargoId; }
}
