/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.Appointment;
import entity.Staff;
import error.NoResultException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author foozh
 */
@Stateless
public class AppointmentSession implements AppointmentSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;
    
    
    @Override
    public void createAppointment(Appointment appointment) {
        em.persist(appointment);
    } //end createAppointment
    
    @Override
    public Appointment getAppointment(Long aId) throws NoResultException {
        Appointment appointment = em.find(Appointment.class, aId);

        if (appointment != null) {
            return appointment;
        } else {
            throw new NoResultException("Not found");
        }
    } //end getAppointment
    
    @Override
    public void updateAppointment(Appointment appointment) throws NoResultException {
        Appointment oldA = getAppointment(appointment.getAppointmentId());
        
         oldA.setCreatedDateTime(appointment.getCreatedDateTime());
         oldA.setAppointDateTime(appointment.getAppointDateTime());
         oldA.setAppointmentType(appointment.getAppointmentType());
         oldA.setStatus(appointment.getStatus());
         oldA.setStaff(appointment.getStaff());
             
         em.merge(oldA);
    } // end updateAppointment
    
    @Override
    public void deleteAppointment(Long aId) throws NoResultException {
        Appointment appointment = em.find(Appointment.class, aId);

        if (appointment == null) {
            throw new NoResultException("Appointment not found for ID: " + aId);
        }
        
        Staff staff = appointment.getStaff();
        if (staff != null) {
            staff.getAppointments().remove(appointment); 
            em.merge(staff); 
        }
        em.remove(appointment);
    } // end deleteAppointment
}
