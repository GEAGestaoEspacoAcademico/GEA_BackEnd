package com.fatec.itu.agendasalas.dto;

public class CoordenadorCreationDTO {
    private long usuarioId;
    private int registroCoordenacao;

    public CoordenadorCreationDTO() {}

    public CoordenadorCreationDTO(long usuarioId, int registroCoordenacao) {
        this.usuarioId = usuarioId;
        this.registroCoordenacao = registroCoordenacao;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getRegistroCoordenacao() {
        return registroCoordenacao;
    }

    public void setRegistroCoordenacao(int registroCoordenacao) {
        this.registroCoordenacao = registroCoordenacao;
    }
}
