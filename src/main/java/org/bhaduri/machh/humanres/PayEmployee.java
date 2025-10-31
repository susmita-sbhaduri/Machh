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
import java.text.ParseException;
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
    private float leaveApplicable;
    private String outstanding;
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
        salary = 0; 
//        If the employee has LOAN then EmpExpense record will have a record. Based on this, Loan Repayment
//        field is based editable. If there is no record then this field becomes readonly
        List<EmpExpDTO> empLoanRecs = masterDataService.getEmpActiveExpRecs(selectedEmp, "LOAN");
        if(empLoanRecs.isEmpty()){            
           readOnlyCondition = true;
           outstanding = "NA";
        } else
            //at one time only one loan will be active hence 0th record is taken out            
            outstanding = empLoanRecs.get(0).getOutstanding();
        bonus = 0; 
        leaveApplicable = (Float.parseFloat(empRec.getSalary())/2) - 
                ((Float.parseFloat(empRec.getSalary())/30)*masterDataService.getCountLeaveEmp(selectedEmp));
        leave = 0;
    }
    
    public String payEmp() throws NamingException, ParseException {
        String redirectUrl = "/secured/humanresource/maintainemp?faces-redirect=true"; 
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        int resp;
        if (salary > 0) {
            if (salary > Float.parseFloat(empRec.getSalary())) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "Paid salary cannot more than the salary set for the employee");
                f.addMessage(null, message);
                return "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
            }
            // there is loan and user has entered payback
            if(readOnlyCondition==false){
                if (payback > 0) {
                    //Payback + Salary cannot be greater than salary as payback will be deducted from salary
                    if ((payback + salary) > Float.parseFloat(empRec.getSalary())) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Payback + Salary cannot be greater than salary");
                        f.addMessage(null, message);
                        return "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
                    }
                }                
            }
            resp = createExpenseRec("SALARY", String.format("%.2f",salary));
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
        // there is loan hence one can enter payback and Loan Repayment is an editable field. 
        if(readOnlyCondition==false){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            MasterDataServices masterDataService = new MasterDataServices();
            String expCat = "LOAN";
        //The closed loan that is the enddate field in empexpense record is not filled up, is taken out.
        //One loan can be active at a time. Hence 0th record is selected.
            EmpExpDTO empexpUpd = masterDataService.getEmpActiveExpRecs(selectedEmp, expCat).get(0);
            if (payback > 0) {
                //Payback + Salary cannot be greater than salary
//                if((payback+salary)> Float.parseFloat(empRec.getSalary())){
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                            "Payback + Salary cannot be greater than salary");
//                    f.addMessage(null, message);
//                    return "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
//                }
                Date loanStartDate = sdf.parse(empexpUpd.getSdate());
                if (paydate.compareTo(loanStartDate) < 0) {//if paydate is before loanStartDate
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Repayment date must be after Loan granted date");
                    f.addMessage(null, message);
                    return "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
                }
                //Based on the repayment amount outstngcalc is calculated.
                float outstngcalc = Float.parseFloat(empexpUpd.getOutstanding()) - payback;
                if (outstngcalc < 0) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Repayment date must not be more than outstanding amount");
                    f.addMessage(null, message);
                    return "/secured/humanresource/payemployee?faces-redirect=true&selectedEmp=" + selectedEmp;
                }
                empexpUpd.setOutstanding(String.format("%.2f", outstngcalc));
                //if outstngcalc = 0 then loan is closed with the repayment date  
                if (outstngcalc == 0) {
                    empexpUpd.setEdate(sdf.format(paydate));
                }
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
                // If there is a payback then PAYBACK record is inserted in empexpense
                 //Construction of empexpense record
                EmpExpDTO empexpRec = new EmpExpDTO();
                int empexpid = masterDataService.getMaxEmpExpenseId();
                if (empexpid == 0) {
                    empexpRec.setId("1");
                } else {
                    empexpRec.setId(String.valueOf(empexpid + 1));
                }
                empexpRec.setEmpid(selectedEmp);
                empexpRec.setExpcategory("PAYBACK");
                empexpRec.setTotal(String.format("%.2f", payback));
                empexpRec.setOutstanding(String.format("%.2f", 0.0));
                empexpRec.setSdate(sdf.format(paydate));
                empexpRec.setEdate(null);
                empexpRec.setEmprefid(empexpUpd.getId());
                int paybackadd = masterDataService.addEmpExpRecord(empexpRec);
                if (paybackadd == SUCCESS) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                            "Payback updated successfully");
                    f.addMessage(null, message);
                } else {
                    if (paybackadd == DB_DUPLICATE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "This expexpense record already exist.");
                        f.addMessage(null, message);
                    }
                    if (paybackadd == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Failure on adding expexpense record.");
                        f.addMessage(null, message);
                    }

                }
            }
        
//           
//            if (payback > 0) {                
//               
//            }
        }
        return redirectUrl;

    }
    
    public int createExpenseRec(String empexptype, String expenseAmt) throws NamingException{
        MasterDataServices masterDataService = new MasterDataServices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ExpenseDTO expenseRec = new ExpenseDTO();
        int expenseid = masterDataService.getNextIdForExpense();
        if (expenseid == 0) {
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

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public float getLeaveApplicable() {
        return leaveApplicable;
    }

    public void setLeaveApplicable(float leaveApplicable) {
        this.leaveApplicable = leaveApplicable;
    }
    
}
