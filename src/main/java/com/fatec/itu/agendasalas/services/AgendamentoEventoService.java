package com.fatec.itu.agendasalas.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;

@Service
public class AgendamentoEventoService {
        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private AgendamentoEventoRepository agendamentoEventoRepository;

        @Autowired
        private AgendamentoRepository agendamentoRepository;

        @Autowired
        private SalaRepository salaRepository;

        @Autowired
        private JanelasHorarioRepository janelasHorarioRepository;

        @Transactional
        public void criar(AgendamentoEventoCreationDTO dto) {
                Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                                () -> new RuntimeException("Usuário não encontrado com ID: "
                                                + dto.usuarioId()));

                Sala sala = salaRepository.findById(dto.salaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Sala não encontrada com ID: " + dto.salaId()));

                List<JanelasHorario> horariosEncontrados = janelasHorarioRepository
                                .findByIntervaloIdHorarios(dto.horarioInicio(), dto.horarioFim());

                List<Long> idsDeHorariosParaExcluirAgendamento =
                                horariosEncontrados.stream().map(h -> h.getId()).toList();

                List<Long> idsDeAgendamentoParaExcluir =
                                agendamentoRepository.findByDataAndJanelaHorario(dto.data(),
                                                idsDeHorariosParaExcluirAgendamento, dto.salaId());

                agendamentoRepository.deleteAllById(idsDeAgendamentoParaExcluir);

                for (int i = 0; i < horariosEncontrados.size(); i++) {
                        AgendamentoEvento proximoAgendamento = new AgendamentoEvento();

                        proximoAgendamento.setUsuario(usuario);
                        proximoAgendamento.setSala(sala);
                        proximoAgendamento.setData(dto.data());
                        proximoAgendamento.setIsEvento((dto.isEvento()));
                        proximoAgendamento.setJanelasHorario(horariosEncontrados.get(i));

                        agendamentoEventoRepository.save(proximoAgendamento);
                }
        }
}
