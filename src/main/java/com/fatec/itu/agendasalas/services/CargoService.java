package com.fatec.itu.agendasalas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.repositories.CargoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }
    

    public List<Cargo> listarTodosCargos() {
        return cargoRepository.findAll();
    }

    public Cargo cadastrarCargo(Cargo cargo){
        if(cargoRepository.findByNome(cargo.getNome()).isPresent()){
            throw new RuntimeException("Impossivel cadastrar cargo, pois já existe com esse nome");
        }
        return cargoRepository.save(cargo);
    }

    public Cargo findById(Long id) {
        return cargoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cargo de id: " + id + " não encontrado"));
    }

    public Cargo findByNome(String nome){
        return cargoRepository.findByNome(nome)
            .orElseThrow(() -> new EntityNotFoundException("Cargo de nome: " + nome + " não encontrado"));
    }
}
