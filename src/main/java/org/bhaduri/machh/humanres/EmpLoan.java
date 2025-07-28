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
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "empLoan")
@ViewScoped
public class EmpLoan implements Serializable {
    private String selectedEmp;
    private String selectedEmpName;
    private float totalLoan;
    private Date sdate = new Date();
    private String outstanding;
    private float payback;
    private EmpExpDTO empexpUpd;
    private boolean newRecord = false;
    private boolean readOnlyCondition;
    /**
     * Creates a new instance of EmpLoan
     */
    public EmpLoan() {
    }
    public void fillValues() throws NamingException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MasterDataServices masterDataService = new MasterDataServices(); 
        EmployeeDTO empRec = masterDataService.getEmpNameForId(selectedEmp);
        selectedEmpName = empRec.getName();
        List<EmpExpDTO> empLoanRecs = masterDataService.getEmpActiveExpRecs(selectedEmp, "LOAN");
        if(empLoanRecs.isEmpty()){
            totalLoan = 0;
            outstanding = String.format("%.2f", totalLoan);
            newRecord = true;
            readOnlyCondition = false;
        } else {
//          at one time only one loan will be active hence 0th record is taken out
//          This is an existing loan. hence all the records are populated and made read only
            totalLoan = Float.parseFloat(empLoanRecs.get(0).getTotal());
            outstanding = empLoanRecs.get(0).getOutstanding();
            sdate =  sdf.parse(empLoanRecs.get(0).getSdate());
            readOnlyCondition = true;
        }
    }
    public void calculateOutstanding() throws NamingException {
        outstanding = String.format("%.2f", totalLoan);
    }
    
    public String saveDetails() throws NamingException {
        int sqlFlag = 0;
        String redirectUrl = "/secured/humanresource/maintainemp?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (newRecord && (Float.parseFloat(outstanding) == totalLoan)
                && (totalLoan > 0)) {
//              Construction of expense record, as newRecord is true 2 records would be inserted in 
//              two tables.            
            ExpenseDTO expenseRec = new ExpenseDTO();
            int expenseid = masterDataService.getNextIdForExpense();
            if (expenseid == 0 || expenseid == DB_SEVERE) {
                expenseRec.setExpenseId("1");
            } else {
                expenseRec.setExpenseId(String.valueOf(expenseid + 1));
            }
            expenseRec.setDate(sdf.format(sdate));
            expenseRec.setExpenseRefId(selectedEmp); //######empid as ref id
            expenseRec.setExpenseType("LOAN");
            expenseRec.setExpenditure(String.format("%.2f", totalLoan));
            expenseRec.setCommString("LOAN");
            
            //Construction of empexpense record
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
            empexpRec.setSdate(sdf.format(sdate));
            empexpRec.setEmpid(selectedEmp); //######empid as ref id   
//            empexpRec.setEmprefid(null);
            
            int expres = masterDataService.addExpenseRecord(expenseRec);
            if (expres == SUCCESS) {
                sqlFlag = sqlFlag+1;
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
            if (sqlFlag == 1) {
                int insempexp = masterDataService.addEmpExpRecord(empexpRec);
                if (insempexp == SUCCESS) {                    
                    sqlFlag = sqlFlag + 1;
                } else {
                    if (insempexp == DB_DUPLICATE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Duplicate record error for empexpense table");
                        f.addMessage(null, message);

                    }
                    if (insempexp == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Failure on insert in empexpense table");
                        f.addMessage(null, message);
                    }
                }
            }
            if (sqlFlag == 2) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Loan added successfully");
                f.addMessage(null, message);
                
            }
        }//if newRecord
//        if (newRecord == false) {
//            empexpUpd = masterDataService.getEmpActiveExpRecs(selectedEmp,"LOAN").get(0);
//            empexpUpd.setOutstanding(outstanding);
//            empexpUpd.setSdate(sdf.format(sdate));
////            if (edate != null) {
////                empexpUpd.setEdate(sdf.format(sdate));
////            } else {
////                empexpUpd.setEdate(null);
////            }
//            int empexpupd = masterDataService.editEmpExpRecord(empexpUpd);
//            if (empexpupd == SUCCESS) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                        "Loan updated successfully");
//                f.addMessage(null, message);
//            } else {
//                if (empexpupd == DB_NON_EXISTING) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                            "This expexpense record does not exist.");
//                    f.addMessage(null, message);
//                }
//                if (empexpupd == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                            "Failure on updating expexpense record.");
//                    f.addMessage(null, message);
//                }
//                
//            }
//        }//if not newRecord
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


    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public boolean isNewRecord() {
        return newRecord;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    public EmpExpDTO getEmpexpUpd() {
        return empexpUpd;
    }

    public void setEmpexpUpd(EmpExpDTO empexpUpd) {
        this.empexpUpd = empexpUpd;
    }

    public boolean isReadOnlyCondition() {
        return readOnlyCondition;
    }

    public void setReadOnlyCondition(boolean readOnlyCondition) {
        this.readOnlyCondition = readOnlyCondition;
    }
    
}
