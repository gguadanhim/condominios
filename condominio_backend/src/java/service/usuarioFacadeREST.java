/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDate;
//import java.awt.RenderingHints.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
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
import static javax.ws.rs.Priorities.AUTHORIZATION;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import javax.xml.bind.DatatypeConverter;
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
    
    //@Inject
    //private KeyGenerator keyGenerator;
    
    @POST
    @Path("/login")
    @Consumes({ MediaType.APPLICATION_JSON})    
    public Response authenticateUser(usuario dados) {  
        try {
            String token = "";
            usuario lusuario;
            lusuario = this.burcarUsuarioSenha(dados);
            if(lusuario != null){
                 token = issueToken(dados.getLogin(),lusuario.getEmpresa().getId());
            }

            return Response.ok(token).build();
        
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }
    
    private usuario burcarUsuarioSenha(usuario a){
        boolean lbValidou = false;
        List<usuario> lUsuarios;
        usuario lUsuario = null;
        
        lUsuarios = this.findAll();
        
        for (int i = 0; i < lUsuarios.size(); i++) {
            if(lUsuarios.get(i).getLogin() != null){
                if(lUsuarios.get(i).getLogin().equals(a.getLogin()) ){
                    lUsuario = lUsuarios.get(i);
                    break;
                }
            }
        }
        
        if(lUsuario.getSenha().equals(a.getSenha())){
            lbValidou = true;
        }else{
            lUsuario = null;
        }
        
        return lUsuario;
    }
    
    private String issueToken(String login,long empresaId) {
        String jwtToken ="";
        
        Date ll2  = new Date();
        
        Date ll = new Date(ll2.getTime() + 30 * 60 * 1000);
        
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("chave");
        //Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(Long.toString(empresaId))
                .setIssuedAt(new Date())
                .setExpiration(ll)
                .signWith(SignatureAlgorithm.HS512, "chave")
                .compact();
        
        return jwtToken;
    }
}
