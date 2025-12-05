package com.fatec.itu.agendasalas.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.itu.agendasalas.entity.AgendamentoAula;

public class AgendamentoAulaSpecification {

    public static Specification<AgendamentoAula> filtroPorListaDeUsuarios(List<Long> usuariosIds){
        return (root, query, builder) -> {

            usuariosIds!=null ? return builder.equals(root.get(AgendamentoAula_))
            if(usuariosIds!=null){
                return
            }
        }
    }

}
