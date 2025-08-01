/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.DTO.TaskPlanDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "taskApply")
@ViewScoped
public class TaskApply implements Serializable {
    private String selectedTask;
    private String selectedHarvest;
    private String taskName;
    private String taskType;
    private String site;
    private String cropcat;
    private String cropname;
    private String resname;
    private String amount;
    private String unit;
    private String amtapplied;
    private String appDt;
    private String rescat;
    private String cropwt;
    private String cropwtunit;
    private String appliedcost;
    private String comments;
    private float resCropAppliedCost=0;
    /**
     * Creates a new instance of TaskApply
     */
    public TaskApply() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        TaskPlanDTO taskplanRec = masterDataService.getTaskPlanForId(selectedTask);
        
        taskName = taskplanRec.getTaskName();
        
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(taskplanRec.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        if (taskplanRec.getTaskType().equals("RES")) {
            taskType = "Resource";
            FarmresourceDTO resourceRec = masterDataService
                    .getResourceNameForId(Integer.parseInt(taskplanRec.getResourceId()));
            resname = resourceRec.getResourceName();
            amount = resourceRec.getAvailableAmt();
            unit = resourceRec.getUnit();
            amtapplied = taskplanRec.getAppliedAmount();
            appliedcost = "";
            comments = "";
//            appliedcost = "NA";
//            comments = "NA";
            if (resourceRec.getCropwtunit() != null) {
                rescat = "Crop";
                cropwt = resourceRec.getCropweight();
                cropwtunit = resourceRec.getCropwtunit();
            } else {
                rescat = "Other";
                cropwt = "";
                cropwtunit = "";
            }
        }
        if (taskplanRec.getTaskType().equals("LABHRVST")) {
            taskType = "Labour(to be paid)";
            appliedcost = taskplanRec.getAppliedAmtCost();
            comments = taskplanRec.getComments();
            resname = "";
            amount = "";
            unit = "Rs.";
            amtapplied = "";
            rescat = "";
            cropwt = "";
            cropwtunit = "";
            
//            resname = "NA";
//            amount = "NA";
//            unit = "Rs.";
//            amtapplied = "NA";
//            rescat = "NA";
//            cropwt = "NA";
//            cropwtunit = "NA";
        }
        if (taskplanRec.getTaskType().equals("LAB")) {
            taskType = "Labour";
            appliedcost = "";
            comments = taskplanRec.getComments();
            resname = "";
            amount = "";
            unit = "";
            amtapplied = "";
            rescat = "";
            cropwt = "";
            cropwtunit = "";
            
//            appliedcost = "NA";
//            comments = taskplanRec.getComments();
//            resname = "NA";
//            amount = "NA";
//            unit = "NA";
//            amtapplied = "NA";
//            rescat = "NA";
//            cropwt = "NA";
//            cropwtunit = "NA";
        }
        appDt = taskplanRec.getTaskDt();
    }
    
    public String saveTask() throws NamingException{
        String redirectUrl = "/secured/taskplan/openschedule?faces-redirect=true";
        
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        
        int sqlFlag = 0;
        MasterDataServices masterDataService = new MasterDataServices();
        //Fetching taskplan record
        TaskPlanDTO taskplanRec = masterDataService.getTaskPlanForId(selectedTask);
        
        if (taskplanRec.getTaskType().equals("RES")) {
            float remainingAmt = Float.parseFloat(amount) - Float.parseFloat(amtapplied);
            if (remainingAmt == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                        "Stored resource would be finished after this application.");
                f.addMessage(null, message);
//                return "/secured/taskplan/taskapply?faces-redirect=true&selectedTask=" + selectedTask;
            }
            if (remainingAmt < 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure.",
                        "Stored resource is less than applied resource.");
                f.addMessage(null, message);
                return "/secured/taskplan/taskedit?faces-redirect=true&selectedTask=" + selectedTask;
            }
        
            //resourcecrop record construction
            ResourceCropDTO resourceCrop = new ResourceCropDTO();
            int applicationid = masterDataService.getMaxIdForResCrop();
            if (applicationid == 0 || applicationid == DB_SEVERE) {
                resourceCrop.setApplicationId("1");
            } else {
                resourceCrop.setApplicationId(String.valueOf(applicationid + 1));
            }
            resourceCrop.setHarvestId(taskplanRec.getHarvestId());
            resourceCrop.setResourceId(taskplanRec.getResourceId());
            resourceCrop.setAppliedAmount(taskplanRec.getAppliedAmount());
            
            //shopresource record construction and update
            String shopResupdate = calcShopResAmt(taskplanRec.getAppliedAmount(),
                    resourceCrop.getApplicationId(), taskplanRec.getResourceId());
            if (shopResupdate.equals("OK")) {
                resourceCrop.setAppliedAmtCost((String.format("%.2f", resCropAppliedCost)));
            } else {
                resourceCrop.setAppliedAmtCost(null);
            }
            resourceCrop.setApplicationDt(taskplanRec.getTaskDt());
            
