package br.com.sanittas.app.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AgendamentoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataHoraAgendamento;
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    @ManyToOne
    private ServicoEmpresa servicoEmpresa;
}
