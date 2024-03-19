/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import error.NoResultException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.GeneralPracSessionLocal;
import session.PatientSessionLocal;
import session.ScheduleDocSessionLocal;

/**
 *
 * @author nicholas
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {
    
    @EJB
    private PatientSessionLocal patientSessionLocal;
    
    @EJB
    private GeneralPracSessionLocal generalPracSessionLocal;
    
    @EJB
    private ScheduleDocSessionLocal scheduleDocSessionLocal;

    private String email = null;
    private String password = null;
    private Long userId = -1L;

    public AuthenticationManagedBean() {
    }

    public void login() throws NoResultException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (isPatient()) {
            setUserId(patientSessionLocal.getPatientByEmail(email).getPatientId());
            context.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/patient/index.xhtml?faces-redirect=true");
        } else if (isGeneralPrac()) {
            setUserId(generalPracSessionLocal.getGeneralPracByEmail(email).getStaffId());
            context.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/staff/index.xhtml?faces-redirect=true");
        } else if (isScheduleDoc()) {
            setUserId(scheduleDocSessionLocal.getScheduleDocByEmail(email).getStaffId());
            context.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/staff/index.xhtml?faces-redirect=true");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Account does not exist or password is invalid");
            context.addMessage(null, message);
            setEmail(null);
            setPassword(null);
            setUserId(-1L);
        }
    }

    public String logout() {
        setEmail(null);
        setPassword(null);
        setUserId(-1L);
        return "/index.xhtml?faces-redirect=true";
    }
    
    public Boolean isPatient() {
        return patientSessionLocal.isValidPatient(email, password);
    }
    
    public Boolean isGeneralPrac() {
        return generalPracSessionLocal.isValidGeneralPrac(email, password);
    }
    
    public Boolean isScheduleDoc() {
        return scheduleDocSessionLocal.isValidScheduleDoc(email, password);
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}