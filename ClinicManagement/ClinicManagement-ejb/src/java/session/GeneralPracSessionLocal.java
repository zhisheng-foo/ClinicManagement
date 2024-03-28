/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package session;

import entity.GeneralPrac;
import error.NoResultException;
import javax.ejb.Local;

/**
 *
 * @author foozh
 */
@Local
public interface GeneralPracSessionLocal {
    
    public void createGeneralPrac(GeneralPrac generalPrac);
    
    public GeneralPrac getGeneralPrac(Long gId) throws NoResultException;
    
    public GeneralPrac getGeneralPracByEmail(String email) throws NoResultException;
    
    public Boolean isValidGeneralPrac(String email, String password);

    public void updateGeneralPrac(GeneralPrac generalPrac) throws NoResultException;
    
    public void deleteScheduleDoc(Long sId) throws NoResultException;

}
