package br.com.sanittas.app.controller;

import br.com.sanittas.app.exception.ValidacaoException;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.service.FuncionarioServices;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaLoginDto;
import br.com.sanittas.app.service.autenticacao.dto.EmpresaTokenDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/funcionarios")
@Slf4j
public class FuncionarioController {
    @Autowired
    private FuncionarioServices services;
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
            var funcionario = services.buscar(id);
            log.info("Funcionario encontrado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        }catch (Exception e){
            log.info("Funcionario não encontrado");
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid FuncionarioCriacaoDto dados, HttpServletRequest request) {
        try{
            String requestTokenHeader = request.getHeader("Authorization");
            String jwtToken = "";
            if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
            }
            services.cadastrar(dados,jwtToken);
            log.info("Funcionario cadastrado");
            return ResponseEntity.status(201).build();
        }catch (Exception e){
            log.error("Erro ao cadastrar funcionario. Exceção:" + e.getLocalizedMessage());
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
