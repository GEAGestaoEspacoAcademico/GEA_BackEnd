package com.fatec.itu.agendasalas.dto.cargoDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CargoResponseDTO {
    private Long cargoId;
    private String cargoNome;

    public CargoResponseDTO(Long id, String nome){
        this.cargoId = id;
        this.cargoNome = nome;
    }
}
