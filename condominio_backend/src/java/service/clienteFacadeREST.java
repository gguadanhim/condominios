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
import model.cliente;
import model.empresa;

/**
 *
 * @author Developer
 */
@Path("model.cliente")
public class clienteFacadeREST extends AbstractFacade<cliente> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public clienteFacadeREST() {
        super(cliente.class);
    }
    
    public cliente getCliente(long aiCliente){
        cliente lCliente;
        lCliente = getEntityManager().find(cliente.class, aiCliente );
        return lCliente;
    }

    @POST
    @Path("{idempresa}/cliente")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create(@PathParam("idempresa") Long idempresa,cliente entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        lEmpresa.getCliente().add(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{idempresa}/cliente/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("idempresa") Long idempresa, @PathParam("id") Long id, cliente entity) {
        empresa lEmpresa = this.getEmpresa(idempresa);
        entity.setEmpresa(lEmpresa);
        getEntityManager().merge(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @DELETE
    @Path("{idempresa}/cliente/{id}")
    public void remove(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        cliente lCliente;
        lCliente = getEntityManager().find(cliente.class,id);
        super.remove(lCliente);
        this.getEmpresa(idempresa).getCliente().remove(lCliente);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{idempresa}/cliente/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public cliente find(@PathParam("idempresa") Long idempresa,@PathParam("id") Long id) {
        return this.getCliente(id);
    }

    @GET
    @Path("{idempresa}/cliente")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<cliente> findAll(@PathParam("idempresa") Long idempresa) {
        return this.getEmpresa(idempresa).getCliente();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
