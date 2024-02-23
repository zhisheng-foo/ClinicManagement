/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author foozh
 */
@Entity
public class GeneralPrac extends Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<String> queue;

    public GeneralPrac() {
    }
    
    public List<String> getQueue() {
        return queue;
    }

    public void setQueue(List<String> queue) {
        this.queue = queue;
    }   
}
