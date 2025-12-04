package com.fatec.itu.agendasalas.exceptions;

public class RegistroProfessorDuplicadoException extends RuntimeException {

    public RegistroProfessorDuplicadoException(Long idProfessor, Long registroProfessor) {
        super("Tentativa de atualizar o professor id: " + idProfessor + " com o registro ja existente: " + registroProfessor);
    }

    
}
