package com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO;

import com.fatec.itu.agendasalas.validators.EmailValido;
import com.fatec.itu.agendasalas.validators.LoginValido;
import com.fatec.itu.agendasalas.validators.SenhaValida;

import jakarta.validation.constraints.NotBlank;

public record AuxiliarDocenteCreationDTO(

    @NotBlank(message = "E-mail é obrigatório")
    @EmailValido(message="Envie um e-mail válido")
    String email,

    @NotBlank(message = "Login é obrigatório")
    @LoginValido
    String login,

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message= "Area é obrigatória")
    String area,

    @NotBlank(message = "Senha é obrigatória")
    @SenhaValida
    String senha
    
   
) {}