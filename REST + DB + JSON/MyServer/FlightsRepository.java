/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myrestserverjson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 *
 * @author studente
 */

@Path("/flights")
public class FlightsRepository {
    private Connection conn;
    
    //For DB
    public void setConnection(String pos) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+pos);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET
    @Path("{flightId}")
    @Produces("application/json")
    public Flight getFlight(@PathParam("flightId") int flightId) {
        return findById(flightId);
    }
    
    @PUT
    @Path("{flightId}")
    @Consumes("application/json")
    public Response updateFlight(@PathParam("flightId") int flightId, Flight flight) {
        Flight existing = findById(flightId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existing.equals(flight)) {
            return Response.notModified().build();
        }
        // fligths.put(fligthId, fligth);
        update(flightId, flight);
        return Response.ok().build();
    }
    
    private Flight findById(int id) {
        //for queries w/ parameters
        PreparedStatement stat = null;
        Flight fl = null;
        try {
            stat = conn.prepareStatement("select * from flight where id = ?");
            stat.setString(1, String.valueOf(id));
        
            ResultSet rs = stat.executeQuery();
            if (rs.next()) {
                fl = new Flight();
                fl.setId(Integer.parseInt(rs.getString("id")));
                fl.setName(rs.getString("name"));
                Logger.getLogger(FlightsRepository.class.getName()).log(Level.INFO, "Accessed : " + fl);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        /* simple version 
        for (Map.Entry<Integer, Fligth> fligth : fligths.entrySet()) {
            if (fligth.getKey() == id) {
                return fligth.getValue();
            }
        }
        */
        return fl;   
    }
    
    private void update(int flightId, Flight flight){
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement("update flight set name = ? where id = ?");
            stat.setString(1, flight.getName());
            stat.setString(2, String.valueOf(flightId));
        
            int affectedRow = stat.executeUpdate();
            if (affectedRow == 1) {
                Logger.getLogger(FlightsRepository.class.getName()).log(Level.INFO, "Updated : " + flight);
                return;
            }    
            else throw new RuntimeException();
        } catch (Exception ex) {
            Logger.getLogger(FlightsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
