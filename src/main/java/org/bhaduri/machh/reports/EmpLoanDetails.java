/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.reports;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.EmpExpDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "empLoanDetails")
@ViewScoped
public class EmpLoanDetails implements Serializable {
    List<EmpExpDTO> loanrecords = new ArrayList<>();
    /**
     * Creates a new instance of EmpLoanDetails
     */
    public EmpLoanDetails() {
    }
    public void fillValues() throws NamingException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MasterDataServices masterDataService = new MasterDataServices(); 
        List<EmpExpDTO> loanList = masterDataService.getEmpActiveLoans();
        EmpExpDTO record = new EmpExpDTO();
        for (int i = 0; i < loanList.size(); i++) {
           String empName = masterDataService.getEmpNameForId(loanList.get(i).getEmpid()).getName();
           record.setEmpid(empName);
           record.setId("LOAN");
           record.setTotal(loanList.get(i).getTotal());
           record.setOutstanding(loanList.get(i).getOutstanding());
           record.setSdate(loanList.get(i).getSdate());
           loanrecords.add(record);
           record = new EmpExpDTO();
           List<EmpExpDTO> paybackList = masterDataService.getPaybackList(loanList.get(i).getEmpid()
                   , loanList.get(i).getId());
           for (int j = 0; j < paybackList.size(); j++) {
               record.setEmpid("");
               record.setId("REPAYMENT");
               record.setTotal(paybackList.get(j).getTotal());
               record.setOutstanding("");
               record.setSdate(paybackList.get(j).getSdate());
               loanrecords.add(record);
               record = new EmpExpDTO();
           }
        }
        
    }

    public List<EmpExpDTO> getLoanrecords() {
        return loanrecords;
    }

    public void setLoanrecords(List<EmpExpDTO> loanrecords) {
        this.loanrecords = loanrecords;
    }
    
}
