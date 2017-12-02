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
import model.produtos;

/**
 *
 * @author Developer
 */
//@javax.ejb.Stateless
@Path("model.produtos")
public class produtosFacadeREST extends AbstractFacade<produtos> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public produtosFacadeREST() {
        super(produtos.class);
        
    }

    @POST
    @Path("{idempresa}/produto")
    @Consumes({MediaType.APPLICATION_JSON})
    public int create(@PathParam("idempresa") Long id, produtos entity) {
        empresa lEmpresa = this.getEmpresa(id);
        entity.setEmpresa(lEmpresa);
        lEmpresa.getProduto().add(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{idempresa}/produto/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa, @PathParam("id") Long id, produtos entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
         entity.setEmpresa(lEmpresa);
         getEntityManager().merge(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @DELETE
    @Path("{idempresa}/produto/{id}")
    public void remove(@PathParam("idempresa") Long idempresa, @PathParam("id") Long id) {
        produtos lProduto;
        lProduto = getEntityManager().find(produtos.class,id);
        super.remove(lProduto);
        this.getEmpresa(idempresa).getProduto().remove(lProduto);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{idempresa}/produto/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public produtos find(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getProduto(id);
    }
    
    public produtos getProduto(long aiProduto){
        produtos lProduto;
        lProduto = getEntityManager().find(produtos.class, aiProduto );
        return lProduto;
    }

    @GET
    @Path("{idempresa}/produto")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<produtos> findAll(@PathParam("idempresa") Long idempresa) {
        return this.getEmpresa(idempresa).getProduto();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<produtos> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
