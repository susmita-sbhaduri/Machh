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
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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

         String redirectUrl = "/secured/harvest/labourcropedit?faces-redirect=true&selectedLabcrop=" 
                 + appliedLabour.getApplicationId();
//        String redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
        return redirectUrl;
    }
    
    public String deleteLabour() throws NamingException {
//        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" + selectedHarvest.getHarvestid();
//        String redirectUrl = "/secured/userhome?faces-redirect=true";
        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" + appliedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        ResourceCropDTO resourceCrop = new ResourceCropDTO();
//        resourceCrop.setApplicationId(appliedRes.getApplicationId());
//        int delres = masterDataService.delResCropRecord(resourceCrop);
//        if (delres == SUCCESS){            
//            FarmresourceDTO resourceRecord = masterDataService.
//                    getResourceNameForId(Integer.parseInt(appliedRes.getResourceId()));
//            float farmResourceAmt = Float.parseFloat(resourceRecord.getAvailableAmt())
//                    +Float.parseFloat(appliedRes.getAppliedAmount());
//            resourceRecord.setAvailableAmt(String.format("%.2f",farmResourceAmt));
//            int updRes = masterDataService.editResource(resourceRecord);
//            if (updRes == SUCCESS) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                        "resourcecrop record deleted successfully");
//                f.addMessage(null, message);
//            }
//            if (updRes == DB_SEVERE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                        "resourcecrop record could not be updated");
//                f.addMessage(null, message);
//            }
//        }
//        if (delres == DB_SEVERE) {
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
//                    "resourcecrop record could not be deleted");
//            f.addMessage(null, message);
//        }
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
