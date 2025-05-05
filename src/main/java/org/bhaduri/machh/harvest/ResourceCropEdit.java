/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;


import jakarta.inject.Named;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "resourceCropEdit")
@ViewScoped
public class ResourceCropEdit implements Serializable {

    private String selectedRes;
    private String selectedRescrop;
    private String site;
    private String cropcat;
    private String cropname;
    private List<FarmresourceDTO> existingresources;
    private String amount;
    private String unit;
    private float amtapplied;
    private Date applyDt;

    public ResourceCropEdit() {
    }
    
    public void fillValues() throws NamingException, ParseException {
        MasterDataServices masterDataService = new MasterDataServices();
        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
        HarvestDTO harvestRecord = masterDataService
                .getHarvestRecForId(rescropRecord.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        existingresources = masterDataService.getResourceList();
        
       
        this.selectedRes = rescropRecord.getResourceId();
        FarmresourceDTO selResDetails = masterDataService.
                getResourceNameForId(Integer.parseInt(rescropRecord.getResourceId()));
        this.amount = selResDetails.getAvailableAmt();
        this.unit = selResDetails.getUnit();
        this.amtapplied = Float.parseFloat(rescropRecord.getAppliedAmount());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        this.applyDt = formatter.parse(rescropRecord.getApplicationDt());
    }

    public void onResSelect() throws NamingException {        
        MasterDataServices masterDataService = new MasterDataServices();
        amount = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes))
                .getAvailableAmt();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes))
                .getUnit();
    }
    
    public String goToApplyRes() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
        String redirectUrl = "/secured/harvest/appliedresperharvest?faces-redirect=true&appliedHarvest=" + rescropRecord.getHarvestId();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        
        if (amtapplied == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Apply non-zero amount of resource.",
                    "Apply non-zero amount of resource.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
//            return null;
        } else {
            float remainingAmt = Float.parseFloat(amount) - amtapplied;
            if (remainingAmt == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Strored resource would be finished after this application.",
                        "Strored resource would be finished after this application.");
                f.addMessage("amtapplied", message);
//                return null;
            }
            if (remainingAmt < 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource cannot be applied.",
                        "Strored resource is less than applied resource.");
                f.addMessage("amtapplied", message);
                return redirectUrl;
//                return null;
            }
        }
        int sqlFlag = 0;
//        ResourceCropDTO resourceCrop = new ResourceCropDTO();
//        
//        int applicationid = masterDataService.getMaxIdForResCrop();
//        if (applicationid == 0 || applicationid == DB_SEVERE) {
//            resourceCrop.setApplicationId("1");
//        } else {
//            resourceCrop.setApplicationId(String.valueOf(applicationid + 1));
//        }
        rescropRecord.setResourceId(selectedRes);
//        rescropRecord.setHarvestId(selectedHarvest);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        rescropRecord.setAppliedAmount(String.format("%.2f", amtapplied));
        rescropRecord.setApplicationDt(sdf.format(applyDt));
        
        FarmresourceDTO resourceRec = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes));
        float remainingAmt = Float.parseFloat(amount) - amtapplied;
        resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
        
        int rescropres = masterDataService.editResCropRecord(rescropRecord);
        
        if (rescropres == SUCCESS) {
            sqlFlag = sqlFlag + 1;
        } else {
            if (rescropres == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure" 
                                                , "Resourcecrop record doesn't exist");
                f.addMessage(null, message);
            }
            if (rescropres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        Integer.toString(DB_SEVERE));
                f.addMessage(null, message);
            } 
            redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
            return redirectUrl;
        }
        
        
        if (sqlFlag == 1) {
            int resres = masterDataService.editResource(resourceRec);
            if (resres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (resres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resource record does not exist", Integer.toString(DB_NON_EXISTING));
                    f.addMessage(null, message);
                }
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resource update", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                int delres = masterDataService.delResCropRecord(rescropRecord);
                if (delres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "resourcecrop record could not be deleted", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
                return redirectUrl;
            }
        }
        if (sqlFlag == 2) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Resource applied successfully with application ID=" + rescropRecord.getApplicationId());
            f.addMessage(null, message);
        }
        return redirectUrl;
        
    }
    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
    }

    public String getSelectedRescrop() {
        return selectedRescrop;
    }

    public void setSelectedRescrop(String selectedRescrop) {
        this.selectedRescrop = selectedRescrop;
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
