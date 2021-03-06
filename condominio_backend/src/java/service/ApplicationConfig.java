/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Developer
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(configuracao.HttpMethodOverrideEnabler.class);
        resources.add(configuracao.NewCrossOriginResourceSharingFilter.class);
        resources.add(service.JWTTokenNeededFilter.class);
        resources.add(service.clienteFacadeREST.class);
        resources.add(service.empresaFacadeREST.class);
        resources.add(service.fornecedorFacadeREST.class);
        resources.add(service.pedidosFacadeREST.class);
        resources.add(service.produtosFacadeREST.class);
        resources.add(service.usuarioFacadeREST.class);
        resources.add(service.vendasFacadeREST.class);
    }
    
}
