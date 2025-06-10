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
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.EmpExpDTO;
import org.bhaduri.machh.DTO.EmployeeDTO;
import org.bhaduri.machh.DTO.ExpenseDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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
    private float salary;
    private Date paydate = new Date();    
    private float payback;
    private float bonus;
    private float leave;
    private boolean readOnlyCondition = false;
    /**
     * Creates a new instance of PayEmployee
     */
    public PayEmployee() {
    }
    public void fillValues() throws NamingException {

        MasterDataServices masterDataService = new MasterDataServices();     
        
        empRec = masterDataService.getEmpNameForId(selectedEmp);
        selectedEmpName = empRec.getName();
        salary = Float.parseFloat(empRec.getSalary());        
        List<EmpExpDTO> empLoanRecs = masterDataService.getEmpActiveExpRecs(selectedEmp);
        if(empLoanRecs.isEmpty()){            
           readOnlyCondition = true;
        } 
        bonus = 0;
        leave = 0;
    }
    
    public String payEmp() throws NamingException {
        String redirectUrl = "/secured/humanresource/maintainemp?faces-redirect=true"; 
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        int resp;
        if (salary > 0) {
            resp = createExpenseRec("SALARY", empRec.getSalary());
            if (resp == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Salary added successfully");
                f.addMessage(null, message);
            } else {
                if (resp == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Duplicate record error for expense table");
                    f.addMessage(null, message);
                }
                if (resp == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on insert in expense table");
                    f.addMessage(null, message);
                }

            }
        }
        if (bonus > 0) {
            resp = createExpenseRec("BONUS", String.format("%.2f", bonus));
            if (resp == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Bonus added successfully");
                f.addMessage(null, message);
            } else {
                if (resp == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Duplicate record error for expense table");
                    f.addMessage(null, message);
                }
                if (resp == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on insert in expense table");
                    f.addMessage(null, message);
                }

            }
        }
        if (leave > 0) {
            resp = createExpenseRec("LEAVE", String.format("%.2f", leave));
            if (resp == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Leave allowance added successfully");
                f.addMessage(null, message);
            } else {
                if (resp == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Duplicate record error for expense table");
                    f.addMessage(null, message);
                }
                if (resp == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on insert in expense table");
                    f.addMessage(null, message);
                }

            }
        }
        
        if(readOnlyCondition==false){
            
            MasterDataServices masterDataService = new MasterDataServices();
            EmpExpDTO empexpUpd = masterDataService.getEmpActiveExpRecs(selectedEmp).get(0);
            float totalLoan = Float.parseFloat(empexpUpd.getTotal());
            float outstngcalc = totalLoan - payback;
            empexpUpd.setOutstanding(String.format("%.2f", outstngcalc));
            
            int empexpupd = masterDataService.editEmpExpRecord(empexpUpd);
            if (empexpupd == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Loan updated successfully");
                f.addMessage(null, message);
            } else {
                if (empexpupd == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "This expexpense record does not exist.");
                    f.addMessage(null, message);
                }
                if (empexpupd == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on updating expexpense record.");
                    f.addMessage(null, message);
                }
                
            }
        }
        return redirectUrl;

    }
    
    public int createExpenseRec(String empexptype, String expenseAmt) throws NamingException{
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
        return expres;
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

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
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

    public boolean isReadOnlyCondition() {
        return readOnlyCondition;
    }

    public void setReadOnlyCondition(boolean readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }    
    
}
