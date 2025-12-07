package com.fatec.itu.agendasalas.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.itu.agendasalas.entity.Sala;

public class SalaSpecification {

    private SalaSpecification() {
    }

    public static Specification<Sala> comFiltros(String nome, Long pisoId, Long tipoSalaId, Boolean disponibilidade) {
        return Specification.allOf(
            filtroPorNome(nome),
            filtroPorPiso(pisoId),
            filtroPorTipoSala(tipoSalaId),
            filtroPorDisponibilidade(disponibilidade)
        );
    }

    public static Specification<Sala> filtroPorNome(String nome) {
        return (root, query, builder) -> {
            if (nome == null || nome.isBlank()) {
                return null;
            }
            String nomeNormalizado = "%%%s%%".formatted(nome.trim().toUpperCase());
            return builder.like(builder.upper(root.get("nome")), nomeNormalizado);
        };
    }

    public static Specification<Sala> filtroPorPiso(Long pisoId) {
        return (root, query, builder) -> pisoId == null
            ? null
            : builder.equal(root.get("piso").get("id"), pisoId);
    }

    public static Specification<Sala> filtroPorTipoSala(Long tipoSalaId) {
        return (root, query, builder) -> tipoSalaId == null
            ? null
            : builder.equal(root.get("tipoSala").get("id"), tipoSalaId);
    }

    public static Specification<Sala> filtroPorDisponibilidade(Boolean disponibilidade) {
        return (root, query, builder) -> disponibilidade == null
            ? null
            : builder.equal(root.get("disponibilidade"), disponibilidade);
    }
}