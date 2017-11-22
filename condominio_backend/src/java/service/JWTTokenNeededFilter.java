/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;


@Provider
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = "";
        String lsPath = "";
        
        lsPath = requestContext.getUriInfo().getPath();
        
        if (!lsPath.startsWith("/model.usuario")&(!lsPath.startsWith("/application.wadl"))){

            try {
                String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
                
                String lsEmpresa = requestContext.getHeaderString("empresa");

                token = authorizationHeader;
                
                Claims claims = Jwts.parser()         
                    .setSigningKey(DatatypeConverter.parseBase64Binary("chave"))
                    .parseClaimsJws(token).getBody();
                
                if(lsEmpresa.equals(claims.getIssuer())){
                    System.out.println("ID: " + claims.getId());
                    System.out.println("Subject: " + claims.getSubject());
                    System.out.println("Issuer: " + claims.getIssuer());
                    System.out.println("Expiration: " + claims.getExpiration());

                   System.out.println("#### valid token : " + token);
                }else{
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }catch(MalformedJwtException e){
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }catch (Exception e) {
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}