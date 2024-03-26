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
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.PatientSessionLocal;
import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import javax.servlet.http.Part;


/**
 *
 * @author 65816
 */
@Named(value = "patientmanagedbean")
@SessionScoped
public class PatientManagedBean implements Serializable {

    @EJB
    private PatientSessionLocal patientSessionLocal;

    private Patient loggedInPatient;
    private String firstName;
    private String lastName;
    private String fullName;
    private String password;
    private String contact;
    private String email;
    private String profilePhoto;
    private byte gender;
    private PatientTypeEnum patientType;
    private List<Appointment> appointments;
    private String confirmPassword;
    private Date currentDateTime;
    private boolean editMode = false;
    private Part uploadedFile;
    

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    /**
     * Creates a new instance of patientmanagedbean
     */
    public PatientManagedBean() {
    }

    @PostConstruct
    public void init() {

        try {
            setLoggedInPatient(patientSessionLocal.getPatient(authenticationManagedBean.getUserId()));
            setCurrentDateTime(new Date());
            setFirstName(loggedInPatient.getFirstName());
            setLastName(loggedInPatient.getLastName());
            setPassword(loggedInPatient.getPassword());
            setContact(loggedInPatient.getContact());
            setEmail(loggedInPatient.getEmail());
            setGender(loggedInPatient.getGender());
            setPatientType(loggedInPatient.getPatientType());
            setAppointments(loggedInPatient.getAppointments());
            setFullName(firstName + lastName);

        } catch (NoResultException e) {
            //do nothing
        }

    }

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
        } else if (!patientSessionLocal.isAvailEmail(email)) {
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
    
    
    
    public void toggleEditMode() {
        editMode = !editMode;
        LOGGER.info("Edit mode toggled: " + editMode);
        LOGGER.info("Edit Mode  : " + profilePhoto);

    }

    public void saveProfile() throws NoResultException {

        LOGGER.info("Saving profile for: " + loggedInPatient.getEmail());
        LOGGER.info("Current profilePhoto path: " + profilePhoto);
        loggedInPatient.setEmail(email);
        loggedInPatient.setFirstName(firstName);
        loggedInPatient.setLastName(lastName);
        loggedInPatient.setEmail(email);
        loggedInPatient.setGender(gender);
        loggedInPatient.setPatientType(patientType);
        loggedInPatient.setAppointments(appointments);
        patientSessionLocal.updatePatient(loggedInPatient);
     
      
        editMode = false;
        LOGGER.info("Profile saved successfully.");

    }
    
    

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
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

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
    
    
    
    

}
