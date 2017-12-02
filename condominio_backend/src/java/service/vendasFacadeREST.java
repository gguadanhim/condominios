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
import model.vendas;

/**
 *
 * @author Developer
 */

@Path("model.vendas")
public class vendasFacadeREST extends AbstractFacade<vendas> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public vendasFacadeREST() {
        super(vendas.class);
    }
    
    public vendas getVendas(long aiCodigo){
        vendas lClasse;
        lClasse = getEntityManager().find(vendas.class, aiCodigo );
        return lClasse;
    }

    @POST
    @Path("{idempresa}/venda")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create(@PathParam("idempresa") Long idempresa,vendas entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        lEmpresa.getVenda().add(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{idempresa}/venda/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id, vendas entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        getEntityManager().merge(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @DELETE
    @Path("{idempresa}/venda/{id}")
    public void remove(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        vendas lVenda;
        lVenda = getEntityManager().find(vendas.class,id);
        super.remove(lVenda);
        this.getEmpresa(idempresa).getVenda().remove(lVenda);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{idempresa}/venda/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public vendas find(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getVendas(id);
    }

    @GET
    @Path("{idempresa}/venda")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<vendas> findAll(@PathParam("idempresa") Long idempresa) {
        return this.getEmpresa(idempresa).getVenda();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<vendas> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
