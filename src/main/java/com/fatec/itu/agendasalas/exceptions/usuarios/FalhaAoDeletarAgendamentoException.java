package com.fatec.itu.agendasalas.exceptions.usuarios;

public class FalhaAoDeletarAgendamentoException extends RuntimeException{
    public FalhaAoDeletarAgendamentoException(Long usuarioId, Throwable cause){
        super("Falha ao deletar agendamentos do usuário id=" + usuarioId, cause);
    }
}
