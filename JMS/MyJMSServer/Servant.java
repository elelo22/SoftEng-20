/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsservant;

import org.apache.activemq.broker.BrokerService;

/**
 *
 * @author studente
 */
public class Servant {
    public static void main(String[] args) throws Exception{
        //BrokerService broker = new BrokerService();
        //broker.addConnector("tcp://127.0.0.1:61616");
        
        //broker.start();

        //ORDER MATTERSt!!!!!!
        orderReceiver receiver = new orderReceiver();
        receiver.start();
        
        genericProducer producer = new genericProducer();
        producer.start();
        

        
    }
}