            //farmresource record construction
            FarmresourceDTO resourceRec = masterDataService
                    .getResourceNameForId(Integer.parseInt(taskplanRec.getResourceId()));
            resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
            
            //taskplan record construction for update
            taskplanRec.setAppliedFlag("Y");
            
            //Addition and update started
            int rescropres = masterDataService.addResCropRecord(resourceCrop);
            
            if (rescropres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (rescropres == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                             "Resource already applied with this application ID=" + resourceCrop.getApplicationId());
                    f.addMessage(null, message);
                }
                if (rescropres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                             "Failure on applying task");
                    f.addMessage(null, message);
                }
//                redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
                return redirectUrl;
            }
            
            if (sqlFlag == 1) {
                int resres = masterDataService.editResource(resourceRec);
                if (resres == SUCCESS) {
                    sqlFlag = sqlFlag + 1;
                } else {
                    if (resres == DB_NON_EXISTING) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                 "Resource record does not exist");
                        f.addMessage(null, message);
                    }
                    if (resres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                 "Failure on resource update");
                        f.addMessage(null, message);
                    }
                    int delres = masterDataService.delResCropRecord(resourceCrop);
                    if (delres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                                 "resourcecrop record could not be deleted");
                        f.addMessage(null, message);
                    }
                    return redirectUrl;
                }
            }
            
            if (sqlFlag == 2) {
                int taskres = masterDataService.editTaskplanRecord(taskplanRec);
                if (taskres == SUCCESS) {
                    sqlFlag = sqlFlag + 1;
                } else {
                    if (taskres == DB_NON_EXISTING) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Taskplan record does not exist");
                        f.addMessage(null, message);
                    }
                    if (taskres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Failure on taskplan update");
                        f.addMessage(null, message);
                    }
                    int delres = masterDataService.delResCropRecord(resourceCrop);
                    if (delres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                                "resourcecrop record could not be deleted");
                        f.addMessage(null, message);
                    }
                    //rollback changes in farmresource
                    resourceRec.setAvailableAmt(amount);
                    int delfarmres = masterDataService.editResource(resourceRec);
                    if (delfarmres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                                "farmresource record could not be updated");
                        f.addMessage(null, message);
                    }
                    return redirectUrl;
                }
            }
            
            if (sqlFlag == 3) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Task for resource applied successfully.");
                f.addMessage(null, message);
            }            
        }
        
        if (taskplanRec.getTaskType().equals("LABHRVST")) {
            //        contruction of labourcrop record
            LabourCropDTO labCropRec = new LabourCropDTO();
            int applicationid = masterDataService.getMaxIdForLabCrop();
            if (applicationid == 0 || applicationid == DB_SEVERE) {
                labCropRec.setApplicationId("1");
            } else {
                labCropRec.setApplicationId(String.valueOf(applicationid + 1));
            }
            labCropRec.setHarvestId(taskplanRec.getHarvestId());
            labCropRec.setApplicationDate(taskplanRec.getTaskDt());
            
//        contruction of expense record
            ExpenseDTO expenseRec = new ExpenseDTO();
            int expenseid = masterDataService.getNextIdForExpense();
            if (expenseid == 0 || expenseid == DB_SEVERE) {
                expenseRec.setExpenseId("1");
            } else {
                expenseRec.setExpenseId(String.valueOf(expenseid + 1));
            }
            expenseRec.setDate(taskplanRec.getTaskDt());
            expenseRec.setExpenseType("LABHRVST");
            expenseRec.setExpenseRefId(labCropRec.getApplicationId()); //######labourcrop app id
            expenseRec.setExpenditure(taskplanRec.getAppliedAmtCost());
            expenseRec.setCommString(comments);
            
            //taskplan record construction for update
            taskplanRec.setAppliedFlag("Y");
            
            int labourapply = masterDataService.addLabourCropRecord(labCropRec);
            if (labourapply == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (labourapply == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Duplicate record error for labourapply");
                    f.addMessage(null, message);
                }
                if (labourapply == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on insert in labourapply");
                    f.addMessage(null, message);
                }
                return redirectUrl;
            }
            
            if (sqlFlag == 1) {
                int expres = masterDataService.addExpenseRecord(expenseRec);
                if (expres == SUCCESS) {
                    sqlFlag = sqlFlag + 1;
                } else {
                    if (expres == DB_DUPLICATE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Failure", "Duplicate record error for expense table");
                        f.addMessage(null, message);
                    }
                    if (expres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Failure", "Failure on insert in expense table");
                        f.addMessage(null, message);
                    }
                    int dellab = masterDataService.delLabourCropRecord(labCropRec);
                    if (dellab == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                 "labourcrop record could not be deleted");
                        f.addMessage(null, message);
                    }
                    return redirectUrl;
                }
            }
            
            if (sqlFlag == 2) {
                int taskres = masterDataService.editTaskplanRecord(taskplanRec);
                if (taskres == SUCCESS) {
                    sqlFlag = sqlFlag + 1;
                } else {
                    if (taskres == DB_NON_EXISTING) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "Taskplan record does not exist");
                        f.addMessage(null, message);
                    }
                    if (taskres == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Failure", "Failure in update of Taskplan table");
                        f.addMessage(null, message);
                    }
                    int dellab = masterDataService.delLabourCropRecord(labCropRec);
                    if (dellab == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                 "labourcrop record could not be deleted");
                        f.addMessage(null, message);
                    }
                    int delexp = masterDataService.delExpenseRecord(expenseRec);
                    if (delexp == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                 "expense record could not be deleted");
                        f.addMessage(null, message);
                    }
                    return redirectUrl;
                }
            }
            if (sqlFlag == 3) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Labour(paid) task applied successfully." );
                f.addMessage(null, message);
            }  
        }
        
        if (taskplanRec.getTaskType().equals("LAB")) {
            //taskplan record construction for update
            taskplanRec.setAppliedFlag("Y");
            int taskres = masterDataService.editTaskplanRecord(taskplanRec);
            if (taskres == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Labour task applied successfully." );
                f.addMessage(null, message);
            } else {
                if (taskres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Taskplan record does not exist");
                    f.addMessage(null, message);
                }
                if (taskres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Failure", "Failure in update of Taskplan table");
                    f.addMessage(null, message);
                }                
                return redirectUrl;
            }
        }
        return redirectUrl;
    }
    public String calcShopResAmt(String quantityApplied, String applId, String resId) 
            throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        String redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
        List<ShopResDTO> shopResListResid = masterDataService.getShopResForResid(resId);

        ShopResDTO shopResRec = new ShopResDTO();
        float appliedQuantity = Float.parseFloat(quantityApplied);
        for (int i = 0; i < shopResListResid.size(); i++) {
            shopResRec.setId(shopResListResid.get(i).getId());
            shopResRec.setRate(shopResListResid.get(i).getRate());
            shopResRec.setResRateDate(shopResListResid.get(i).getResRateDate());
            shopResRec.setResourceId(shopResListResid.get(i).getResourceId());
            shopResRec.setShopId(shopResListResid.get(i).getShopId());
            float shopResStock = Float.parseFloat(shopResListResid.get(i).getStockPerRate());
            float shopResRate = Float.parseFloat(shopResListResid.get(i).getRate());
            if (appliedQuantity <= shopResStock) {
                //in this case the applied resource is deducted from the stock(consumed
                //from the stock completely and stock is not more than 0, hence break from this loop.
                shopResStock = shopResStock - appliedQuantity;                
                resCropAppliedCost = resCropAppliedCost + (appliedQuantity * shopResRate);                
                shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
                appliedQuantity = 0;
                
                int shopres = masterDataService.editShopForRes(shopResRec);
                if (shopres != SUCCESS) {
                    resCropAppliedCost = 0;
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "shopresource record could not be updated");
                    f.addMessage(null, message);
                    return redirectUrl;
                }
                
                break;
            }
            if (appliedQuantity  > shopResStock) {
                resCropAppliedCost = resCropAppliedCost + (shopResStock * shopResRate);
                appliedQuantity = appliedQuantity - shopResStock;
                shopResStock = 0;
                shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
            }
            int shopres = masterDataService.editShopForRes(shopResRec);
            if (shopres != SUCCESS) {
                resCropAppliedCost = 0;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "shopresource record could not be updated");
                f.addMessage(null, message);
                return redirectUrl;
            }
        }
        return "OK";
    }
    
    public String getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(String selectedTask) {
        this.selectedTask = selectedTask;
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

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
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

    public String getAmtapplied() {
        return amtapplied;
    }

    public void setAmtapplied(String amtapplied) {
        this.amtapplied = amtapplied;
    }

    public String getAppDt() {
        return appDt;
    }

    public void setAppDt(String appDt) {
        this.appDt = appDt;
    }

    public String getRescat() {
        return rescat;
    }

    public void setRescat(String rescat) {
        this.rescat = rescat;
    }

    public String getCropwt() {
        return cropwt;
    }

    public void setCropwt(String cropwt) {
        this.cropwt = cropwt;
    }

    public String getCropwtunit() {
        return cropwtunit;
    }

    public void setCropwtunit(String cropwtunit) {
        this.cropwtunit = cropwtunit;
    }

    public String getAppliedcost() {
        return appliedcost;
    }

    public void setAppliedcost(String appliedcost) {
        this.appliedcost = appliedcost;
    }

    public float getResCropAppliedCost() {
        return resCropAppliedCost;
    }

    public void setResCropAppliedCost(float resCropAppliedCost) {
        this.resCropAppliedCost = resCropAppliedCost;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    
}
