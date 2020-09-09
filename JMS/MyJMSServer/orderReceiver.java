/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsservant;

import java.util.Properties;
import javax.jms.*;
import javax.naming.*;

/**
 *
 * @author studente
 */
class orderReceiver {
    public void start() throws NamingException, JMSException{
        Context jndiContext = null;
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        
        Destination destination = null;
        String destinationName = "dynamicTopics/Ordini";
        
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        jndiContext = new InitialContext(props);
        
        connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
        connection = (Connection) connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        
        destination = (Destination) jndiContext.lookup(destinationName);
        MessageConsumer consumer = session.createConsumer(destination);
        MessageListener listener = new myListenerOrdini(session, destination);
        consumer.setMessageListener(listener);
        
    }
}
