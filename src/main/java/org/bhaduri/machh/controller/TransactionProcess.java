/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.model.SelectItem;
import java.util.Date;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.DTO.CropPk;
import org.bhaduri.machh.DTO.UsersDTO;
import org.bhaduri.machh.services.MasterDataServices;
//import org.bhaduri.machh.DTO.ExpenseCategoryDTO;

/**
 *
 * @author sb
 */
@Named(value = "transactionProcess")
@ViewScoped
public class TransactionProcess implements Serializable{
    private Date transdate;
    private String transtype;
    private String cropcategory;
    private List<String> cropcategories;
    private String cropname;
    private List<String> cropnames;
    
    
    @PostConstruct 
    public void init() {
        System.out.println("PostConstruct: Bean initialized");
    }
    public void onTransTypeChange() {
        if ("capx".equals(transtype)) {
            cropcategories = new ArrayList<>();
//            cropnames = new ArrayList<>();
//            cropcategories.add(new SelectItem("", "Select One"));  // Add default option
//            cropnames.add(new SelectItem("", "Select One"));
            MasterDataServices masterDataService = new MasterDataServices();
            List<String> cropCat = masterDataService.getCropCat();
            for (int i = 0; i < cropCat.size(); i++) {
                cropcategories.add(cropCat.get(i));
//                cropnames.add(cropPkDto.get(i).getCropName());
            }
        }
    }
    
    public void onCropCatChange() {
        if ("capx".equals(transtype)) {            
            cropnames = new ArrayList<>();
            MasterDataServices masterDataService = new MasterDataServices();
            List<String> cropnamesforcat = masterDataService.getCropNameForCat(cropcategory);
            for (int i = 0; i < cropnamesforcat.size(); i++) {                
                cropnames.add(cropnamesforcat.get(i));
            }
        }
    }
    public Date getTransdate() {
        return transdate;
    }

    public String getCropcategory() {
        return cropcategory;
    }

    public void setCropcategory(String cropcategory) {
        this.cropcategory = cropcategory;
    }

    public List<String> getCropcategories() {
        return cropcategories;
    }

    public void setCropcategories(List<String> cropcategories) {
        this.cropcategories = cropcategories;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public List<String> getCropnames() {
        return cropnames;
    }

    public void setCropnames(List<String> cropnames) {
        this.cropnames = cropnames;
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
    
    
//    public List<String> loadExpenseCategories() {
//        List<String> types = new ArrayList<>();        
//        MasterDataServices masterDataService = new MasterDataServices();
//        List<ExpenseCategoryDTO> expensecategories = masterDataService.getExpenseCategoryDetails();
//        for (int i = 0; i < expensecategories.size(); i++) {
//            types.add(expensecategories.get(i).getExpenseType());
//        }
//        return types;
//    }
}
