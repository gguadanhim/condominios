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
import model.usuario;

/**
 *
 * @author Developer
 */
@Path("model.empresa")
public class empresaFacadeREST extends AbstractFacade<empresa> {

    @PersistenceContext(unitName = "condominio_backendPU")
    private EntityManager em;

    public empresaFacadeREST() {
        super(empresa.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public int create(empresa entity) {
        super.create(entity);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return 1;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, empresa entity) {
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
    public empresa find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<empresa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<empresa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @POST
    @Path("{id}/model.usuario")
    public empresa adicionarUsuario(@PathParam("id") Long id, usuario u){
        empresa lEmpresa = super.find(id);
        u.setEmpresa(lEmpresa);
        lEmpresa.getUsuario().add(u);
        em.getTransaction().begin();
        em.getTransaction().commit();
        return lEmpresa;
    }
    
    @GET
    @Path("{id}/model.usuario")
    public List<usuario> BuscarUsuarios(@PathParam("id") Long id){
        empresa lEmpresa = super.find(id);
        List<usuario> ll;
        ll = lEmpresa.getUsuario();
        return ll; 
    }
    
    @GET
    @Path("{id}/model.usuario/{idusuario}")
    public usuario BuscarUsuario(@PathParam("id") Long id,@PathParam("idusuario") int idUsuario){
        usuarioFacadeREST ll = new usuarioFacadeREST();
        usuario lUsuario;
        lUsuario = ll.find(Long.valueOf(idUsuario));
        
        return lUsuario;
    }
    
    @DELETE
    @Path("{id}/model.usuario/{idusuario}")
    public int ApagarUsuario(@PathParam("id") Long id,@PathParam("idusuario") Long idUsuario){
        empresa lEmpresa = super.find(id);
        usuario lUsuario = this.getUsuario(idUsuario);
        getEntityManager().remove(lUsuario);
        lEmpresa.getUsuario().remove(lUsuario);
        
        getEntityManager().getTransaction().begin();
        getEntityManager().getTransaction().commit();
        return 1;
    }
    
    public usuario getUsuario(long aiUsuario){
        usuario lUsuario;
        lUsuario = getEntityManager().find(usuario.class, aiUsuario);
        return lUsuario;
    }
    
    @PUT
    @Path("{id}/model.usuario/{idusuario}")
    public void EditarUsuario(@PathParam("id") Long id, @PathParam("idusuario") Long idUsuario, usuario u) {
         empresa lEmpresa = super.find(id);
         u.setEmpresa(lEmpresa);
         getEntityManager().merge(u);
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
}
