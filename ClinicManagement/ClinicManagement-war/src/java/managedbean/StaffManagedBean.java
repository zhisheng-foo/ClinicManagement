/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author shionhigashi
 */
@Named(value = "staffManagedBean")
@SessionScoped
public class StaffManagedBean implements Serializable {

    /**
     * Creates a new instance of StaffManagedBean
     */
    public StaffManagedBean() {
    }
    
    public void checkPatientIn(Long appointmentId) {

    }
    
    public void noShowPatient(Long appointmentId) {
        
    }
}
