/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer
 */
@Entity
@XmlRootElement
public class itens_pedido implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private double quantidade;
    
    private double valor_unitario;

    public long codigo_produto;
    
    @OneToOne
    private produtos iProduto;
    
    @ManyToOne
    private pedidos iPedido;
    
    @XmlTransient
    public pedidos getPedido() {
        return iPedido;
    }
    public void setPedido(pedidos iPedido) {
        this.iPedido = iPedido;
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
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor_unitario
     */
    public double getValor_unitario() {
        return valor_unitario;
    }

    /**
     * @param valor_unitario the valor_unitario to set
     */
    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    /**
     * @return the iProduto
     */
    public produtos getiProduto() {
        return iProduto;
    }

    /**
     * @param iProduto the iProduto to set
     */
    public void setiProduto(produtos iProduto) {
        this.iProduto = iProduto;
    }
   
    @JsonInclude()
    @Transient
    public long getCodigo_produto() {
        return codigo_produto;
    }

    /**
     * @param codigo_fornecedor the codigo_fornecedor to set
     */
    public void setCodigo_produto(long codigo_produto) {
        this.codigo_produto = codigo_produto;
    }
    
}
