package com.fatec.itu.agendasalas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteCreationDTO;
import com.fatec.itu.agendasalas.dto.auxiliarDocenteDTO.AuxiliarDocenteResponseDTO;
import com.fatec.itu.agendasalas.dto.usersDTO.UsuarioCreationDTO;
import com.fatec.itu.agendasalas.entity.AuxiliarDocente;
import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.repositories.AuxiliarDocenteRepository;
import com.fatec.itu.agendasalas.repositories.CargoRepository;

@Service
public class AuxiliarDocenteService {

    @Autowired
    private AuxiliarDocenteRepository auxiliarDocenteRepository;

    @Autowired
    private CargoRepository cargoRepository;

    public Page<AuxiliarDocenteResponseDTO> listarAuxiliaresDocentes(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
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

    public AuxiliarDocenteResponseDTO cadastrarAuxiliarDocente(AuxiliarDocenteCreationDTO auxiliarDocenteCreationDTO) {
        Cargo cargo = cargoRepository.findById(2L).orElseThrow();
        AuxiliarDocente auxiliarDocente = new AuxiliarDocente();
    }


}
