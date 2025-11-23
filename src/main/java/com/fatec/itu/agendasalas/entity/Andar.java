package com.fatec.itu.agendasalas.entity;

/**
 * Classe legada `Andar` mantida por compatibilidade durante a migração para `Piso`.
 * Não é mapeada como entidade JPA — use `Piso` para persistência.
 */
@Deprecated
public final class Andar {
    private Andar() {}
}

