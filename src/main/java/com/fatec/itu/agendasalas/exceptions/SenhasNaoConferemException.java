package com.fatec.itu.agendasalas.exceptions;

public class SenhasNaoConferemException extends RuntimeException {

    public SenhasNaoConferemException() {
        super("Ao tentar redefinir a senha foram enviadas senhas que não coincidem");
    }


}
