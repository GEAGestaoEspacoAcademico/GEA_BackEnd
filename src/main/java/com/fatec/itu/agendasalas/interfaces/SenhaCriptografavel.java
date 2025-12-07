package com.fatec.itu.agendasalas.interfaces;

public interface SenhaCriptografavel {
    String criptografarSenha(String senha);
    boolean matches(String senha, String senhaCriptografada);
}
