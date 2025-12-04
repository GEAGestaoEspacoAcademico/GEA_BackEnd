package com.fatec.itu.agendasalas.exceptions;

public class ListaDeAulasVaziaNotificacaoException extends RuntimeException {

    public ListaDeAulasVaziaNotificacaoException() {
        super("Tentativa de enviar uma notificação via e-mail, porém a lista de aulas agendadas estava vazia");
    }

    
}
