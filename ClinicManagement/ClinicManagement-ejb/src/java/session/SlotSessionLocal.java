/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package session;

import entity.Slot;
import error.NoResultException;
import javax.ejb.Local;

/**
 *
 * @author foozh
 */
@Local
public interface SlotSessionLocal {
    
    public void createSlot(Slot slot);
    
    public Slot getSlot(Long sId) throws NoResultException;
    
    public void updateSlot(Slot slot) throws NoResultException;
    
    public void deleteSlot(Long sId) throws NoResultException;    
}
