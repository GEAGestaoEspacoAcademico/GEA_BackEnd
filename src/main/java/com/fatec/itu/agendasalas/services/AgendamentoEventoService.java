package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoDiasAgendadosDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoResponseDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.DEVAgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.DEVAgendamentoEventoDiasAgendadosDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.exceptions.JanelasHorarioNaoEncontradaException;
import com.fatec.itu.agendasalas.exceptions.SalaNaoEncontradaException;
import com.fatec.itu.agendasalas.exceptions.UsuarioNaoEncontradoException;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.AgendamentoRepository;
import com.fatec.itu.agendasalas.repositories.EventoRepository;
import com.fatec.itu.agendasalas.repositories.JanelasHorarioRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
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
        private EventoRepository eventoRepository;

        @Autowired
        private JanelasHorarioRepository janelasHorarioRepository;

        @Autowired
        private RecorrenciaRepository recorrenciaRepository;

        @Autowired
        private AgendamentoConflitoService agendamentoConflitoService;

        @Autowired
        private AgendamentoAulaService agendamentoAulaService;

        @Transactional
        public void criar(AgendamentoEventoCreationDTO dto) {
                Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(
                                () -> new RuntimeException("Usuário não encontrado com ID: "
                                                + dto.usuarioId()));

                Sala sala = salaRepository.findById(dto.salaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Sala não encontrada com ID: " + dto.salaId()));

                Evento evento = eventoRepository.findByNome(dto.eventoNome()).orElseThrow(()->new RuntimeException("Aaoao"));

                 

            List<AgendamentoEventoDiasAgendadosDTO> diasAgendadosDTO = dto.dias();
             LocalDate diaInicial = diasAgendadosDTO.stream()
            .map(AgendamentoEventoDiasAgendadosDTO::dia)
            .min(LocalDate::compareTo)
            .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        
            LocalDate diaFinal = diasAgendadosDTO.stream()
            .map(AgendamentoEventoDiasAgendadosDTO::dia)
            .max(LocalDate::compareTo)
            .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        
            Recorrencia recorrencia = recorrenciaRepository.save(new Recorrencia(diaInicial, diaFinal));

            for(AgendamentoEventoDiasAgendadosDTO dia : diasAgendadosDTO){
                LocalDate diaAgendado = dia.dia();
                LocalTime horaInicioDoDiaAgendado = dia.horaInicio();
                LocalTime horaFimDoDiaAgendado = dia.horaFim();

                List<JanelasHorario> horariosEncontrados = janelasHorarioRepository
                                .findByIntervaloIdHorarios(horaInicioDoDiaAgendado, horaFimDoDiaAgendado);


                for (int i = 0; i < horariosEncontrados.size(); i++) {
                      AgendamentoAula agendamentoAulaConflitante = agendamentoConflitoService.filtrarAulasConflitantes(sala.getId(), diaAgendado, horariosEncontrados.get(i).getId());
                        if(agendamentoAulaConflitante!=null){
                            //aqui preciso cancelar a aula, por enquanto vou deixar para excluir
                            //pra logica seria: definir o status como CANCELADO, criar um registro na tabela AGENDAMENTOS_CANCELADOS
                            agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaConflitante.getId());
                        }
                        if(agendamentoConflitoService.existeEventoNoHorario(sala.getId(), diaAgendado, horariosEncontrados.get(i).getId())){
                            throw new ConflitoAoAgendarException(sala.getNome(), diaAgendado, horariosEncontrados.get(i).getHoraInicio(), horariosEncontrados.get(i).getHoraFim());
                        }

                        AgendamentoEvento proximoAgendamento = new AgendamentoEvento();

                        proximoAgendamento.setUsuario(usuario);
                        proximoAgendamento.setSala(sala);
                        proximoAgendamento.setData(diaAgendado);
                        proximoAgendamento.setEvento(evento);
                        proximoAgendamento.setIsEvento(true);
                        proximoAgendamento.setJanelasHorario(horariosEncontrados.get(i));
                        proximoAgendamento.setStatus("ATIVO");
                        proximoAgendamento.setSolicitante(usuario.getNome());
                        proximoAgendamento.preencherDiaDaSemana();
                        proximoAgendamento.setRecorrencia(recorrencia);

                        agendamentoEventoRepository.save(proximoAgendamento);
                }
            }          
               
                /* 
                List<Long> idsDeHorariosParaExcluirAgendamento =
                                horariosEncontrados.stream().map(h -> h.getId()).toList();

                List<Long> idsDeAgendamentoParaExcluir =
                                agendamentoRepository.findByDataAndJanelaHorario(dto.data(),
                                                idsDeHorariosParaExcluirAgendamento, dto.salaId());

                agendamentoRepository.deleteAllById(idsDeAgendamentoParaExcluir);
                */
               
        }

    @Transactional
    public void criarAgendamentoEvento(DEVAgendamentoEventoCreationDTO agendamentoEventoCreationDTO){ 
       
        LocalDate diaInicial = agendamentoEventoCreationDTO.diasAgendados().stream()
        .map(DEVAgendamentoEventoDiasAgendadosDTO::dia)
        .min(LocalDate::compareTo)
        .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        
        LocalDate diaFinal = agendamentoEventoCreationDTO.diasAgendados().stream()
        .map(DEVAgendamentoEventoDiasAgendadosDTO::dia)
        .max(LocalDate::compareTo)
        .orElseThrow(()-> new RuntimeException("Lista de dias vazia"));
        

        Recorrencia recorrencia = new Recorrencia(diaInicial, diaFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);

        Usuario usuario = usuarioRepository.findById(agendamentoEventoCreationDTO.usuario())
            .orElseThrow(()-> new UsuarioNaoEncontradoException(agendamentoEventoCreationDTO.usuario()));
       
        Evento evento = eventoRepository.findByNome(agendamentoEventoCreationDTO.nomeEvento())
            .orElseThrow(()-> new RuntimeException("EVENTO NAO ENCONTRADO"));
        
        Sala sala = salaRepository.findById(agendamentoEventoCreationDTO.localId())
            .orElseThrow(()-> new SalaNaoEncontradaException(agendamentoEventoCreationDTO.localId()));

        for(DEVAgendamentoEventoDiasAgendadosDTO agendamentoDia :  agendamentoEventoCreationDTO.diasAgendados()){

            for(Long janelaId : agendamentoDia.janelasHorarioId()){
                JanelasHorario janela = janelasHorarioRepository.findById(janelaId).orElseThrow(()-> new JanelasHorarioNaoEncontradaException(janelaId));
                AgendamentoAula agendamentoAulaConflitante = agendamentoConflitoService.filtrarAulasConflitantes(sala.getId(), agendamentoDia.dia(), janelaId);
                if(agendamentoAulaConflitante!=null){
                    //aqui preciso cancelar a aula, por enquanto vou deixar para excluir
                    //pra logica seria: definir o status como CANCELADO, criar um registro na tabela AGENDAMENTOS_CANCELADOS
                    agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaConflitante.getId());
                }
                if(agendamentoConflitoService.existeEventoNoHorario(sala.getId(), agendamentoDia.dia(), janelaId)){
                    throw new ConflitoAoAgendarException(sala.getNome(), agendamentoDia.dia(), janela.getHoraInicio(), janela.getHoraFim());
                }

                AgendamentoEvento agendamentoEvento = new AgendamentoEvento();
                agendamentoEvento.setUsuario(usuario);
                agendamentoEvento.setSala(sala);
                agendamentoEvento.setEvento(evento);
                agendamentoEvento.setJanelasHorario(janela);
                agendamentoEvento.setData(agendamentoDia.dia());
                agendamentoEvento.preencherDiaDaSemana();
                agendamentoEvento.setRecorrencia(recorrenciaSalva);
                agendamentoEvento.setIsEvento(true);
                agendamentoEvento.setStatus("ATIVO");
                agendamentoEvento.setSolicitante(usuario.getNome()); 
                //estou considerando que a propria pessoa que agendou é a solicitante, pois na tela não tem nada disso.
                agendamentoEventoRepository.save(agendamentoEvento);

            }

        }
           
    }

    private AgendamentoEventoResponseDTO converterParaResponseDTO(AgendamentoEvento agendamentoEvento){
        return new AgendamentoEventoResponseDTO(
            agendamentoEvento.getId(),
            agendamentoEvento.getUsuario().getNome(),
            agendamentoEvento.getSala().getNome(),
            agendamentoEvento.getEvento().getNome(),
            agendamentoEvento.getData(),
            agendamentoEvento.getDiaDaSemana(),
            agendamentoEvento.getJanelasHorario().getHoraInicio(),
            agendamentoEvento.getJanelasHorario().getHoraFim(),
            agendamentoEvento.getIsEvento().booleanValue(),
            agendamentoEvento.getRecorrencia().getId(),
            agendamentoEvento.getStatus(),
            agendamentoEvento.getSolicitante()
        );  
    }

    public List<AgendamentoEventoResponseDTO> listarAgendamentosEvento(){
         return agendamentoEventoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletarAgendamentoEventoEspecifico(Long agendamentoEventoId){
        agendamentoEventoRepository.deleteById(agendamentoEventoId);
    }

    public void deletarAgendamentoEventoPelaRecorrencia(Long recorrenciaId)
    {
        
    }  

    }

