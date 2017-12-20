/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Developer
 */
public class pedido_comunicacao extends pedidos{
    private long ilCodigoFornecedor;

    /**
     * @return the ilCodigoFornecedor
     */
    public long getCodigoFornecedor() {
        return ilCodigoFornecedor;
    }

    /**
     * @param ilCodigoFornecedor the ilCodigoFornecedor to set
     */
    public void setCodigoFornecedor(long ilCodigoFornecedor) {
        this.ilCodigoFornecedor = ilCodigoFornecedor;
    }
}
