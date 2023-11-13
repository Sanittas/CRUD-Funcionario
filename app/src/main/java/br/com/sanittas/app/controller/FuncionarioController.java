package br.com.sanittas.app.controller;

import br.com.sanittas.app.exception.ValidacaoException;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.service.FuncionarioServices;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaLoginDto;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaTokenDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/funcionarios")
@Slf4j
public class FuncionarioController {
    @Autowired
    private FuncionarioServices services;


    @PostMapping("/autenticar")
    public ResponseEntity<EmpresaTokenDto> login(@RequestBody EmpresaLoginDto empresaLoginDto) {
        log.info("Recebida solicitação de login para empresa: {}", empresaLoginDto.cnpj());
        EmpresaTokenDto empresaTokenDto = services.autenticar(empresaLoginDto);
        log.info("Login bem-sucedido para empresa: {}", empresaLoginDto.cnpj());
        return ResponseEntity.status(200).body(empresaTokenDto);
    }
    @GetMapping("/")
    public ResponseEntity<List<ListaFuncionario>> listar() {
        try{
            var response = services.listaFuncionarios();
            if (!response.isEmpty()){
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).build();
        }catch (Exception e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try{
            var usuario = services.buscar(id);
            log.info("Funcionario encontrado" + usuario);
            return ResponseEntity.status(200).body(usuario);
        }catch (Exception e){
            log.info("Funcionario não encontrado" + e.getLocalizedMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid FuncionarioCriacaoDto dados) {
        try{
            services.cadastrar(dados);
            log.info("Funcionario cadastrado");
            return ResponseEntity.status(201).build();
        }catch (Exception e){
            log.error("Erro ao cadastrar funcionario", e.getLocalizedMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody @Valid Funcionario dados) {
        try{
            var funcionario = services.atualizar(id,dados);
            log.info("Funcionario atualizado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        }catch (ValidacaoException e){
            log.error("Erro ao atualizar funcionario" + e.getLocalizedMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try{
            services.deletar(id);
            log.info("Funcionario deletado");
            return ResponseEntity.status(200).build();
        }catch (Exception e){
            log.error("Erro ao deletar funcionario" + e.getLocalizedMessage());
            return ResponseEntity.status(404).build();
        }
    }
}
