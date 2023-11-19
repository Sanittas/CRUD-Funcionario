package br.com.sanittas.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ServicoEmpresa {
    @EmbeddedId
    private IdServicoEmpresa idServicoEmpresa;
    @ManyToOne
    @MapsId("fkEmpresa")
    @JoinColumn(name = "fkEmpresa")
    private Empresa empresa;
    @ManyToOne
    @MapsId("fkServico")
    @JoinColumn(name = "fkServico")
    private Servico servico;
    private String valorServico;
    private String duracaoEstimada;
    private String equipeResponsavel;
    @OneToMany(mappedBy = "servicoEmpresa")
    private List<AgendamentoServico> agendamentoServico;
}
