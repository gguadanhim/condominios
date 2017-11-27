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
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer
 */
@Entity
@XmlRootElement
public class empresa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;
    
    private String endereco;
    
    private String telefone;
    
    private String cnpj;

    private Long forma_pagamento;
    
    private Long status;
    
    @OneToMany(mappedBy = "iEmpresa",cascade = CascadeType.PERSIST)
    private List<usuario> iUsuario;
    
    @OneToMany(mappedBy = "iCliente",cascade = CascadeType.PERSIST)
    private List<cliente> iCliente;
    
    @OneToMany(mappedBy = "iFornecedor",cascade = CascadeType.PERSIST)
    private List<fornecedor> iFornecedor;
    
    @OneToMany(mappedBy = "iPedido",cascade = CascadeType.PERSIST)
    private List<pedidos> iPedido;
    
    @OneToMany(mappedBy = "iProduto",cascade = CascadeType.PERSIST)
    private List<produtos> iProduto;
    
    @OneToMany(mappedBy = "iVenda",cascade = CascadeType.PERSIST)
    private List<vendas> iVenda;
    
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the iUsuario
     */
    @XmlTransient
    public List<usuario> getUsuario() {
        return iUsuario;
    }

    /**
     * @param iUsuario the iUsuario to set
     */
    public void setUsuario(List<usuario> iUsuario) {
        this.iUsuario = iUsuario;
    }
    
    @XmlTransient
    public List<cliente> getCliente() {
        return iCliente;
    }
    public void setCliente(List<cliente> iCliente) {
        this.iCliente = iCliente;
    }
    
    @XmlTransient
    public List<fornecedor> getFornecedor() {
        return iFornecedor;
    }

    public void setFornecedor(List<fornecedor> iFornecedor) {
        this.iFornecedor = iFornecedor;
    }
    
    @XmlTransient
    public List<pedidos> getPedido() {
        return iPedido;
    }

    public void setPedido(List<pedidos> iPedido) {
        this.iPedido = iPedido;
    }
    
    @XmlTransient
    public List<produtos> getProduto() {
        return iProduto;
    }

    public void setProduto(List<produtos> iProduto) {
        this.iProduto = iProduto;
    }
    
    @XmlTransient
    public List<vendas> getVenda() {
        return iVenda;
    }

    public void setVenda(List<vendas> iVenda) {
        this.iVenda = iVenda;
    }
}
