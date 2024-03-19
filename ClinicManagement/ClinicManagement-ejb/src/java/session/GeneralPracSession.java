/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.GeneralPrac;
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
public class GeneralPracSession implements GeneralPracSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;

    @Override
    public void createGeneralPrac(GeneralPrac generalPrac) {
        em.persist(generalPrac);
    } //end createGeneralPrac 
    
    @Override
    public GeneralPrac getGeneralPrac(Long gId) throws NoResultException {
        GeneralPrac generalPrac = em.find(GeneralPrac.class, gId);

        if (generalPrac != null) {
            return generalPrac;
        } else {
            throw new NoResultException("Not found");
        }
    } //end getScheduleDoc
    
    @Override
    public GeneralPrac getGeneralPracByEmail(String email) throws NoResultException {
        Query q = em.createQuery("SELECT gp FROM GeneralPrac gp WHERE gp.email = :email")
                .setParameter("email", email);
        
        List<GeneralPrac> generalPracs = q.getResultList();
        
        if (generalPracs != null) {
            return generalPracs.get(0);
        } else {
            throw new NoResultException("Error: GeneralPrac with email " + email + " not found!");
        }
    }
    
    @Override
    public Boolean isValidGeneralPrac(String email, String password) {
        Query q = em.createQuery("SELECT gp FROM GeneralPrac gp WHERE gp.email = :email AND gp.password = :password")
                .setParameter("email", email)
                .setParameter("password", password);
        
        return !q.getResultList().isEmpty();
    }
    
    @Override
    public void updateGeneralPrac(GeneralPrac generalPrac) throws NoResultException {
        GeneralPrac oldGp = getGeneralPrac(generalPrac.getStaffId());

        oldGp.setQueue(generalPrac.getQueue());
        oldGp.setFirstName(generalPrac.getFirstName());
        oldGp.setLastName(generalPrac.getLastName());
        oldGp.setEmail(generalPrac.getEmail());
        oldGp.setPassword(generalPrac.getPassword());
        oldGp.setAppointments(generalPrac.getAppointments());

        em.merge(oldGp);
    } // end updateSlot
    
    @Override
    public void deleteScheduleDoc(Long sId) throws NoResultException {

        GeneralPrac generalPrac = em.find(GeneralPrac.class, sId);

        if (generalPrac == null) {
            throw new NoResultException("General Prac not found for ID: " + sId);
        }

        if (generalPrac.getAppointments() != null) {
            new ArrayList<>(generalPrac.getAppointments()).stream().map(appointment -> {
                appointment.setStaff(null);
                return appointment;
            }).forEachOrdered(appointment -> {
                em.merge(appointment);
            });
        }

        em.remove(generalPrac);
    } //deleteGeneralPrac 
    
}
