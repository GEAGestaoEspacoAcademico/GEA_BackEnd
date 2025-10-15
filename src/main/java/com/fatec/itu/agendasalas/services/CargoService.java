package com.fatec.itu.agendasalas.services;

import java.util.List;

import com.fatec.itu.agendasalas.entity.Cargo;
import com.fatec.itu.agendasalas.repositories.CargoRepository;

public class CargoService {

    private final CargoRepository cargoRepository;

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

}
