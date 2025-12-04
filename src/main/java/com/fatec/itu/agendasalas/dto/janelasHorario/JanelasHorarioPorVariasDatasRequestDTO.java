package com.fatec.itu.agendasalas.dto.janelasHorario;

import java.util.List;

public record JanelasHorarioPorVariasDatasRequestDTO(
    List<String> datas,
    Long salaId
){}
