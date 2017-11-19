/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = "";
        String lsPath = "";
        KeyGenerator keyGenerator;
        
        lsPath = requestContext.getUriInfo().getPath();
        
        if (!lsPath.startsWith("/model.usuario")&(!lsPath.startsWith("/application.wadl"))){

            try {
                String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

                // Extract the token from the HTTP Authorization header
                token = authorizationHeader.substring("Bearer".length()).trim();
                    // Validate the token
                //    Key key =  keyGenerator.generateKey();
                //   Jwts.parser().setSigningKey((java.security.Key) key).parseClaimsJws(token);
                    System.out.println("#### valid token : " + token);

            } catch (Exception e) {
                System.out.println("#### invalid token : " + token);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}