/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import fontes.login;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Developer
 */
//@javax.ejb.Stateless
@Path("fontes.login")
public class loginFacadeREST extends AbstractFacade<login> {

    //@PersistenceContext(unitName = "ProjetoPU")
    private EntityManager em;

    public loginFacadeREST() {
        super(login.class);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void logar(login acompany) {
        //super.create(entity);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    
    /*
    @POST
    @Consumes({MediaType.APPLICATION_JSON })
    public void logar(@FormParam("usuario") String usuario,@FormParam("senha") String senha) {
        //super.create(entity);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(login entity) {
        super.create(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, login entity) {
        super.edit(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
        em.getTransaction().begin();
        em.getTransaction().commit();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public login find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<login> findAll() {
        //return 1;
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<login> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
            factory = Persistence.createEntityManagerFactory("ProjetoPU");
            em = factory.createEntityManager();
        }
        return em;
    }
    
}
