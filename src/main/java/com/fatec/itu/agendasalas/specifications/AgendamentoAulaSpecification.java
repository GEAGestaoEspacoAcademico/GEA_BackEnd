package com.fatec.itu.agendasalas.specifications;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaFilterDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;

public class AgendamentoAulaSpecification {

    public static Specification<AgendamentoAula> porFiltros(AgendamentoAulaFilterDTO filtro) {

         return Specification.allOf(
            filtroPorListaDeUsuarios(filtro.usuarioIds()),
            filtroPorListaDeJanelasHorario(filtro.janelaHorarioIds()),
            filtroPorListaDeSalas(filtro.salaIds()),
            filtroPorListaDeDisciplinas(filtro.disciplinaIds()),
            filtroPorIntervaloDeDatas(filtro.dataInicio(), filtro.dataFim())
        );

        
    }


    public static Specification<AgendamentoAula> filtroPorListaDeUsuarios(List<Long> usuariosIds){
        return (root, query, builder) -> {

            if(usuariosIds == null){
                return null;
            }
            if(usuariosIds.size()==1){
                return builder.equal(root.get("usuario").get("id"), usuariosIds.get(0));
            }

            return root.get("usario").get("id").in(usuariosIds);          
        };
    }

     public static Specification<AgendamentoAula> filtroPorListaDeSalas(List<Long> salasIds){
        return (root, query, builder) -> {

            if(salasIds == null){
                return null;
            }
            if(salasIds.size()==1){
                return builder.equal(root.get("sala").get("id"), salasIds.get(0));
            }

            return root.get("sala").get("id").in(salasIds);          
        };
    } 
    
    public static Specification<AgendamentoAula> filtroPorListaDeDisciplinas(List<Long> disciplinasIds){
        return (root, query, builder) ->{
            if(disciplinasIds==null){
                return null;
            }
            if(disciplinasIds.size() == 1){
                return builder.equal(root.get("disciplina").get("id"), disciplinasIds.get(0));
            }
            return root.get("disciplina").get("id").in(disciplinasIds);
        };
    }

    public static Specification<AgendamentoAula> filtroPorIntervaloDeDatas(LocalDate dataInicio, LocalDate dataFim){
        return (root, query, builder) ->{
            if(dataInicio==null || dataFim == null){
                return null;
            }
            return builder.between(
                root.get("data"),
                dataInicio,
                dataFim
            );
        };
    }

    public static Specification<AgendamentoAula> filtroPorListaDeJanelasHorario(List<Long> janelasHorarioIds){
        return (root, query, builder) -> {
            if(janelasHorarioIds==null){
                return null;
            }
            if(janelasHorarioIds.size()==1){
                return builder.equal(root.get("janelasHorario").get("id"), janelasHorarioIds.get(0));
            }
            return root.get("janelasHorario").get("id").in(janelasHorarioIds);
        };
    }



}
