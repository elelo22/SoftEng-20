/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myrestclientjson;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;

/**
 *
 * @author studente
 */
public class Client {
    private static final String BASE_URL = "http://localhost:8080/flights/";
    private static CloseableHttpClient client;
    
    public static void main(String[] args) throws IOException {
        client = HttpClients.createDefault();
        
        //GET example
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL(BASE_URL + "2");
        InputStream input = url.openStream();
        Flight fl = (Flight) mapper.readValue(input, Flight.class);        
        System.out.println(fl);
        
        //PUT example
        Flight newFl = new Flight();
        newFl.setId(2);
        newFl.setName("XX000");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonstr = objectMapper.writeValueAsString(newFl);
        StringEntity entity = new StringEntity(jsonstr);
        
        HttpPut httpPut = new HttpPut(BASE_URL + "2/");
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpPut);
        System.out.println(response);
        
        InputStream input2 = url.openStream();
        fl = (Flight) mapper.readValue(input2, Flight.class);
        System.out.println(fl);
    }
}
