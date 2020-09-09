/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myrestclient;

import java.io.*;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXB;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.*;

/**
 *
 * @author studente
 */
public class Client {
    private static final String BASE_URL = "http://localhost:8080/courses/";
    private static CloseableHttpClient client;

    public static void main(String[] args) throws IOException {
        client = HttpClients.createDefault();
        
        //GET
        System.out.println("GET example - course #1:");
        Course c1 = getCourse(1);
        System.out.println(c1);
        
        System.out.println("\nStudents in course #1:");
        List<Student> l = c1.getStudents();
        for (Student  s : l){
            System.out.println(s);
        }
        System.out.println("////////////\n");
        
        System.out.println("GET example - student #1 of course #1:");
        Student s1 = getStudent(1, 1);
        System.out.println(s1);
        
        System.out.println("////////////\n");
        
        System.out.println("GET example - course #2:");
        Course c2 = getCourse(2);
        System.out.println(c2);
        
        System.out.println("\nStudents in course #2:");
        List<Student> l2 = c2.getStudents();
        for (Student  s : l2){
            System.out.println(s);
        }
        System.out.println("////////////\n");
        
        System.out.println("POST example - add student to course #2:");
        postStudent(2);
        
        c2 = getCourse(2);
        System.out.println("Course #2 AFTER POST:");
        System.out.println(c2);
        
        System.out.println("\nStudents in course #2 AFTER POST:");
        l2 = c2.getStudents();
        for (Student  s : l2){
            System.out.println(s);
        }
        System.out.println("////////////\n");
        
        
        System.out.println("PUT example - edit course #3:");
        putCourse(1);
        c1 = getCourse(1);
        System.out.println("course #1 AFTER PUT:");
        System.out.println(c1);
       
        System.out.println("////////////\n");
        System.out.println("DELETE example - student #3");
        deleteStudent(2, 3);
        c2 = getCourse(2);
        System.out.println(c2);
    }
    
    //GET
    private static Course getCourse(int courseID) throws IOException{
        final URL url = new URL(BASE_URL + courseID);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Course.class);
    }
    
    private static Student getStudent(int courseID, int studentID) throws IOException{
        final URL url = new URL(BASE_URL + courseID + "/students/" + studentID);
        final InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Student.class);
    }
    
    //POST
    private static void postStudent(int courseID) throws IOException{
        final HttpPost httpPost = new HttpPost(BASE_URL + courseID +"/students");
        final InputStream resourceStream =  Client.class.getClassLoader().getResourceAsStream("newStudent.xml");
        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        System.out.println("POST Request of Curse with ID="+ courseID +"///executed with RESPONSE= "+ response.getStatusLine());
        
    }
    
    //PUT
    private static void putCourse(int courseID) throws IOException{
        HttpPut httpPut = new HttpPut(BASE_URL + courseID);
        InputStream resourceStream =  Client.class.getClassLoader().getResourceAsStream("editCourse.xml");
        httpPut.setEntity(new InputStreamEntity(resourceStream));
        httpPut.setHeader("Content-Type", "text/xml");

        HttpResponse response = client.execute(httpPut);
        System.out.println("PUT Request of Student with ID="+ courseID +"///executed with RESPONSE= "+ response.getStatusLine());
    }
    
    //DELETE
    private static void deleteStudent(int courseID, int studentID) throws IOException{
        HttpDelete httpDelete = new HttpDelete(BASE_URL+courseID+"/students/"+studentID);
        httpDelete.setHeader("Content-Type","text/xml");
        
        HttpResponse response = client.execute(httpDelete);
        System.out.println("DELETE Request of ID=" + studentID + "///executed with RESPONSE= " + response.getStatusLine());
    }
    
    
}
