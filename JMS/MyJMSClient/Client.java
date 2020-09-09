/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsclient;

import java.util.Properties;
import javax.jms.*;
import javax.naming.*;
/**
 *
 * @author studente
 */
public class Client {
    public static void main(String[] args) throws NamingException, JMSException{
        Context jndiContext = null;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        
        //For topic 1, the one in which we listen
        Destination destination = null; 
        String destinationName = "dynamicTopics/Quotazioni";
        
        //For topic 2, the one in which we write and listen
        Destination destination2 = null;
        String destinationName2= "dynamicTopics/Ordini";
        
        /////////////////////////////////////////////////////////
        /////////////////////INIZIALIZATION//////////////////////
        ///////////STANDARD EVERY TIME FOR EVERYONE//////////////
        /////////////////////////////////////////////////////////
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://127.0.0.1:61616");
        jndiContext = new InitialContext(props);
        
        connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        connection = (Connection) connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        
        /////////////////////////////////////////////////////////////
        ///////////////////FOR TOPIC 1 - LISTEN ////////////////////
        ////////////////////////////////////////////////////////////
        //Here we have a CONSUMER, aka a SUBSCRIBER to a given topic
        //The consumer will  have to have a LISTENER, a class needed to manage the reception of
        //messages from the topic
        destination = (Destination) jndiContext.lookup(destinationName);
        MessageConsumer consumer = session.createConsumer(destination);
        MessageListener listener = new myListenerQuotazioni();
        consumer.setMessageListener(listener);
        
        /////////////////////////////////////////////////////////////
        ///////////////////FOR TOPIC 2 - WRITE & LISTEN// ////////////////////
        /////////////////////////////////////////////////////////////
        //Here we have a CONSUMER, aka a SUBSCRIBER to another given topic, DESTINATION, like above
        //We will also have a PUBLISHER associated to that consumer in order to send messages
        //But we will also have a LISTENER2, to receive the answer
        //Thus, we will use the same DESTINATION for both a CONSUMER and a PRODUCER
        
        destination2 = (Destination) jndiContext.lookup(destinationName2);
        MessageConsumer consumer2 = session.createConsumer(destination2);
        MessageProducer producer = session.createProducer(destination2);
        TextMessage m = session.createTextMessage();
        
        //m.setText("user");
        m.setStringProperty("Utente", "elelo22");
        m.setStringProperty("Nome", "Amazon");
        m.setFloatProperty("Prezzo", 10);
        m.setIntProperty("Quantita", 3);
        producer.send(m);
        System.out.println("[CLIENT] ORDER_REQUEST\nUSER: elelo22 - NOME: Amazon -PREZZO: 10€ - QUANTITA: 3\n"
                + "\n/////////////////////////////");        

        MessageListener listener2 = new myListenerOrdini();
        consumer2.setMessageListener(listener2);
    }
}
