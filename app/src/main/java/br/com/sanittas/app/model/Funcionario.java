package br.com.sanittas.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Funcionario")
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    private String senha;
    @NotBlank
    private String numeroRegistroAtuacao;
    @ManyToOne
    private Empresa idEmpresa;

}
