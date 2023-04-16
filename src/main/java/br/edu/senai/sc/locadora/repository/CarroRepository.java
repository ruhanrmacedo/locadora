package br.edu.senai.sc.locadora.repository;

import br.edu.senai.sc.locadora.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    List<Carro> findCarrosByAno(int ano);

    List<Carro> findCarrosByModeloContaining(String modelo);

    @Query(value = "select c from Carro c where c.modelo like ?1 ")
    List<Carro> buscarCarrosPorModelo(String modelo);
    @Query(value = "SELECT count (*) from Carro", nativeQuery = true)
    Integer numeroCarros();
}
