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
import model.usuario;

/**
 *
 * @author Developer
 */
//@javax.ejb.Stateless
@Path("model.usuario")
public class usuarioFacadeREST extends AbstractFacade<usuario> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public usuarioFacadeREST() {
        super(usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public int create(usuario entity) {
        super.create(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        return 200;
    }

    @PUT
    @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON})
    public int edit(@PathParam("id") Long id, usuario entity) {
        super.edit(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 200;
    }

    @DELETE
    @Path("{id}")
    public int remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 200;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public usuario find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({ MediaType.APPLICATION_JSON})
    public List<usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
