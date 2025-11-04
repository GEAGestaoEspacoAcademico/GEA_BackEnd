package com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO;

import jakarta.validation.constraints.NotEmpty;

public record AuxiliarDocenteCreationDTO(
    @NotEmpty(message = "Login é obrigatório")
    String login,

    @NotEmpty(message = "Nome é obrigatório")
    String nome,

    @NotEmpty(message = "E-mail é obrigatório")
    String email,

    @NotEmpty(message = "Senha é obrigatória")
    String senha,
    
    @NotEmpty(message= "Area é obrigatória")
    String area
) {}