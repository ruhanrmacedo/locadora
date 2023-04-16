package br.edu.senai.sc.locadora.repository;

import br.edu.senai.sc.locadora.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAllByLocatarioContainingIgnoreCase(String nome);

    List<Emprestimo> findAllByCpf(String cpf);

}
