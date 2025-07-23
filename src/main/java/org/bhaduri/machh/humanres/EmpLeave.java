/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.humanres;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.EmpLeaveDTO;
import org.bhaduri.machh.DTO.EmployeeDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "empLeave")
@ViewScoped
public class EmpLeave implements Serializable {
    private List<EmployeeDTO> employees;
    private int selectedIndex;
    private Date leaveDt = new Date();
    private String comments;
    /**
     * Creates a new instance of EmpLeave
     */
    public EmpLeave() {
    }
    public String fillValues() throws NamingException, IOException {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String redirectUrl = contextPath + "/faces/secured/humanresource/createemp.xhtml";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        
        employees = masterDataService.getActiveEmployeeList();
        if (employees.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "No active employee.");
            f.addMessage(null, message);
            // Do the redirect here:
            f.getExternalContext().redirect(redirectUrl);
            f.responseComplete();
            return null; // Return null after a programmatic redirect
        } 
        else return null;        
    }
    
    public String saveEmpLeave() throws NamingException {
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        String redirectUrl = "/secured/humanresource/empleave?faces-redirect=true";
        
        MasterDataServices masterDataService = new MasterDataServices();
        EmpLeaveDTO leaveRec = new EmpLeaveDTO();
        int maxid = masterDataService.getMaxEmpLeaveId();
        if (maxid == 0 || maxid == DB_SEVERE) {
            leaveRec.setId("1");
        } else {
            leaveRec.setId(String.valueOf(maxid + 1));
        }
        leaveRec.setEmpid(employees.get(selectedIndex).getId());
               
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        leaveRec.setLeavedate(sdf.format(leaveDt));
        leaveRec.setComments(comments);
        
        int response = masterDataService.addEmpleaveRecord(leaveRec);
        if (response == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Employee added successfully");
            f.addMessage(null, message);
//            return "/secured/userhome?faces-redirect=true";
            return redirectUrl;
        } else {  
            if (response == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        Integer.toString(DB_DUPLICATE));
                f.addMessage(null, message);
            } 
            if (response == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        Integer.toString(DB_SEVERE));
                f.addMessage(null, message);
            } 
            return "/secured/userhome?faces-redirect=true";
        }
        
    }
    
    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public Date getLeaveDt() {
        return leaveDt;
    }

    public void setLeaveDt(Date leaveDt) {
        this.leaveDt = leaveDt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
}
