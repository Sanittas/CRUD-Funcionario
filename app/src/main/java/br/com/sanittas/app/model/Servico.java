package br.com.sanittas.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descrição;
    @ManyToOne
    @JoinColumn(name = "fk_categoria_servico")
    private CategoriaServico categoriaServico;
    @OneToMany(mappedBy = "servico")
    private List<ServicoEmpresa> servicoEmpresa;
}
