package com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO;

import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;

public record AuxiliarDocenteCreationDTO(
        UsuarioCreationDTO userCreationDTO,
        String area
) {}
