package com.fatec.itu.agendasalas.dto.usersDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioResetSenhaEmailDTO(
    @Email (message= "O e-mail deve ser válido")
    @NotBlank(message = "Precisa informar um e-mail")
    String email
) {

}
