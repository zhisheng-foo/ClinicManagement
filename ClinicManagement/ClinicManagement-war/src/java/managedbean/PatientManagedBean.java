/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Appointment;
import entity.Patient;
import enumeration.PatientTypeEnum;
import error.NoResultException;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import session.PatientSessionLocal;

/**
 *
 * @author nicholas
 */
@Named(value = "patientManagedBean")
@ViewScoped
public class PatientManagedBean implements Serializable {
    
    @EJB
    private PatientSessionLocal patientSessionLocal;
    
    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    private String firstName;
    private String lastName;
    private byte gender;
    private String email;
    private String password;
    private String contact;
    private PatientTypeEnum patientType;
    
    private List<Appointment> appointments;
    
    private String confirmPassword;
    private Date currentDateTime;
    
    private Patient loggedInPatient;

    public PatientManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            setLoggedInPatient(patientSessionLocal.getPatient(authenticationManagedBean.getUserId()));
            setCurrentDateTime(new Date());
        } catch (NoResultException e) {
            //do nothing
        }
    } //end init
    
    //8 Character, must have characters and numerics
    public boolean isPasswordStrong(String password) {
        String pattern = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$";
        return password.matches(pattern);
    }
    
    //under the assumption that we would use prime faces growl
    //currently this method simply adds the customer, no form of redirect to login.xhtml, can change if you want to
    public void addPatient() throws NoResultException {
       
        if (!isPasswordStrong(password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Password is too weak. Minimum eight characters,inclusive of at least one letter and one number"));
            return;
        }
        
        List<Patient> patientWithSameName = patientSessionLocal.searchPatients(email);
        if (!patientWithSameName.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation Error", "A customer with this name already exists."));
            firstName = "";
            lastName = "";
            gender = 0;
            contact = "";
            email = "";
            password = "";
           
            return;
        }

        Patient p = new Patient();

        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setGender(gender);
        p.setEmail(email);
        p.setPassword(password);
        p.setContact(contact);
        p.setPatientType(patientType);

        p.setAppointments(new ArrayList<>());
        patientSessionLocal.createPatient(p);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Flash flash = externalContext.getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Patient Added Successfully"));
        authenticationManagedBean.setEmail(getEmail());
        authenticationManagedBean.setPassword(getPassword());

        try {
            // Perform the redirect to the same page
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/register.xhtml");
            firstName = "";
            lastName = "";
            gender = 0;
            contact = "";
            email = "";
            password = "";
            
        } catch (IOException e) {

        }
        /* I am not sure what this is ;O
         else {
            //perform login automatically upon account creation
            authenticationManagedBean.setEmail(getEmail());
            authenticationManagedBean.setPassword(getPassword());
            //login method will perform redirect
            authenticationManagedBean.login();
        }
         */
    } //end addPatient


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public PatientTypeEnum getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientTypeEnum patientType) {
        this.patientType = patientType;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(Date currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }
    
}