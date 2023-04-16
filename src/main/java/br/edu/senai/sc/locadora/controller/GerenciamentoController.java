package br.edu.senai.sc.locadora.controller;
import br.edu.senai.sc.locadora.entity.Carro;
import br.edu.senai.sc.locadora.repository.CarroRepository;
import br.edu.senai.sc.locadora.service.GerenciamentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gerenciamento")
@Slf4j
public class GerenciamentoController {

    private final GerenciamentoService gerenciamentoService;

    public GerenciamentoController(GerenciamentoService gerenciamentoService) {
        this.gerenciamentoService = gerenciamentoService;
    }


    @GetMapping("/hello")
    public ResponseEntity<Carro> helloWorld(){
        Carro carro = new Carro();
        carro.setModelo("Honda");
        carro.setAno(2022);
        carro.setCor("Prata");
        return new ResponseEntity<>(carro, HttpStatus.OK);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 545, message = "Erro de teste"),
            @ApiResponse(code = 207, message = "Sucesso demais")
    })
    @ApiOperation(value = "Cadastrar Novo Carro")
    public ResponseEntity<String> cadastrarCarro(@RequestBody Carro carro){
        try {
            gerenciamentoService.salvarCarro(carro);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro ao cadastrar o Carro", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Carro cadastrado com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/alterar/{codigo}")
    public ResponseEntity<String> alterarCarro(@RequestBody Carro carro, @PathVariable("codigo") int codigo){
        try{
            gerenciamentoService.salvarCarro(carro);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro ao alterar o Carro", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Carro alterado com sucesso", HttpStatus.OK);
    }


    @PatchMapping("/alterarPlaca/{codigo}")
    public ResponseEntity<String> alterarPlaca(@RequestParam String placa, @PathVariable("codigo") Long codigo){
        try{
            gerenciamentoService.alterarPlaca(placa, codigo);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro ao alterar placa", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Placa alterada com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{codigo}")
    public ResponseEntity<String> excluirCarro(@PathVariable("codigo")Long codigo){
        try {
            gerenciamentoService.excluirCarro(codigo);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro ao excluir o Carro", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Carro excluido com sucesso", HttpStatus.CREATED);
    }

    @GetMapping("/consultar/{codigo}")
    public ResponseEntity<Carro> consultaCarro(@PathVariable("codigo")Long codigo){
        try{
            Optional<Carro> carro = gerenciamentoService.buscarCarroPorCodigo(codigo);
            if(Optional.ofNullable(carro).isPresent())
                return new ResponseEntity<>(carro.get(), HttpStatus.OK);
            return new ResponseEntity<>(new Carro(), HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            return new ResponseEntity<>(new Carro(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/carros")
    public ResponseEntity<List<Carro>> buscarCarros(){
        try{
            List<Carro> carros = gerenciamentoService.buscarCarros();
                return new ResponseEntity<>(carros, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/carros/modelo")
    public ResponseEntity<List<Carro>> buscarCarros(@RequestParam("modelo")String modelo){
        try{
            List<Carro> carros = gerenciamentoService.buscarCarrosPorModelo(modelo);
            return new ResponseEntity<>(carros, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }
}
