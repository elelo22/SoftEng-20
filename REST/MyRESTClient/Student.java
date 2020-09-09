/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myrestclient;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author studente
 */

@XmlRootElement(name = "Student")
class Student {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Student) && ((Student) obj).getId()==this.id && ((Student) obj).getName().equals(this.name);
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + '}';
    }
    
}
