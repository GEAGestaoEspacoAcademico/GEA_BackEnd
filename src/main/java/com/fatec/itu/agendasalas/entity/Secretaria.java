package com.fatec.itu.agendasalas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "SECRETARIA")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
public class Secretaria extends Usuario{

    @Column(name = "matricula")
    private Long matricula;
    
    
}
