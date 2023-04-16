package br.edu.senai.sc.locadora.service;

import br.edu.senai.sc.locadora.entity.Carro;
import br.edu.senai.sc.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GerenciamentoService {

    private final CarroRepository carroRepository;

    public GerenciamentoService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public Carro salvarCarro(Carro carro){
            carroRepository.save(carro);

            return carro;
    }

    public void excluirCarro(Long codigo){
        carroRepository.deleteById(codigo);
    }

    public Optional<Carro> buscarCarroPorCodigo(Long codigo){
        return carroRepository.findById(codigo);
    }

    public List<Carro> buscarCarros(){
        return carroRepository.findAll();
    }

    public List<Carro> buscarCarrosPorModelo(String modelo){
        return carroRepository.findCarrosByModeloContaining(modelo);
    }

    public void alterarPlaca(String placa, Long codigo){
        Optional<Carro> carro = carroRepository.findById(codigo);
        if (Optional.ofNullable(carro).isPresent()){
            carro.get().setPlaca(placa);
            carroRepository.save(carro.get());
        }
    }

}