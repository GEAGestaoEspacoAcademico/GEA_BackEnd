package com.fatec.itu.agendasalas.services;

import com.fatec.itu.agendasalas.repositories.CargoRepository;

public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }
    

    public List<Cargo> listarTodosCargos() {
        return cargoRepository.findAll();
    }

}
