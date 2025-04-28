/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.resource;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResAcquireDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "acquireResource")
@ViewScoped
public class AcquireResource implements Serializable {

    private boolean saveDisabled = true;
    private String selectedRes;
    private String selectedShop;
    private String rate;
    private String unit;
    private List<ShopResDTO> shopForSelectedRes;
    private List<FarmresourceDTO> existingresources;
    private ShopResDTO selectedShopRes;
    private float amount;
    private Date purchaseDt = new Date();

    public AcquireResource() {
    }

    public void fillResourceValues() throws NamingException {
//        String redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        MasterDataServices masterDataService = new MasterDataServices();
//        existingresources = masterDataService.getResourceList();
//        if (existingresources.isEmpty()) {
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Before acquiring resource should be added.",
//                    "Before acquiring resource should be added.");
//            f.addMessage("resid", message);
//            return redirectUrl;
//        } else {
//            return null;
//        }
        MasterDataServices masterDataService = new MasterDataServices();
        shopForSelectedRes = masterDataService.getShopResForResid(selectedRes);
    }

    public void onResourceIdselect() throws NamingException {
//         FarmresourceDTO temp = selectedRes;
        System.out.println("No crop categories are found." + selectedRes);
        MasterDataServices masterDataService = new MasterDataServices();
        shopForSelectedRes = masterDataService.getShopResForResid(selectedRes);

    }

    public void onShopResSelect() throws NamingException {
        System.out.println("No crop categories are found." + selectedShop);
        MasterDataServices masterDataService = new MasterDataServices();
        selectedShopRes = masterDataService.getResShopForPk(selectedRes, selectedShop);
        rate = selectedShopRes.getRate();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
    }

    public String goToReviewRes() {
//        String redirectUrl = "/secured/resource/acquireresource?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();        
//        f.getExternalContext().getFlash().setKeepMessages(true);
        if (selectedRes == null || selectedRes.trim().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select one resource.",
                    "Select one resource.");
            f.addMessage("resid", message);
            return null;
        }

        if (selectedShop == null || selectedShop.trim().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select one shop.",
                    "Select one shop.");
            f.addMessage("shopid", message);
            return null;
        }

        if (amount == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Provide non-zero purchase amount.",
                    "Provide non-zero purchase amount.");
            f.addMessage("amount", message);
            return null;
        }
        float calculatedAmount = Float.parseFloat(rate)*amount;
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Total cost for "+selectedShopRes.getResourceName()
                +" for shop "+selectedShopRes.getShopName(),
                    "=Rs."+String.format("%.2f", calculatedAmount));
        
        f.addMessage(null, message);
        saveDisabled = false; // Enable the save button
        return null;
    }

    public String goToSaveRes() throws NamingException {
         
        String redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        
        MasterDataServices masterDataService = new MasterDataServices();
        ResAcquireDTO resAcquireRec = new ResAcquireDTO();
        float calculatedAmount = Float.parseFloat(rate)*amount;
        int acquireid = masterDataService.getNextIdForResAquire();
        if(acquireid==0 || acquireid == DB_SEVERE){
            resAcquireRec.setAcquireId("1");
        }
        else{
            resAcquireRec.setAcquireId(String.valueOf(acquireid+1));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        resAcquireRec.setResoureId(selectedShopRes.getResourceId());
        resAcquireRec.setAmount(String.format("%.2f", calculatedAmount));
        resAcquireRec.setAcquireDate(sdf.format(purchaseDt));
        int acqres = masterDataService.addAcquireResource(resAcquireRec);
        
        ExpenseDTO expenseRec = new ExpenseDTO();
        int expenseid = masterDataService.getNextIdForExpense();
        if(expenseid==0 || expenseid == DB_SEVERE){
            expenseRec.setExpenseId("1");
        }
        else{
            expenseRec.setExpenseId(String.valueOf(expenseid+1));
        }
        expenseRec.setDate(sdf.format(purchaseDt));
        expenseRec.setExpenseType("RES");
        expenseRec.setExpenseRefId(selectedShopRes.getResourceId());
        expenseRec.setExpenditure(String.format("%.2f", calculatedAmount));
        expenseRec.setCommString("");
        int expres = masterDataService.addExpenseRecord(expenseRec);
        
        FarmresourceDTO resourceRec = new FarmresourceDTO();
        resourceRec = masterDataService.getResourceNameForId(Integer.parseInt(
                selectedShopRes.getResourceId()));
        calculatedAmount = calculatedAmount+Float.parseFloat(resourceRec.getAvailableAmt());
        resourceRec.setAvailableAmt(String.format("%.2f", calculatedAmount));
        int resres = masterDataService.editResource(resourceRec);
        
        if (acqres == SUCCESS && expres == SUCCESS && resres == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inventory added", Integer.toString(SUCCESS));
            f.addMessage(null, message);
        } else {
            if (acqres == DB_DUPLICATE || expres == DB_DUPLICATE || resres == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate record Or record doesn't exist for update", Integer.toString(DB_DUPLICATE));
                f.addMessage(null, message);
                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true";
                return redirectUrl;
            }
            if (acqres == DB_SEVERE || expres == DB_SEVERE || resres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
                f.addMessage(null, message);
                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true";
                return redirectUrl;
            }
        }
        return redirectUrl;
    }

    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
    }

    public String getSelectedShop() {
        return selectedShop;
    }

    public void setSelectedShop(String selectedShop) {
        this.selectedShop = selectedShop;
    }

    public List<ShopResDTO> getShopForSelectedRes() {
        return shopForSelectedRes;
    }

    public void setShopForSelectedRes(List<ShopResDTO> shopForSelectedRes) {
        this.shopForSelectedRes = shopForSelectedRes;
    }

    public List<FarmresourceDTO> getExistingresources() {
        return existingresources;
    }

    public void setExistingresources(List<FarmresourceDTO> existingresources) {
        this.existingresources = existingresources;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ShopResDTO getSelectedShopRes() {
        return selectedShopRes;
    }

    public void setSelectedShopRes(ShopResDTO selectedShopRes) {
        this.selectedShopRes = selectedShopRes;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPurchaseDt() {
        return purchaseDt;
    }

    public void setPurchaseDt(Date purchaseDt) {
        this.purchaseDt = purchaseDt;
    }

    public boolean isSaveDisabled() {
        return saveDisabled;
    }

    public void setSaveDisabled(boolean saveDisabled) {
        this.saveDisabled = saveDisabled;
    }

    
}
