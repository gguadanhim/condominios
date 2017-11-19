/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates


* and open the template in the editor.
 */
package configuracao;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Developer
 */

@Provider
 public class HttpMethodOverrideEnabler implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
       //containerRequestContext.setMethod("POST");

       containerRequestContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        containerRequestContext.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        containerRequestContext.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type, Authorization, Origin, X-Requested-With");
        containerRequestContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
       
       String override = containerRequestContext.getHeaders().getFirst( "X-HTTP-Method-Override");
       if (override != null) {
           containerRequestContext.setMethod(override);
       }
   }
}