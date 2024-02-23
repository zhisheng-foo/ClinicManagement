/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author foozh
 */
@Stateless
public class ScheduleDocSession implements ScheduleDocSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
}