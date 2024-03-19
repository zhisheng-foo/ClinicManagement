/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.ScheduleDoc;
import error.NoResultException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author foozh
 */
@Stateless
public class ScheduleDocSession implements ScheduleDocSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;

    @Override
    public void createScheduleDoc(ScheduleDoc scheduleDoc) {
        em.persist(scheduleDoc);
    } //end createScheduleDoc 
    
    @Override
    public ScheduleDoc getScheduleDoc(Long sId) throws NoResultException {
        ScheduleDoc scheduleDoc = em.find(ScheduleDoc.class, sId);

        if (scheduleDoc != null) {
            return scheduleDoc;
        } else {
            throw new NoResultException("Not found");
        }
    } //end getScheduleDoc
    
    @Override
    public ScheduleDoc getScheduleDocByEmail(String email) throws NoResultException {
        Query q = em.createQuery("SELECT sd FROM ScheduleDoc sd WHERE sd.email = :email")
                .setParameter("email", email);
        
        List<ScheduleDoc> scheduleDocs = q.getResultList();
        
        if (scheduleDocs != null) {
            return scheduleDocs.get(0);
        } else {
            throw new NoResultException("Error: ScheduleDoc with email " + email + " not found!");
        }
    }
    
    @Override
    public Boolean isValidScheduleDoc(String email, String password) {
        Query q = em.createQuery("SELECT sd FROM ScheduleDoc sd WHERE sd.email = :email AND sd.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        
        return !q.getResultList().isEmpty();
    }
    
    @Override
    public void updateScheduleDoc(ScheduleDoc scheduleDoc) throws NoResultException {
        ScheduleDoc oldSd = getScheduleDoc(scheduleDoc.getStaffId());
        
         oldSd.setAvailableSlots(scheduleDoc.getAvailableSlots());
         oldSd.setFirstName(scheduleDoc.getFirstName());
         oldSd.setLastName(scheduleDoc.getLastName());
         oldSd.setEmail(scheduleDoc.getEmail());
         oldSd.setPassword(scheduleDoc.getPassword());
         oldSd.setAppointments(scheduleDoc.getAppointments());
         
         em.merge(oldSd);
    } // end updateSlot
    
    @Override
    public void deleteScheduleDoc(Long sId) throws NoResultException {
        
        ScheduleDoc scheduleDoc = em.find(ScheduleDoc.class, sId);

        if (scheduleDoc == null) {
            throw new NoResultException("ScheduleDoc not found for ID: " + sId);
        }

        
        if (scheduleDoc.getAvailableSlots() != null) {
            new ArrayList<>(scheduleDoc.getAvailableSlots()).stream().map(slot -> {
                slot.setScheduleDoc(null);
                return slot;
            }).forEachOrdered(slot -> {
                em.merge(slot);
            });
        }
        
        if (scheduleDoc.getAppointments() != null) {
            new ArrayList<>(scheduleDoc.getAppointments()).stream().map(appointment -> {
                appointment.setStaff(null);
                return appointment;
            }).forEachOrdered(appointment -> {
                em.merge(appointment);
            });
        }

        em.remove(scheduleDoc);
    } //deleteScheduleDoc
}
