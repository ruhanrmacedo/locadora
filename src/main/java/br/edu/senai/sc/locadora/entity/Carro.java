package br.edu.senai.sc.locadora.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String modelo;
    private int ano;
    private String placa;
    private String cor;
    private Double preco;
    private String categoria;
    @JsonIgnore
    @OneToMany(mappedBy = "carro", orphanRemoval = true)
    private List<Emprestimo> emprestimos;

}
