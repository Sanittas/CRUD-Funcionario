package br.com.sanittas.app.controller;

import br.com.sanittas.app.exception.ValidacaoException;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.service.FuncionarioServices;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioServices services;


    @GetMapping("/")
    public ResponseEntity<List<ListaFuncionario>> listar() {
        try{
            var response = services.listaFuncionarios();
            if (!response.isEmpty()){
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        }catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try{
            var usuario = services.buscar(id);
            return ResponseEntity.status(200).body(usuario);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid FuncionarioCriacaoDto dados) {
        try{
            services.cadastrar(dados);
            return ResponseEntity.status(201).build();
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody @Valid Funcionario dados) {
        try{
            var usuario = services.atualizar(id,dados);
            return ResponseEntity.status(200).body(usuario);
        }catch (ValidacaoException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try{
            services.deletar(id);
            return ResponseEntity.status(200).build();
        }catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }
}
