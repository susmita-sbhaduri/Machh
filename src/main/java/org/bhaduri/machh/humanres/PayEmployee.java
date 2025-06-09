/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.humanres;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.EmpExpDTO;
import org.bhaduri.machh.DTO.EmployeeDTO;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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
    private Date sdate;
    private Date edate;
    private Date paydate = new Date();
    private String outstanding;
    private float payback;
    private float bonus;
    private float leave;
    private boolean newRecord = false;
    /**
     * Creates a new instance of PayEmployee
     */
    public PayEmployee() {
    }
    public void fillValues() throws NamingException {

        MasterDataServices masterDataService = new MasterDataServices();     
        
        empRec = masterDataService.getEmpNameForId(selectedEmp);
        selectedEmpName = empRec.getName();
        salary = empRec.getSalary();        
        List<EmpExpDTO> empLoanRecs = masterDataService.getEmpActiveExpRecs(selectedEmp);
        if(empLoanRecs.isEmpty()){
            totalLoan = 0;
            outstanding = String.format("%.2f", totalLoan);
            payback = 0;
            newRecord = true;
        } else {
            totalLoan = Float.parseFloat(empLoanRecs.get(0).getTotal());
            outstanding = empLoanRecs.get(0).getOutstanding();
            payback = totalLoan-Float.parseFloat(outstanding);
        }
        bonus = 0;
        leave = 0;
    }
    public void calculateOutstanding() throws NamingException {
        float outstngcalc = totalLoan - payback; 
        outstanding = String.format("%.2f", outstngcalc);
        
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
    
    
    public String payEmp() throws NamingException {
        String redirectUrl = "/secured/humanresource/maintainemp?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        updateExpense("SALARY", empRec.getSalary());
        if (bonus != 0) {
            updateExpense("BONUS", String.format("%.2f", bonus));
        }
        if (leave != 0) {
            updateExpense("LEAVE", String.format("%.2f", leave));
        }
        if (newRecord && (Float.parseFloat(outstanding) == totalLoan) 
                && (payback==0) && (totalLoan > 0)) {
            
            updateExpense("LOAN", String.format("%.2f", totalLoan));
            
            EmpExpDTO empexpRec = new EmpExpDTO();
            int empexpid = masterDataService.getMaxEmpExpenseId();
            if (empexpid == 0 || empexpid == DB_SEVERE) {
                empexpRec.setId("1");
            } else {
                empexpRec.setId(String.valueOf(empexpid + 1));
            }
            empexpRec.setTotal(String.format("%.2f", totalLoan));
            empexpRec.setOutstanding(outstanding);
            empexpRec.setExpcategory("LOAN");
            empexpRec.setSdate(sdf.format(paydate));
            empexpRec.setEdate(null);
            empexpRec.setEmpid(selectedEmp); //######empid as ref id
            int insempexp = masterDataService.addEmpExpRecord(empexpRec);
        }
        
//        if ("salary".equals(selectedEmp.getPaymentcat())) {
//            expenseRec.setExpenseType("SALARY");
//        }
//        if (bonus!=0) {
//            expenseRec.setExpenseType("BONUS");
//        }
//        if ("leave".equals(selectedEmp.getPaymentcat())) {
//            expenseRec.setExpenseType("LEAVE");
//        }
        
        return redirectUrl;

    }
    
    public String updateExpense(String empexptype, String expenseAmt) throws NamingException{
        String redirectUrl = "/secured/humanresource/maintainemp?faces-redirect=true"; 
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ExpenseDTO expenseRec = new ExpenseDTO();
        int expenseid = masterDataService.getNextIdForExpense();
        if (expenseid == 0 || expenseid == DB_SEVERE) {
            expenseRec.setExpenseId("1");
        } else {
            expenseRec.setExpenseId(String.valueOf(expenseid + 1));
        }
        expenseRec.setDate(sdf.format(paydate));
        expenseRec.setExpenseRefId(selectedEmp); //######empid as ref id
        expenseRec.setExpenseType(empexptype);
        expenseRec.setExpenditure(expenseAmt);
        expenseRec.setCommString(empexptype);
        int expres = masterDataService.addExpenseRecord(expenseRec);
        if (expres == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Expense added successfully");
            f.addMessage(null, message);
        } else {
            if (expres == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "Duplicate record error for expense table");
                f.addMessage(null, message);
            }
            if (expres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "Failure on insert in expense table");
                f.addMessage(null, message);
            }

        }
        return redirectUrl;
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

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }
    
    
}
