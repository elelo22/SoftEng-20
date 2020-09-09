/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsservant;

import java.util.logging.*;
import javax.jms.*;

/**
 *
 * @author studente
 */
public class myListenerOrdini implements MessageListener {
    private Session session;
    private Destination destination;
    
    public myListenerOrdini(Session s, Destination d){
        this.session = s;
        this.destination = d;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            try {
                TextMessage msg = (TextMessage) message;
                String user = msg.getStringProperty("Utente");
                String name = msg.getStringProperty("Nome");
                Float price = msg.getFloatProperty("Prezzo");
                Integer quantita = msg.getIntProperty("Quantita");
                System.out.println("ORDER RECEVIED:\n"+"Utente: "+user+", Nome: "+name+", Prezzo: "+price+", Quantità: "+quantita);
                
                MessageProducer producer = session.createProducer(destination);
                TextMessage response = session.createTextMessage();
                response.setStringProperty("Status", "OK");
                producer.send(response);
                
            } catch (JMSException ex) {
                Logger.getLogger(myListenerOrdini.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
