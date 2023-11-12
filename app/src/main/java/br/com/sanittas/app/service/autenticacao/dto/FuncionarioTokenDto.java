package br.com.sanittas.app.service.autenticacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FuncionarioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
