/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsservant;

import java.util.*;
import javax.jms.*;
import javax.naming.*;
/**
 *
 * @author studente
 */
class genericProducer {
    private final String[] titles = { "Telecom", "Finmeccanica", "Banca_Intesa",
                    "Oracle", "Parmalat", "Mondadori", "Vodafone", "Barilla", "NajoGames" };
    private Random rnd = new Random();
    
    private String pickRandomTitle(){
        int randomIndex;
        randomIndex = rnd.nextInt(titles.length);
        return this.titles[randomIndex];
    }
    
    private float getRandomValue(){
        return (rnd.nextFloat()*100);
    }
    
    public void start() throws NamingException, JMSException, InterruptedException{
        Context jndiContext = null;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        
        Destination destination = null;
        String destinationName = "dynamicTopics/Quotazioni";
        
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        jndiContext = new InitialContext(props);
        
        connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        connection = (Connection) connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        
        destination = (Destination) jndiContext.lookup(destinationName);
        MessageProducer producer = session.createProducer(destination);
        TextMessage m = session.createTextMessage();
        
        String titolo = null;
        float quotazione;
        int i = 0;
        while(true){
            i++;
            titolo = pickRandomTitle();
            quotazione = getRandomValue();
            m.setStringProperty("Nome", titolo);
            m.setFloatProperty("Valore", quotazione);
            m.setText("Item: " + i + ", Name: " + titolo + ", Value: "+quotazione);
            producer.send(m);
            
            System.out.println("Item: " + i + ", Name: " + titolo + ", Value: "+quotazione);
            Thread.sleep(2000);
 
        }
        
    }
}
