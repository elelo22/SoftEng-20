/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.mycompany.soap.webservice.Student;
import com.mycompany.soap.webservice.StudentEntry;
import com.mycompany.soap.webservice.StudentMap;
import java.util.List;

/**
 *
 * @author studente
 */
public class Client {
    
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setName("Eleonora");
        Client.helloStudent(s1);
        
        Student s2 = new Student();
        s2.setName("Giulio");
        Client.helloStudent(s2);
        
        List<StudentEntry> studs = Client.getStudents().getEntry();
        for (int i=0 ; i<studs.size(); i++){
            System.out.println(studs.get(i).getStudent().getName());
        }
    }

    private static StudentMap getStudents() {
        com.mycompany.soap.webservice.WSImplService service = new com.mycompany.soap.webservice.WSImplService();
        com.mycompany.soap.webservice.WSInterface port = service.getWSImplPort();
        return port.getStudents();
    }

    private static String hello(java.lang.String arg0) {
        com.mycompany.soap.webservice.WSImplService service = new com.mycompany.soap.webservice.WSImplService();
        com.mycompany.soap.webservice.WSInterface port = service.getWSImplPort();
        return port.hello(arg0);
    }

    private static String helloStudent(com.mycompany.soap.webservice.Student arg0) {
        com.mycompany.soap.webservice.WSImplService service = new com.mycompany.soap.webservice.WSImplService();
        com.mycompany.soap.webservice.WSInterface port = service.getWSImplPort();
        return port.helloStudent(arg0);
    }
}
