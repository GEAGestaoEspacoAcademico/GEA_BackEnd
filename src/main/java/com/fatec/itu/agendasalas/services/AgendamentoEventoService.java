package com.fatec.itu.agendasalas.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoCreationDTO;
import com.fatec.itu.agendasalas.dto.agendamentosDTO.AgendamentoEventoResponseDTO;
import com.fatec.itu.agendasalas.entity.AgendamentoAula;
import com.fatec.itu.agendasalas.entity.AgendamentoEvento;
import com.fatec.itu.agendasalas.entity.Evento;
import com.fatec.itu.agendasalas.entity.JanelasHorario;
import com.fatec.itu.agendasalas.entity.Recorrencia;
import com.fatec.itu.agendasalas.entity.Sala;
import com.fatec.itu.agendasalas.entity.Usuario;
import com.fatec.itu.agendasalas.exceptions.ConflitoAoAgendarException;
import com.fatec.itu.agendasalas.repositories.AgendamentoEventoRepository;
import com.fatec.itu.agendasalas.repositories.EventoRepository;
import com.fatec.itu.agendasalas.repositories.RecorrenciaRepository;
import com.fatec.itu.agendasalas.repositories.SalaRepository;
import com.fatec.itu.agendasalas.repositories.UsuarioRepository;


@Service
public class AgendamentoEventoService {
    
    private AgendamentoEventoRepository agendamentoEventoRepository;
    private AgendamentoConflitoService agendamentoConflitoService;
    private JanelasHorarioService janelasHorarioService;
    private UsuarioRepository usuarioRepository;
    private SalaRepository salaRepository;
    private EventoRepository eventoRepository;
    private RecorrenciaRepository recorrenciaRepository;
    private AgendamentoAulaService agendamentoAulaService;

    public AgendamentoEventoService(
        AgendamentoEventoRepository agendamentoEventoRepository, 
        JanelasHorarioService janelasHorarioService,
        AgendamentoAulaService agendamentoAulaService, 
        UsuarioRepository usuarioRepository,
        SalaRepository salaRepository,
        EventoRepository eventoRepository,
        RecorrenciaRepository recorrenciaRepository,
        AgendamentoConflitoService agendamentoConflitoService){
            this.agendamentoEventoRepository = agendamentoEventoRepository;
            this.janelasHorarioService = janelasHorarioService;
            this.usuarioRepository = usuarioRepository;
            this.salaRepository = salaRepository;
            this.eventoRepository = eventoRepository;
            this.recorrenciaRepository = recorrenciaRepository;
            this.agendamentoConflitoService = agendamentoConflitoService;
            this.agendamentoAulaService = agendamentoAulaService;
        }

    @Transactional
    public void criarAgendamentoEvento(AgendamentoEventoCreationDTO agendamentoEventoCreationDTO) throws ConflitoAoAgendarException{ 
    
        List<JanelasHorario> janelasHorario = janelasHorarioService.buscaJanelaHorarioPelosHorariosInicioeFim(agendamentoEventoCreationDTO.horaInicio(), agendamentoEventoCreationDTO.horafim(), agendamentoEventoCreationDTO.todosHorarios());
        
        LocalDate dataInicial = agendamentoEventoCreationDTO.diaInicio(); 
        LocalDate dataFinal = agendamentoEventoCreationDTO.diaFim();
        Recorrencia recorrencia = new Recorrencia(dataInicial, dataFinal);
        Recorrencia recorrenciaSalva = recorrenciaRepository.save(recorrencia);


        Usuario usuario = usuarioRepository.getReferenceById(agendamentoEventoCreationDTO.usuario());
        Evento evento = eventoRepository.findByNome(agendamentoEventoCreationDTO.nomeEvento()).orElseThrow(()-> new RuntimeException("EVENTO NAO ENCONTRADA"));;
        Sala sala = salaRepository.findByNome(agendamentoEventoCreationDTO.local()).orElseThrow(()-> new RuntimeException("SALA NAO ENCONTRADA"));


        while(!dataInicial.isAfter(dataFinal)){

            for(JanelasHorario janela : janelasHorario){
                AgendamentoAula agendamentoAulaConflitante = agendamentoConflitoService.filtrarAulasConflitantes(sala.getId(), dataInicial, janela.getId());
                if(agendamentoAulaConflitante!=null){
                    //aqui preciso cancelar a aula, por enquanto vou deixar para excluir
                    //pra logica seria: definir o status como CANCELADO, criar um registro na tabela AGENDAMENTOS_CANCELADOS
                    agendamentoAulaService.excluirAgendamentoAula(agendamentoAulaConflitante.getId());
                    //e enviar uma notificao pro professor da aula
                }
                if(agendamentoConflitoService.existeEventoNoHorario(sala.getId(), dataInicial, janela.getId())){
                    throw new ConflitoAoAgendarException(
                        "Falha ao agendar um evento, pois já existe um evento na sala " + sala.getNome() + 
                        " no dia "  +  dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                        " na janela de horarios de: " + janela.getHoraInicio().toString() + "-"+janela.getHoraFim().toString());
                }
                AgendamentoEvento agendamentoEvento = new AgendamentoEvento();
                agendamentoEvento.setUsuario(usuario);
                agendamentoEvento.setSala(sala);
                agendamentoEvento.setEventoId(evento);
                agendamentoEvento.setJanelasHorario(janela);
                agendamentoEvento.setData(dataInicial);
                agendamentoEvento.setRecorrencia(recorrenciaSalva);
                agendamentoEvento.setTipoEvento(true);
                agendamentoEvento.setStatus("ATIVO");
                agendamentoEventoRepository.save(agendamentoEvento);

            }
            dataInicial = dataInicial.plusDays(1);
            
            }
    }

    private AgendamentoEventoResponseDTO converterParaResponseDTO(AgendamentoEvento agendamentoEvento){
        return new AgendamentoEventoResponseDTO(
            agendamentoEvento.getId(),
            agendamentoEvento.getUsuario() != null ? agendamentoEvento.getUsuario().getNome() : null,
            agendamentoEvento.getSala() != null ? agendamentoEvento.getSala().getNome() : null,
            agendamentoEvento.getEventoId()!=null? agendamentoEvento.getEventoId().getNome(): null,
            agendamentoEvento.getData(),
            agendamentoEvento.getDiaDaSemana(),
            agendamentoEvento.getJanelasHorario().getHoraInicio(),
            agendamentoEvento.getJanelasHorario().getHoraFim(),
            agendamentoEvento.isTipoEvento(),
            agendamentoEvento.getRecorrencia().getId(),
            agendamentoEvento.getStatus()
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

