/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.ScheduleDoc;
import entity.Slot;
import error.NoResultException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author foozh
 */
@Stateless
public class SlotSession implements SlotSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;

    
    @Override
    public void createSlot(Slot slot) {
        em.persist(slot);
    } //end createSlot 
    
    
    @Override
    public Slot getSlot(Long sId) throws NoResultException {
        Slot slot = em.find(Slot.class, sId);

        if (slot != null) {
            return slot;
        } else {
            throw new NoResultException("Not found");
        }
    } //end getAppointment
    
    @Override
    public void updateSlot(Slot slot) throws NoResultException {
        Slot oldS = getSlot(slot.getSlotId());
        
         oldS.setSlotDateTime(slot.getSlotDateTime());
         oldS.setIsBooked(slot.isIsBooked());
             
         em.merge(oldS);
    } // end updateSlot
    
    @Override
    public void deleteSlot(Long sId) throws NoResultException {
        Slot slot = em.find(Slot.class, sId);

        if (slot == null) {
            throw new NoResultException("Slot not found for ID: " + sId);
        }
        
        ScheduleDoc scheduleDoc = slot.getScheduleDoc();
        if (scheduleDoc != null && scheduleDoc.getAvailableSlots() != null) {
            scheduleDoc.getAvailableSlots().remove(slot);
            em.merge(scheduleDoc);
        }
        em.remove(slot);
    } // end deleteAppointment
}
