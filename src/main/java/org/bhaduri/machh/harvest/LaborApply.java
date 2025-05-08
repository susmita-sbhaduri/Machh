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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResAcquireDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "laborApply")
@ViewScoped
public class LaborApply implements Serializable {

    private String selectedHarvest;
    private String site;
    private String cropcat;
    private String cropname;
    private String labCategory;
    private float amountPaid;
    private Date applyDt = new Date();
    
    public LaborApply() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(selectedHarvest);
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
    }
    
    public String goToSaveLabor() throws NamingException {
        int sqlFlag = 0;
        String redirectUrl = "/secured/harvest/laborapply?faces-redirect=true&selectedHarvest=" + selectedHarvest;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);

        MasterDataServices masterDataService = new MasterDataServices();
        
//        contruction of labouracquire record
        LabourCropDTO labCropRec = new LabourCropDTO();        
        int applicationid = masterDataService.getMaxIdForLabCrop();
        if (applicationid == 0 || applicationid == DB_SEVERE) {
            labCropRec.setApplicationId("1");
        } else {
            labCropRec.setApplicationId(String.valueOf(applicationid + 1));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        labCropRec.setHarvestId(selectedHarvest);
        labCropRec.setLabourCategory(labCategory);
        labCropRec.setApplicationDate(sdf.format(applyDt));   
//        contruction of expense record
        ExpenseDTO expenseRec = new ExpenseDTO();
        int expenseid = masterDataService.getNextIdForExpense();
        if (expenseid == 0 || expenseid == DB_SEVERE) {
            expenseRec.setExpenseId("1");
        } else {
            expenseRec.setExpenseId(String.valueOf(expenseid + 1));
        }
        expenseRec.setDate(sdf.format(applyDt));
        expenseRec.setExpenseType(labCategory);
        expenseRec.setExpenseRefId(selectedHarvest);
        expenseRec.setExpenditure(String.format("%.2f", amountPaid));
        expenseRec.setCommString("");


        int acqres = masterDataService.addAcquireResource(resAcquireRec);
        if (acqres == SUCCESS) {
            sqlFlag = sqlFlag + 1;
        } else {
            if (acqres == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate record error for resourceacquire table", Integer.toString(DB_DUPLICATE));
                f.addMessage(null, message);
            }
            if (acqres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on insert in resourceacquire table", Integer.toString(DB_SEVERE));
                f.addMessage(null, message);
            }

            redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
                        + selectedShopRes.getResourceId();
            return redirectUrl;
        }

        if (sqlFlag == 1) {

            int expres = masterDataService.addExpenseRecord(expenseRec);
            if (expres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (expres == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate record error for expense table", Integer.toString(DB_DUPLICATE));
                    f.addMessage(null, message);
                }
                if (expres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on insert in expense table", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                int delacq = masterDataService.delAcquireResource(resAcquireRec);
                if (delacq == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "acquireresource record could not be deleted", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
                        + selectedShopRes.getResourceId();
                return redirectUrl;
            }
        }

        if (sqlFlag == 2) {

            int resres = masterDataService.editResource(resourceRec);
            if (resres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (resres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "This resource record does not exist.", Integer.toString(DB_NON_EXISTING));
                    f.addMessage(null, message);
                }
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on edit in resource table", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                
                int delacq = masterDataService.delAcquireResource(resAcquireRec);
                if (delacq == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "acquireresource record could not be deleted", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                int delexpense = masterDataService.delExpenseRecord(expenseRec);
                if (delexpense == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "expense record could not be deleted", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
                        + selectedShopRes.getResourceId();
                return redirectUrl;
            }
        }
                
        if (sqlFlag==3) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Resource acquired successfully");
            f.addMessage(null, message);
        }
        return redirectUrl;
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

    public String getLabCategory() {
        return labCategory;
    }

    public void setLabCategory(String labCategory) {
        this.labCategory = labCategory;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }

    
    
}
