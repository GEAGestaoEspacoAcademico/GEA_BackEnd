package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteCreationDTO;
import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.entity.AuxiliarDocente;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.interfaces.UsuarioCadastravel;
import com.fatec.itu.agendasalas.repositories.AuxiliarDocenteRepository;
import com.fatec.itu.agendasalas.repositories.CargoRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AuxiliarDocenteService implements UsuarioCadastravel<AuxiliarDocenteCreationDTO, AuxiliarDocenteResponseDTO> {

    @Autowired
    private AuxiliarDocenteRepository auxiliarDocenteRepository;

    @Autowired
    private PasswordEncryptService passwordEncryptService;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    public Page<AuxiliarDocenteResponseDTO> listarAuxiliaresDocentes(int page, int size) {
       Pageable pageable = PageRequest.of(page-1, size);
       return auxiliarDocenteRepository.findAll(pageable)
       .map(this::converterParaDTO);
    }
    
    private AuxiliarDocenteResponseDTO converterParaDTO(AuxiliarDocente auxiliarDocente){
        
        return new AuxiliarDocenteResponseDTO(
            auxiliarDocente.getId(), 
            auxiliarDocente.getNome(), 
            auxiliarDocente.getEmail(),
            auxiliarDocente.getArea()
        );
    }


    @Override
    @Transactional
    public AuxiliarDocenteResponseDTO cadastrarUsuario(AuxiliarDocenteCreationDTO auxiliarDocenteCreationDTO) throws MessagingException {
        AuxiliarDocente auxiliarDocente = new AuxiliarDocente(
                auxiliarDocenteCreationDTO.login(), 
                auxiliarDocenteCreationDTO.email(),
                auxiliarDocenteCreationDTO.nome(),
                auxiliarDocenteCreationDTO.area()
            );

          
            auxiliarDocente.setSenha(passwordEncryptService.criptografarSenha(auxiliarDocenteCreationDTO.senha()));
            Cargo cargo = cargoRepository.findByNome("AUXILIAR_DOCENTE").orElseThrow(()-> new EntityNotFoundException("CARGO AUXILIAR DOCENTE NÃO ENCONTRADO"));
            auxiliarDocente.setCargo(cargo);

            auxiliarDocenteRepository.save(auxiliarDocente);
            notificacaoService.notificarCadastro(auxiliarDocente, auxiliarDocenteCreationDTO.senha());
            return converterParaDTO(auxiliarDocente);
    }


}
