package br.edu.senai.sc.locadora.service;

import br.edu.senai.sc.locadora.dto.ConsultaCustoDTO;
import br.edu.senai.sc.locadora.dto.EmprestimoDTO;
import br.edu.senai.sc.locadora.dto.RetornoCustoDTO;
import br.edu.senai.sc.locadora.dto.RetornoTaxasEmprestimoDTO;
import br.edu.senai.sc.locadora.entity.Carro;
import br.edu.senai.sc.locadora.entity.Emprestimo;
import br.edu.senai.sc.locadora.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final Double MULTA_DIARIA = 15.0;
    private final EmprestimoRepository emprestimoRepository;
    private final GerenciamentoService gerenciamentoService;


    public EmprestimoService(EmprestimoRepository emprestimoRepository, GerenciamentoService gerenciamentoService) {
        this.emprestimoRepository = emprestimoRepository;
        this.gerenciamentoService = gerenciamentoService;
    }

    public void emprestar(EmprestimoDTO emprestimoDTO) throws Exception{
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLocatario(emprestimoDTO.getLocatario());
        emprestimo.setCpf(emprestimoDTO.getCpf());
        emprestimo.setInicio(emprestimoDTO.getInicio());
        emprestimo.setTermino(emprestimoDTO.getTermino());
        emprestimo.setPagamento(emprestimoDTO.getPagamento());

        Optional<Carro> carro = gerenciamentoService.buscarCarroPorCodigo(emprestimoDTO.getCarro());

        if (!carro.isPresent() || carro.isEmpty())
            throw new Exception("Carro não encontrado");

        emprestimo.setCarro(carro.get());

        emprestimoRepository.save(emprestimo);
    }

    public void devolucao(Long codigoEmprestimo) throws Exception {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(codigoEmprestimo);

        if (!emprestimo.isPresent() || emprestimo.isEmpty())
            throw new Exception("Emprestimo não encontrado");

        RetornoTaxasEmprestimoDTO taxasEmprestimoDTO = getTaxasEmprestimo(emprestimo.get());
        Double taxas = taxasEmprestimoDTO.getMultaTotal()+taxasEmprestimoDTO.getPendencia();

        emprestimo.get().setDevolucao(LocalDateTime.now());
        emprestimo.get().setTaxa(taxas);

        emprestimoRepository.save(emprestimo.get());

    }

    public RetornoTaxasEmprestimoDTO verificacao(Long codigoEmprestimo) throws Exception {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(codigoEmprestimo);

        if (!emprestimo.isPresent() || emprestimo.isEmpty())
            throw new Exception("Emprestimo não encontrado");

        return getTaxasEmprestimo(emprestimo.get());
    }

    private RetornoTaxasEmprestimoDTO getTaxasEmprestimo(Emprestimo emprestimo){

        long diasAtrasados = ChronoUnit.DAYS.between(emprestimo.getTermino(), LocalDateTime.now());
        long disasEmprestimo = ChronoUnit.DAYS.between(emprestimo.getInicio(), LocalDateTime.now());
        Double valorEsperado = emprestimo.getCarro().getPreco() * disasEmprestimo;
        Double valorPendente = valorEsperado - emprestimo.getPagamento();
        Double multaDiaria = emprestimo.getCarro().getPreco() * MULTA_DIARIA;
        Double multaTotal = multaDiaria * diasAtrasados;

        RetornoTaxasEmprestimoDTO retornoTaxas = new RetornoTaxasEmprestimoDTO();
        retornoTaxas.setDiasAtrasados(diasAtrasados);
        retornoTaxas.setPendencia(valorPendente);
        retornoTaxas.setMultaDiaria(multaDiaria);
        retornoTaxas.setMultaTotal(multaTotal);

        return retornoTaxas;
    }

    public List<Emprestimo> consultarTodos(){
        return emprestimoRepository.findAll();
    }

    public List<Emprestimo> consultarCPF(String cpf){
        return emprestimoRepository.findAllByCpf(cpf);
    }

    public List<Emprestimo> consultarNome(String nome){
        return emprestimoRepository.findAllByLocatarioContainingIgnoreCase(nome);
    }

    public RetornoCustoDTO consultarCusto(ConsultaCustoDTO consultaCustoDTO) throws Exception {
        Optional<Carro> carro = gerenciamentoService.buscarCarroPorCodigo(consultaCustoDTO.getCarro());

        if (!carro.isPresent() || carro.isEmpty())
            throw new Exception("Carro não encontrado");

        Long dias = ChronoUnit.DAYS.between(consultaCustoDTO.getInicio(), consultaCustoDTO.getTermino());

        Double custo = carro.get().getPreco() * dias;
        RetornoCustoDTO custoDTO = new RetornoCustoDTO();
        custoDTO.setCarro(carro.get());
        custoDTO.setCusto(custo);

        return custoDTO;
    }
}
