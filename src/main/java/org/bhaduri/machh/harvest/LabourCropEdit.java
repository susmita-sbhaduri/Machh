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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "labourCropEdit")
@ViewScoped
public class LabourCropEdit implements Serializable {

    private String selectedLabcrop;
    private String site;
    private String cropcat;
    private String cropname;
    private float amountpaid;
    private Date applyDt = new Date();
    private String comments;
    private ExpenseDTO expensePrev;
    private LabourCropDTO labcropPrev;
    /**
     * Creates a new instance of LabourCropEdit
     */
    public LabourCropEdit() {
    }
    public void fillValues() throws NamingException, ParseException {
        MasterDataServices masterDataService = new MasterDataServices();
        labcropPrev = masterDataService.getLabCropForId(selectedLabcrop);
        HarvestDTO harvestRecord = masterDataService
                .getHarvestRecForId(labcropPrev.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
       
        // for autofill        
        this.amountpaid = Float.parseFloat(labcropPrev.getAppliedAmount());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        this.applyDt = formatter.parse(labcropPrev.getApplicationDate());
    }

    public String goToSave() throws NamingException {
//        MasterDataServices masterDataService = new MasterDataServices();
        
        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" 
                + labcropPrev.getHarvestId();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
//        float remainingAmt =0;
//        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
//        
//        /*if resource id is changed then both resource id and resource amount is updated in 
//        resourceapply and farmresource(remainingAmt field) table. */
//        if (selectedRes.equals(rescropPrev.getResourceId())) {
//            if (amtapplied == Float.parseFloat(rescropPrev.getAppliedAmount())) {
//                remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt());
//            } else {
//                remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt())
//                        - amtapplied
//                        + Float.parseFloat(rescropPrev.getAppliedAmount());
//            }
//        } else {
//            remainingAmt = Float.parseFloat(amount)
//                    - amtapplied
//                    + Float.parseFloat(rescropRecord.getAppliedAmount());
//        }  
//        
//        if (amtapplied == 0) {
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Apply non-zero amount of resource.",
//                    "Apply non-zero amount of resource.");
//            f.addMessage("amtapplied", message);
//            return redirectUrl;
//        } else {
////            float remainingAmt = Float.parseFloat(amount) - amtapplied +amtappliedprev;
//            if (remainingAmt == 0) {
//                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Strored resource would be finished after this application.",
//                        "Strored resource would be finished after this application.");
//                f.addMessage("amtapplied", message);
//            }
//            if (remainingAmt < 0) {
//                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource cannot be applied.",
//                        "Strored resource is less than applied resource.");
//                f.addMessage("amtapplied", message);
//                return redirectUrl;
//            }
//        }
//        
//        int sqlFlag = 0;
////        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
//        rescropRecord.setResourceId(selectedRes);// for resourcecrop table
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        rescropRecord.setAppliedAmount(String.format("%.2f", amtapplied));
//        rescropRecord.setApplicationDt(sdf.format(applyDt));
//        
//        FarmresourceDTO resourceRec = masterDataService.
//                getResourceNameForId(Integer.parseInt(selectedRes)); // for farmresouce table
//        resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
//        
//        int rescropres = masterDataService.editResCropRecord(rescropRecord);
//        
//        if (rescropres == SUCCESS) {
//            sqlFlag = sqlFlag + 1;
//        } else {
//            if (rescropres == DB_NON_EXISTING) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure" 
//                                                , "Resourcecrop record doesn't exist");
//                f.addMessage(null, message);
//            }
//            if (rescropres == DB_SEVERE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
//                        Integer.toString(DB_SEVERE));
//                f.addMessage(null, message);
//            } 
////            redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//            return redirectUrl;
//        }
//        
//        
//        if (sqlFlag == 1) {
//            int resres = masterDataService.editResource(resourceRec);
//            if (resres == SUCCESS) {
//                sqlFlag = sqlFlag + 1;
//            } else {
//                if (resres == DB_NON_EXISTING) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resource record does not exist", Integer.toString(DB_NON_EXISTING));
//                    f.addMessage(null, message);
//                }
//                if (resres == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resource update", Integer.toString(DB_SEVERE));
//                    f.addMessage(null, message);
//                }
////                resourceRec.setAvailableAmt(amount); //revert the changes in amount for farmresouce table
//                resres = masterDataService.editResCropRecord(rescropPrev);
////                
////                rescropRecord.setAppliedAmount(String.format("%.2f", amtapplied));
////                rescropRecord.setApplicationDt(sdf.format(applyDt));
//                
//                if (resres == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resourcecrop table update", 
//                            Integer.toString(DB_SEVERE));
//                    f.addMessage(null, message);
//                }
////                redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//                return redirectUrl;
//            }
//        }
//        if (sqlFlag == 2) {
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                    "Resource updated successfully with application ID=" + rescropRecord.getApplicationId());
//            f.addMessage(null, message);
//        }
        return redirectUrl;        
    }
    
    public String goToAppliedLab() {
        String redirectUrl = "/secured/harvest/appliedlabperharvest?faces-redirect=true&appliedHarvest=" 
                + labcropPrev.getHarvestId();
        return redirectUrl; 
    }
    public String getSelectedLabcrop() {
        return selectedLabcrop;
    }

    public void setSelectedLabcrop(String selectedLabcrop) {
        this.selectedLabcrop = selectedLabcrop;
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

    public float getAmountpaid() {
        return amountpaid;
    }

    public void setAmountpaid(float amountpaid) {
        this.amountpaid = amountpaid;
    }

    

    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ExpenseDTO getExpensePrev() {
        return expensePrev;
    }

    public void setExpensePrev(ExpenseDTO expensePrev) {
        this.expensePrev = expensePrev;
    }

    public LabourCropDTO getLabcropPrev() {
        return labcropPrev;
    }

    public void setLabcropPrev(LabourCropDTO labcropPrev) {
        this.labcropPrev = labcropPrev;
    }
    
}
