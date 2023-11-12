package br.com.sanittas.app.service.funcionario.dto;

import br.com.sanittas.app.model.Empresa;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ListaFuncionarioAtualizacao {
    private int id;
    private String funcional;
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private String senha;
    private String numeroRegistroAtuacao;

}



