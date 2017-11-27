/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer
 */
@Entity
@XmlRootElement
public class vendas implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //cliente
    private int forma_pagamento;
    
    private double valor_total;
    
    private String detalhes;
    
    private int status;
    
    @OneToMany(mappedBy = "iVenda",cascade = CascadeType.PERSIST)
    private List<itens_venda> iItensVenda;

    @ManyToOne
    private empresa iEmpresa;
    
    @ManyToOne
    private cliente iCliente;
    
    
    @XmlTransient
    public cliente getCliente() {
        return iCliente;
    }
    public void setCliente(cliente iCliente) {
        this.iCliente = iCliente;
    }
    
    
    @XmlTransient
    public empresa getEmpresa() {
        return iEmpresa;
    }
    public void setEmpresa(empresa iEmpresa) {
        this.iEmpresa = iEmpresa;
    }
    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the forma_pagamento
     */
    public int getForma_pagamento() {
        return forma_pagamento;
    }

    /**
     * @param forma_pagamento the forma_pagamento to set
     */
    public void setForma_pagamento(int forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    /**
     * @return the valor_total
     */
    public double getValor_total() {
        return valor_total;
    }

    /**
     * @param valor_total the valor_total to set
     */
    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    /**
     * @return the detalhes
     */
    public String getDetalhes() {
        return detalhes;
    }

    /**
     * @param detalhes the detalhes to set
     */
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    @XmlTransient
    public List<itens_venda> getItensVenda() {
        return iItensVenda;
    }

    public void setItensVenda(List<itens_venda> iItensVenda) {
        this.iItensVenda = iItensVenda;
    }
    
}
