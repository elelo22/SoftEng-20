/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myrestserverjson;

import com.fasterxml.jackson.jaxrs.json.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.*;

/**
 *
 * @author studente
 */
public class Server {
    public static void main(String[] args) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(FlightsRepository.class);
        
        //NEW - set connection with db
        FlightsRepository fr = new FlightsRepository();
        fr.setConnection(args[0]);
        
        factoryBean.setResourceProvider(new SingletonResourceProvider(fr));
        factoryBean.setAddress("http://localhost:8080/");
        
        //NEW - providers
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        factoryBean.setProviders(providers);
        
        //NEW - magic
        BindingFactoryManager manager = factoryBean.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory restFactory = new JAXRSBindingFactory();
        restFactory.setBus(factoryBean.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, restFactory);

        org.apache.cxf.endpoint.Server server = factoryBean.create();

        System.out.println("Server ready...");

        while (true) {
        }
    }
    
}
