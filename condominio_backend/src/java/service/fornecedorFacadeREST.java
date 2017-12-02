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

/**
 *
 * @author Developer
 */
@Path("model.fornecedor")
public class fornecedorFacadeREST extends AbstractFacade<fornecedor> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public fornecedorFacadeREST() {
        super(fornecedor.class);
    }
    
    public fornecedor getForncedor(long aiCodigo){
        fornecedor lClasse;
        lClasse = getEntityManager().find(fornecedor.class, aiCodigo );
        return lClasse;
    }

    @POST
    @Path("{idempresa}/fornecedor")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create(@PathParam("idempresa") Long idempresa,fornecedor entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        lEmpresa.getFornecedor().add(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{idempresa}/fornecedor/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id, fornecedor entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        getEntityManager().merge(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @DELETE
    @Path("{idempresa}/fornecedor/{id}")
    public void remove(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        fornecedor lFornecedor;
        lFornecedor = getEntityManager().find(fornecedor.class,id);
        super.remove(lFornecedor);
        this.getEmpresa(idempresa).getFornecedor().remove(lFornecedor);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{idempresa}/fornecedor/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public fornecedor find(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getForncedor(id);
    }

    @GET
    @Path("{idempresa}/fornecedor")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<fornecedor> findAll(@PathParam("idempresa") Long idempresa) {
        return this.getEmpresa(idempresa).getFornecedor();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<fornecedor> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
