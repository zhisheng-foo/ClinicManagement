/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Appointment;
import entity.Patient;
import enumeration.PatientTypeEnum;
import error.NoResultException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private byte gender = 0;
    private String email;
    private String password;
    private String contact = "12345678";
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
    
    public void addPatient() throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!email.contains("@")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Invalid email, please check again"));
        } else if (!contact.matches("\\d+")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Invalid phone number, please check again"));
        } else if (getContact().length() < 8) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Phone number must be at least 8 digits, please check again"));
        } else if (!password.equals(confirmPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Passwords do not match, please check again"));
        } else if (!patientSessionLocal.isAvailableEmail(email)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email is already in use, please use a different email"));
        } else {
            System.out.println(getFirstName());
            System.out.println(getLastName());
            System.out.println(getGender());
            System.out.println(getEmail());
            System.out.println(getPassword());
            System.out.println(getContact());
            Patient p = new Patient();
            p.setFirstName(getFirstName());
            p.setLastName(getLastName());
            //p.setGender(getGender());
            p.setEmail(getEmail());
            p.setPassword(getPassword());
            p.setContact("12345678");
            //p.setContact(getContact());

            patientSessionLocal.createPatient(p);

            //perform login automatically upon account creation
            authenticationManagedBean.setEmail(getEmail());
            authenticationManagedBean.setPassword(getPassword());
            //login method will perform redirect
            authenticationManagedBean.login();
        }
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
