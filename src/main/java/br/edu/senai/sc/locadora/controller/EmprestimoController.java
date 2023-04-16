package br.edu.senai.sc.locadora.controller;

import br.edu.senai.sc.locadora.dto.ConsultaCustoDTO;
import br.edu.senai.sc.locadora.dto.EmprestimoDTO;
import br.edu.senai.sc.locadora.dto.RetornoCustoDTO;
import br.edu.senai.sc.locadora.dto.RetornoTaxasEmprestimoDTO;
import br.edu.senai.sc.locadora.entity.Emprestimo;
import br.edu.senai.sc.locadora.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimo")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/locacao")
    public ResponseEntity<String> realizarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        try {
            emprestimoService.emprestar(emprestimoDTO);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro ao realizar empréstimo" + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>("Empréstimo realizado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/custo")
    public ResponseEntity<RetornoCustoDTO> custoFinal(@RequestBody ConsultaCustoDTO consultaCustoDTO){

        try {
            RetornoCustoDTO retornoCustoDTO = emprestimoService.consultarCusto(consultaCustoDTO);
            return new ResponseEntity<>(retornoCustoDTO, HttpStatus.OK);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/verificacao/{emprestimo}")
    public ResponseEntity<RetornoTaxasEmprestimoDTO> consultarTaxas(@PathVariable("emprestimo") Long codigoEmprestimo){

        try {
            RetornoTaxasEmprestimoDTO taxasEmprestimoDTO = emprestimoService.verificacao(codigoEmprestimo);
            return new ResponseEntity<>(taxasEmprestimoDTO, HttpStatus.OK);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PatchMapping("/devolucao/{emprestimo}")
    public ResponseEntity<String> realizarDevolucao(@PathVariable("emprestimo") Long codigoEmprestimo){

        try {
            emprestimoService.devolucao(codigoEmprestimo);
            return new ResponseEntity<>("Devolução realizada com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<Emprestimo>> consultarTodosEmprestimos(){
        return new ResponseEntity<>(emprestimoService.consultarTodos(), HttpStatus.OK);

    }

    @GetMapping("/consultar/nome")
    public ResponseEntity<List<Emprestimo>> consultarEmprestimoPorNome(@RequestParam("nome") String nome){
        return new ResponseEntity<>(emprestimoService.consultarNome(nome), HttpStatus.OK);

    }

    @GetMapping("/consultar/cpf")
    public ResponseEntity<List<Emprestimo>> consultarEmprestimoPorCPF(@RequestParam("cpf") String cpf){
        return new ResponseEntity<>(emprestimoService.consultarCPF(cpf), HttpStatus.OK);

    }

}
