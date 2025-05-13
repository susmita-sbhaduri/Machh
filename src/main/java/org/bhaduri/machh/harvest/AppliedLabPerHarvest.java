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
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "appliedLabPerHarvest")
@ViewScoped
public class AppliedLabPerHarvest implements Serializable {
    private List<LabourCropDTO> appliedlabours;
    private String appliedHarvest;   
    private String sitename;
    private String cropcat;
    private String cropname;
    private LabourCropDTO appliedLabour;
    /**
     * Creates a new instance of AppliedLabPerHarvest
     */
    public AppliedLabPerHarvest() {
    }
    public String fillResourceValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(appliedHarvest);
        sitename = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        
        appliedlabours = masterDataService.getLabCropForHarvest(appliedHarvest);        
        String redirectUrl = "/secured/harvest/laborapply?faces-redirect=true&selectedHarvest=" + appliedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        
        if(appliedlabours==null||appliedlabours.size()==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "No labor is applied for this harvest.");
            f.addMessage(null, message);
            return redirectUrl;
        } else 
            return null;        
    }
    
    public String editLabour() {
//        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" + selectedHarvest.getHarvestid();
        String redirectUrl = "/secured/userhome?faces-redirect=true";
        return redirectUrl;
    }
    
    public String deleteLabour() {
//        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" + selectedHarvest.getHarvestid();
        String redirectUrl = "/secured/userhome?faces-redirect=true";
        return redirectUrl;
    } 
    
    public List<LabourCropDTO> getAppliedlabours() {
        return appliedlabours;
    }
    
   
    public void setAppliedlabours(List<LabourCropDTO> appliedlabours) {
        this.appliedlabours = appliedlabours;
    }

    public String getAppliedHarvest() {
        return appliedHarvest;
    }

    public void setAppliedHarvest(String appliedHarvest) {
        this.appliedHarvest = appliedHarvest;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
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

    public LabourCropDTO getAppliedLabour() {
        return appliedLabour;
    }

    public void setAppliedLabour(LabourCropDTO appliedLabour) {
        this.appliedLabour = appliedLabour;
    }
    
}
