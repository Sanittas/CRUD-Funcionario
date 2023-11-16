package br.com.sanittas.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class IdServicoEmpresa implements Serializable {
    private Integer fkEmpresa;
    private Integer fkServico;
}
