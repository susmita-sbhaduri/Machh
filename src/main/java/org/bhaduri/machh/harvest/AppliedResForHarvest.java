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
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "appliedResForHarvest")
@ViewScoped
public class AppliedResForHarvest implements Serializable {
    private List<ResourceCropDTO> appliedresources;
    private String appliedHarvest;   
    private String sitename;
    private String cropcat;
    private String cropname;
    private ResourceCropDTO appliedRes;
    /**
     * Creates a new instance of AppliedResForHarvest
     */
    public AppliedResForHarvest() {
    }
    
    public String fillResourceValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(appliedHarvest);
        sitename = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        
        appliedresources = masterDataService.getResCropForHarvest(appliedHarvest);        
        String redirectUrl = "/secured/harvest/resourceapply?faces-redirect=true&selectedHarvest=" + appliedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        
        if(appliedresources==null){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "No resource is applied for this harvest.");
            f.addMessage(null, message);
            return redirectUrl;
        } else 
            return null;        
    }
    
    public String editResource(){
        String redirectUrl = "/secured/harvest/resourcecropedit?faces-redirect=true&selectedRescrop=" + 
                appliedRes.getApplicationId();
//        String redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
        return redirectUrl;
    }
    
    public String deleteResource() throws NamingException{
        String redirectUrl = "/secured/harvest/appliedresperharvest?faces-redirect=true&appliedHarvest=" + appliedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        ResourceCropDTO resourceCrop = new ResourceCropDTO();
        resourceCrop.setApplicationId(appliedRes.getApplicationId());
        int delres = masterDataService.delResCropRecord(resourceCrop);
        if (delres == SUCCESS){
            
        }
        if (delres == DB_SEVERE) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                    "resourcecrop record could not be deleted");
            f.addMessage(null, message);
        }
                
//        String redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
        return redirectUrl;
    }
    public List<ResourceCropDTO> getAppliedresources() {
        return appliedresources;
    }

    public void setAppliedresources(List<ResourceCropDTO> appliedresources) {
        this.appliedresources = appliedresources;
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

    public ResourceCropDTO getAppliedRes() {
        return appliedRes;
    }

    public void setAppliedRes(ResourceCropDTO appliedRes) {
        this.appliedRes = appliedRes;
    }
    
}
