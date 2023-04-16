package br.edu.senai.sc.locadora.dto;

import br.edu.senai.sc.locadora.entity.Carro;
import lombok.Data;

@Data
public class RetornoCustoDTO {
    private Double custo;
    private Carro carro;
}
