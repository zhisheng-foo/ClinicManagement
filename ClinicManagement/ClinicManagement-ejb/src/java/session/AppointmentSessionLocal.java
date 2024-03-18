/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package session;

import entity.Appointment;
import error.NoResultException;
import javax.ejb.Local;

/**
 *
 * @author foozh
 */
@Local
public interface AppointmentSessionLocal {
    
    public void createAppointment(Appointment appointment);
    
    public Appointment getAppointment(Long aId) throws NoResultException;
    
    public void updateAppointment(Appointment appointment) throws NoResultException;
    
    public void deleteAppointment(Long aId) throws NoResultException;
       
}
