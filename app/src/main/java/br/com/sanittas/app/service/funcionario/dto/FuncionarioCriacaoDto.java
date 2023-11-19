package br.com.sanittas.app.service.funcionario.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@Getter
@Setter
public class FuncionarioCriacaoDto {
    @NotBlank
    private String funcional;
    @NotBlank
    private String nome;
    @CPF
    private String cpf;
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-[\\d\\w]",
            message = "O RG deve estar no formato XX.XXX.XXX-X ou XX.XXX.XXX-x, onde X é um dígito ou letra.")
    private String rg;
    @Email
    private String email;
    @NotBlank
    private String numeroRegistroAtuacao;
}
