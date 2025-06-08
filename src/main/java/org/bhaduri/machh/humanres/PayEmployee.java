/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.humanres;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.EmployeeDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "payEmployee")
@ViewScoped
public class PayEmployee implements Serializable {
    private String selectedEmp;
    private String selectedEmpName;
    private EmployeeDTO empRec;
    private String salary;    
    private float totalLoan;
    private Date sdate = new Date();
    private Date edate;
    private String outstanding;
    private float payback;
    private float bonus;
    private float leave;
    /**
     * Creates a new instance of PayEmployee
     */
    public PayEmployee() {
    }
    public void fillValues() throws NamingException {

        MasterDataServices masterDataService = new MasterDataServices();     
        
        EmployeeDTO selectedEmpDto = masterDataService.getEmpNameForId(selectedEmp);
        selectedEmpName = selectedEmpDto.getName();
        salary = selectedEmpDto.getSalary();
        
    }
    public void calculateOutstanding() throws NamingException {
//        System.out.println("No crop categories are found." + selectedShop);
//        MasterDataServices masterDataService = new MasterDataServices();
//        
//        List<ShopResDTO> selectedShopResLst = masterDataService.getResShopForPk(selectedRes, selectedShop);
//        selectedShopRes = selectedShopResLst.get(0);
////        rate = Float.parseFloat(selectedShopRes.getRate());
//        rate = 0;
//        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
//        String redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="+ selectedShopRes.getResourceId();
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        if(Float.parseFloat(selectedShopRes.getRate())==0){
//           message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                    "This resource is getting acquired for the first time.");
//            f.addMessage("rate", message); 
//            return redirectUrl;
//        } else return null; 
    }
    
    public String goToSaveRes() throws NamingException {
        String redirectUrl = "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
        
        return redirectUrl;
    }
    public String getSelectedEmp() {
        return selectedEmp;
    }

    public void setSelectedEmp(String selectedEmp) {
        this.selectedEmp = selectedEmp;
    }

    public String getSelectedEmpName() {
        return selectedEmpName;
    }

    public void setSelectedEmpName(String selectedEmpName) {
        this.selectedEmpName = selectedEmpName;
    }

    public EmployeeDTO getEmpRec() {
        return empRec;
    }

    public void setEmpRec(EmployeeDTO empRec) {
        this.empRec = empRec;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public float getTotalLoan() {
        return totalLoan;
    }

    public void setTotalLoan(float totalLoan) {
        this.totalLoan = totalLoan;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public float getPayback() {
        return payback;
    }

    public void setPayback(float payback) {
        this.payback = payback;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getLeave() {
        return leave;
    }

    public void setLeave(float leave) {
        this.leave = leave;
    }
    
    
}
