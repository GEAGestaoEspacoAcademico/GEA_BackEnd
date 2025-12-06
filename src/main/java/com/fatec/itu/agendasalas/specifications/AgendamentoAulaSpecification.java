package com.fatec.itu.agendasalas.specifications;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoAulaFilterDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoAula_;
import com.fatec.itu.agendasalas.entity.Disciplina_;
import com.fatec.itu.agendasalas.entity.JanelasHorario_;
import com.fatec.itu.agendasalas.entity.Sala_;
import com.fatec.itu.agendasalas.entity.Usuario_;

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
                return builder.equal(root.get(AgendamentoAula_.USUARIO).get(Usuario_.ID), usuariosIds.get(0));
            }

            return root.get(AgendamentoAula_.USUARIO).get(Usuario_.ID).in(usuariosIds);          
        };
    }

     public static Specification<AgendamentoAula> filtroPorListaDeSalas(List<Long> salasIds){
        return (root, query, builder) -> {

            if(salasIds == null){
                return null;
            }
            if(salasIds.size()==1){
                return builder.equal(root.get(AgendamentoAula_.SALA).get(Sala_.ID), salasIds.get(0));
            }

            return root.get(AgendamentoAula_.SALA).get(Sala_.ID).in(salasIds);          
        };
    } 
    
    public static Specification<AgendamentoAula> filtroPorListaDeDisciplinas(List<Long> disciplinasIds){
        return (root, query, builder) ->{
            if(disciplinasIds==null){
                return null;
            }
            if(disciplinasIds.size() == 1){
                return builder.equal(root.get(AgendamentoAula_.DISCIPLINA).get(Disciplina_.ID), disciplinasIds.get(0));
            }
            return root.get(AgendamentoAula_.DISCIPLINA).get(Disciplina_.ID).in(disciplinasIds);
        };
    }

    public static Specification<AgendamentoAula> filtroPorIntervaloDeDatas(LocalDate dataInicio, LocalDate dataFim){
        return (root, query, builder) ->{
            if(dataInicio==null || dataFim == null){
                return null;
            }
            return builder.between(
                root.get(AgendamentoAula_.DATA),
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
                return builder.equal(root.get(AgendamentoAula_.JANELAS_HORARIO).get(JanelasHorario_.ID), janelasHorarioIds.get(0));
            }
            return root.get(AgendamentoAula_.JANELAS_HORARIO).get(JanelasHorario_.ID).in(janelasHorarioIds);
        };
    }



}
