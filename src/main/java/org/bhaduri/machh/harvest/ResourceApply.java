/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.CropDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.ShopDTO;
import org.bhaduri.machh.DTO.SiteDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "resourceApply")
@ViewScoped
public class ResourceApply implements Serializable {
    private String selectedRes;
    private String selectedHarvest;
    private String site;
    private String cropcat;
    private String cropname;
    private List<FarmresourceDTO> existingresources;
    private String amount;
    private String unit;
    private float amtapplied;
    private Date applyDt = new Date();
    /**
     * Creates a new instance of ResourceApply
     */
    public ResourceApply() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(selectedHarvest);
//        SiteDTO siteRecord = masterDataService.getSiteNameForId(harvestRecord.getSiteName());
//        CropDTO cropRecord = masterDataService.getCropPerPk(harvestRecord.getCropName());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        existingresources = masterDataService.getResourceList();
    }
    
    public void onResSelect() throws NamingException {        
        MasterDataServices masterDataService = new MasterDataServices();
        amount = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getAvailableAmt();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
    }
    
    public void goToSubmitRes() {
        System.out.println("No crop categories are found." + selectedRes);
        
    }
    
    public String goToApplyRes() {
        String redirectUrl = "/secured/harvest/resourceapply?faces-redirect=true&selectedHarvest=" + selectedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        if (selectedRes == null || selectedRes.trim().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select one resource.",
                    "Select one resource.");
            f.addMessage("resid", message);
            return redirectUrl;
        }
        if (amtapplied == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Apply non-zero amount of resource.",
                    "Apply non-zero amount of resource.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        } else {
            float remainingAmt = Float.parseFloat(amount) - amtapplied;
            if (remainingAmt == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Strored resource would be finished after this application.",
                        "Strored resource would be finished after this application.");
                f.addMessage("amount", message);
                return null;
            }
            if (remainingAmt < 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource cannot be applied.",
                        "Strored resource is less than applied resource.");
                f.addMessage("amount", message);
                return redirectUrl;
            }
        }
        
        
    }
    
    public String goToApplyResAgain(){
        String redirectUrl = "/secured/harvest/resourceapply?faces-redirect=true&selectedHarvest=" + selectedHarvest;
        return redirectUrl;
    }
    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
    }

    public String getSelectedHarvest() {
        return selectedHarvest;
    }

    public void setSelectedHarvest(String selectedHarvest) {
        this.selectedHarvest = selectedHarvest;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCropcat() {
        return cropcat;
    }

    public void setCropcat(String cropcat) {
        this.cropcat = cropcat;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public List<FarmresourceDTO> getExistingresources() {
        return existingresources;
    }

    public void setExistingresources(List<FarmresourceDTO> existingresources) {
        this.existingresources = existingresources;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getAmtapplied() {
        return amtapplied;
    }

    public void setAmtapplied(float amtapplied) {
        this.amtapplied = amtapplied;
    }

    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }
    
        
}
