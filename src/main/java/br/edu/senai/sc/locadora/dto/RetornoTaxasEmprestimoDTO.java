package br.edu.senai.sc.locadora.dto;

import lombok.Data;

@Data
public class RetornoTaxasEmprestimoDTO {

    Double pendencia;
    Double multaDiaria;
    Long diasAtrasados;
    Double multaTotal;

}
