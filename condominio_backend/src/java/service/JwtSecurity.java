/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
/*
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import model.usuario;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

/**
 *
 * @author Developer

@Path("/security")
public class JwtSecurity {

static List<JsonWebKey> jwkList = null;

static {    
    System.out.println("Inside static initializer...");
  jwkList = new LinkedList<>(); 
  for (int kid = 1; kid <= 3; kid++) { 
    JsonWebKey jwk = null;
    try {
      jwk = RsaJwkGenerator.generateJwk(2048); 
      System.out.println("PUBLIC KEY (" + kid + "): " + jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
    } catch (JoseException e) {
      e.printStackTrace();
    } 
    jwk.setKeyId(String.valueOf(kid));  
    jwkList.add(jwk); 
  } 
}

@Path("/status")
@GET
@Produces(MediaType.TEXT_HTML)
public String returnVersion() {
  return "JwtSecurityExample Status is OK...";
}
/*
@Path("/authenticate")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response authenticateCredentials(@HeaderParam("username") 

String username,

    @HeaderParam("password") String password)

    throws JsonGenerationException, JsonMappingException, 

            IOException {



  System.out.println("Authenticating User Credentials...");



  if (username == null) {

    StatusMessage statusMessage = new StatusMessage();

    statusMessage.setStatus(

        Status.PRECONDITION_FAILED.getStatusCode());

    statusMessage.setMessage("Username value is missing!!!");

    return Response.status(

        Status.PRECONDITION_FAILED.getStatusCode())

        .entity(statusMessage).build();

  }

   

  if (password == null) {

    StatusMessage statusMessage = new StatusMessage();

    statusMessage.setStatus(

      Status.PRECONDITION_FAILED.getStatusCode());

    statusMessage.setMessage("Password value is missing!!!");

    return Response.status(

        Status.PRECONDITION_FAILED.getStatusCode())

        .entity(statusMessage).build();

  }



  usuario luser = validUser(username, password); 

  if (luser == null) {

    StatusMessage statusMessage = new StatusMessage();

    statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());

    statusMessage.setMessage(

      "Access Denied for this functionality !!!");

    return Response.status(Status.FORBIDDEN.getStatusCode())

        .entity(statusMessage).build();

  }

  RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);

  senderJwk.setKeyId("1");

  System.out.println("JWK (1) ===> " + senderJwk.toJson());



  // Create the Claims, which will be the content of the JWT

  JwtClaims claims = new JwtClaims();

  claims.setIssuer("avaldes.com");

  claims.setExpirationTimeMinutesInTheFuture(10);

  claims.setGeneratedJwtId();

  claims.setIssuedAtToNow();

  claims.setNotBeforeMinutesInThePast(2);

  claims.setSubject(user.getUsername());

  claims.setStringListClaim("roles", user.getRolesList()); 

  JsonWebSignature jws = new JsonWebSignature();

  jws.setPayload(claims.toJson());
  jws.setKeyIdHeaderValue(senderJwk.getKeyId());

  jws.setKey(senderJwk.getPrivateKey());
  jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256); 
  String jwt = null;

  jwt = jws.getCompactSerialization();

  return Response.status(200).entity(jwt).build();
}
    private usuario validUser(String username, String password) {
        usuario lUsuario = null;
        
        return lUsuario;
    }

}
*/