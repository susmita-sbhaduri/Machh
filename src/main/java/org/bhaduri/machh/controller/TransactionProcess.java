/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.controller;

import jakarta.annotation.PostConstruct;
import java.util.Date;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.DTO.UsersDTO;
import org.bhaduri.machh.services.MasterDataServices;
import org.bhaduri.machh.DTO.ExpenseCategoryDTO;

/**
 *
 * @author sb
 */
@Named(value = "transactionProcess")
@ViewScoped
public class TransactionProcess implements Serializable{
    private Date transdate;
    private String transtype;
    private String expenseCategory;
    private List<String> expenseCategories;
    
     @PostConstruct
    public void init() {
        expenseCategories = loadExpenseCategories();
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public List<String> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(List<String> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }
    
    public Date getTransdate() {
        return transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }
    
    public void process () {
//        userDTO = new UsersDTO();
//        MasterDataServices masterDataService = new MasterDataServices();
//        userDTO = masterDataService.getUserAuthDetails(username, password);
//        if(userDTO.getID().equals("null")){
//            return "landing?faces-redirect=true";
//        } else return "/secured/empty?faces-redirect=true";
//        if (transdate.equals("susmita")) {
//            if (transtype.equals("bumbu123")) {
////                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
//                return "/secured/empty?faces-redirect=true";
//            }
//        }
//        return "landing?faces-redirect=true";
          System.out.println("Selected Date: " + transdate);
          System.out.println("Name: " + transtype);
    }
    public List<String> loadExpenseCategories() {
        List<String> types = new ArrayList<>();        
        MasterDataServices masterDataService = new MasterDataServices();
        List<ExpenseCategoryDTO> expensecategories = masterDataService.getExpenseCategoryDetails();
        for (int i = 0; i < expensecategories.size(); i++) {
            types.add(expensecategories.get(i).getExpenseType());
        }
        return types;
    }
}
