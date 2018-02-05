/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.empresa;
import model.fornecedor;
import model.itens_pedido;
import model.pedido_comunicacao;
import model.pedidos;

/**
 *
 * @author Developer
 */

@Path("model.pedidos")
public class pedidosFacadeREST extends AbstractFacade<pedidos> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public pedidosFacadeREST() {
        super(pedidos.class);
    }
    
    public pedidos convertePedido(pedido_comunicacao aPedido){
        pedidos lPedidos = new pedidos();
        
        lPedidos = aPedido;
        
        return lPedidos;
    }
    public pedidos getPedido(long aiCodigo){
        pedidos lClasse;
        lClasse = getEntityManager().find(pedidos.class, aiCodigo );
        return lClasse;
    }
    
    public fornecedor getFornecedor(long aiCodigo){
        fornecedor lClasse;
        lClasse = getEntityManager().find(fornecedor.class, aiCodigo );
        return lClasse;
    }

    @POST
    @Path("{idempresa}/pedido")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create(@PathParam("idempresa") Long idempresa, pedidos entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        
        entity.setFornecedor(getFornecedor(entity.getCodigo_fornecedor()));
        entity.setEmpresa(lEmpresa);
        
        lEmpresa.getPedido().add(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{idempresa}/pedido/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id, pedidos entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        
        entity.setFornecedor(getFornecedor(entity.getCodigo_fornecedor()));
        entity.setEmpresa(lEmpresa);
        
        getEntityManager().merge(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @PUT
    @Path("{idempresa}/pedido/{id}/itenspedido")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id, List<itens_pedido> entity) {
        
        pedidos lPedido;
        lPedido = this.getPedido(id);
        lPedido.setItensPedido(entity);
        
        for( itens_pedido item : entity )
        {
              item.setPedido(lPedido);
        }
        
        getEntityManager().merge(lPedido);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    
    @GET
    @Path("{idempresa}/pedido/{id}/itenspedido")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<itens_pedido> finditens(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getPedido(id).getItensPedido();
    }
    
    
    @DELETE
    @Path("{idempresa}/pedido/{id}")
    public void remove(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        pedidos lPedidos;
        lPedidos = getEntityManager().find(pedidos.class,id);
        super.remove(lPedidos);
        this.getEmpresa(idempresa).getPedido().remove(lPedidos);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{idempresa}/pedido/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public pedidos find(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getPedido(id);
    }

    @GET
    @Path("{idempresa}/pedido")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<pedidos> findAll(@PathParam("idempresa") Long idempresa) {
        return this.getEmpresa(idempresa).getPedido();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<pedidos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return null;//super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        if(em == null){
            EntityManagerFactory factory;
            factory = Persistence.createEntityManagerFactory("condominio_backendPU");
            em = factory.createEntityManager();
        }
        return em;
    }
    
}
