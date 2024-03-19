/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package session;

import entity.GeneralPrac;
import entity.Patient;
import error.NoResultException;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author foozh
 */
@Stateless
public class PatientSession implements PatientSessionLocal {

    @PersistenceContext(unitName = "ClinicManagement-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public void createPatient(Patient patient) {
         em.persist(patient);
    }

    @Override
    public Patient getPatient(Long pId) throws NoResultException {
        
         Patient patient = em.find(Patient.class, pId);

        if (patient != null) {
            return patient;
        } else {
            throw new NoResultException("Not found");
        }
    }

    @Override
    public void updatePatient(Patient patient) throws NoResultException {
        
        Patient patienttoUpdate = getPatient(patient.getPatientId());
        patienttoUpdate.setContact(patient.getContact());
        patienttoUpdate.setEmail(patient.getEmail());
        patienttoUpdate.setFirstName(patient.getFirstName());
        patienttoUpdate.setLastName(patient.getLastName());
        patienttoUpdate.setGender(patient.getGender());
        patienttoUpdate.setPassword(patient.getPassword());
        patienttoUpdate.setPatientType(patient.getPatientType());
       

        em.merge(patienttoUpdate);
    }

    @Override
    public void deletePatient(Long pId) throws NoResultException {
        
         Patient patient = em.find(Patient.class, pId);

        if (patient == null) {
            throw new NoResultException("Patient not found for ID: " + pId);
        }

        if (patient.getAppointments()!= null) {
            new ArrayList<>(patient.getAppointments()).stream().map(appointment -> {
                appointment.setStaff(null);
                return appointment;
            }).forEachOrdered(appointment -> {
                em.merge(appointment);
            });
        }

        em.remove(patient);
        
    }
}
