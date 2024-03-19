/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package session;

import entity.ScheduleDoc;
import error.NoResultException;
import javax.ejb.Local;

/**
 *
 * @author foozh
 */
@Local
public interface ScheduleDocSessionLocal {
    
    public void createScheduleDoc(ScheduleDoc scheduleDoc);
    
    public ScheduleDoc getScheduleDoc(Long sId) throws NoResultException;
    
    public void updateScheduleDoc(ScheduleDoc scheduleDoc) throws NoResultException;
    
    public void deleteScheduleDoc(Long sId) throws NoResultException;
   
}
