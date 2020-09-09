/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author studente
 */
public class myListenerQuotazioni implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        TextMessage message = null;
        
        if(msg instanceof TextMessage ){
            message = (TextMessage) msg;
            
            try {
                String name = message.getStringProperty("Nome");
                String value = message.getText();
                if (name.equals("Barilla")){
                    System.out.println(value);
                }
            } catch (JMSException ex) {
                Logger.getLogger(myListenerQuotazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
