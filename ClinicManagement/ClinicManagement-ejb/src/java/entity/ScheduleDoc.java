/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class ScheduleDoc extends Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "scheduleDoc", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<Slot> availableSlots;

    public ScheduleDoc() {
    }

    public List<Slot> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<Slot> availableSlots) {
        this.availableSlots = availableSlots;
    }
}
