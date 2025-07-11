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
import org.bhaduri.machh.DTO.TaskPlanDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "taskEdit")
@ViewScoped
public class TaskEdit implements Serializable {
    private String selectedTask;
    private String taskName;
    private String taskType;
    private Date taskDt;
    private List<FarmresourceDTO> availableresources;
    private int selectedIndexRes;
    private List<HarvestDTO> activeharvests;
    private int selectedIndexHarvest;
    private String unit;
    private float amtapplied;
    private float appliedcost;
    private boolean resReadonly = false; // default not readonly
    private boolean costReadonly = false;
    private String rescat;
    private String cropwt;
    private String cropwtunit;
    /**
     * Creates a new instance of TaskEdit
     */
    public TaskEdit() {
    }
    public void fillValues() throws NamingException, ParseException {
        MasterDataServices masterDataService = new MasterDataServices();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TaskPlanDTO taskRecord = masterDataService.getTaskPlanForId(selectedTask);
        taskDt = sdf.parse(taskRecord.getTaskDt());
        taskType = taskRecord.getTaskType();
        taskName = taskRecord.getTaskName();
        
        activeharvests = masterDataService.getActiveHarvestList();
        for (int i = 0; i < activeharvests.size(); i++) {            
            if (activeharvests.get(i).getHarvestid().equals(taskRecord.getHarvestId())) {
                selectedIndexHarvest = i;
                break;
            }
        }
        
        availableresources = masterDataService.getNonzeroResList();
        for (int i = 0; i < availableresources.size(); i++) {            
            if (availableresources.get(i).getResourceId().equals(taskRecord.getResourceId())) {
                selectedIndexRes = i;
                break;
            }
        }
        
        if (taskType.equals("LABHRVST")) {
            resReadonly = true;
            costReadonly = false;
            taskType = "Labour";
            unit = "Rs.";
        }
        if (taskType.equals("RES")) {
            resReadonly = false;
            costReadonly = true;
            taskType = "Resource";

            unit = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                    get(selectedIndexRes).getResourceId())).getUnit();

            if (masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                    get(selectedIndexRes).getResourceId()))
                    .getCropwtunit() != null) {
                rescat = "Crop";
                cropwt = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                        get(selectedIndexRes).getResourceId()))
                        .getCropweight();
                cropwtunit = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                        get(selectedIndexRes).getResourceId()))
                        .getCropwtunit();

            } else {
                rescat = "Other";
                cropwt = "";
                cropwtunit = "";
            }
        }
        if (taskRecord.getAppliedAmount()==null)
            amtapplied = 0;
        else
            amtapplied = Float.parseFloat(taskRecord.getAppliedAmount());
        
        if (taskRecord.getAppliedAmtCost()==null)
            appliedcost = 0;
        else
            appliedcost = Float.parseFloat(taskRecord.getAppliedAmtCost());
    }
    public void onResourceChange() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                get(selectedIndexRes).getResourceId())).getUnit();
        
        if(masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                get(selectedIndexRes).getResourceId()))
                .getCropwtunit()!=null){
            rescat = "Crop";
            cropwt = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                get(selectedIndexRes).getResourceId()))
                    .getCropweight();
            cropwtunit = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                get(selectedIndexRes).getResourceId()))
                    .getCropwtunit();
            
        } else{ 
            rescat = "Other";
            cropwt = "";
            cropwtunit = "";
        }
    }
    
    public String saveTask() throws NamingException {
        
        String redirectUrl = "/secured/taskplan/taskedit?faces-redirect=true&selectedTask=" + selectedTask;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        Date today = new Date();
        if (taskDt.before(today)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "Task cannot be added in a past date.");
            f.addMessage(null, message);
            return redirectUrl;
        }
        
        if (taskType.equals("Resource") && amtapplied == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide non-zero resource amount.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        }
        if (taskType.equals("Labour") && appliedcost == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide non-zero cost.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        }
//        int sqlFlag = 0;
        MasterDataServices masterDataService = new MasterDataServices();
        
//        
//        
//        //taskplan record construction
        TaskPlanDTO taskplanRec = masterDataService.getTaskPlanForId(selectedTask);
        taskplanRec.setTaskId(selectedTask);
        taskplanRec.setHarvestId(activeharvests.get(selectedIndexHarvest).getHarvestid());
        if (taskplanRec.getTaskType().equals("RES")) {
//            taskplanRec.setTaskType("RES");
            taskplanRec.setResourceId(availableresources.get(selectedIndexRes).getResourceId());
            taskplanRec.setAppliedAmount(String.format("%.2f", amtapplied));
            taskplanRec.setAppliedAmtCost(null);
        }
        if (taskplanRec.getTaskType().equals("LABHRVST")) {
//            taskplanRec.setTaskType("LABHRVST");
            taskplanRec.setResourceId(null);
            taskplanRec.setAppliedAmount(null);
            taskplanRec.setAppliedAmtCost(String.format("%.2f", appliedcost));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        taskplanRec.setTaskDt(sdf.format(taskDt));
        taskplanRec.setAppliedFlag(null);
        
        int response = masterDataService.editTaskplanRecord(taskplanRec);
        redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
        if (response == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Task is updated successfully");
            f.addMessage(null, message);
        } else {
            if (response == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                         "Task does not exist.");
                f.addMessage(null, message);
            }
            if (response == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                         "Failure on adding task");
                f.addMessage(null, message);
            }

        }
        return redirectUrl;
//        ResourceCropDTO resourceCrop = new ResourceCropDTO();        
//        int applicationid = masterDataService.getMaxIdForResCrop();
//        if (applicationid == 0 || applicationid == DB_SEVERE) {
//            resourceCrop.setApplicationId("1");
//        } else {
//            resourceCrop.setApplicationId(String.valueOf(applicationid + 1));
//        }
//        resourceCrop.setResourceId(selectedRes);
//        resourceCrop.setHarvestId(selectedHarvest);
//        resourceCrop.setAppliedAmount(String.format("%.2f", amtapplied)); 
//        //shopresource record construction and update
//        String shopResupdate = calcShopResAmt(amtapplied, resourceCrop.getApplicationId());
//        //shopresource record construction and update
//        if(shopResupdate.equals("OK")){
//            resourceCrop.setAppliedAmtCost((String.format("%.2f", resCropAppliedCost)));
//        } else resourceCrop.setAppliedAmtCost("0.00");
//        resourceCrop.setApplicationDt(sdf.format(applyDt));
//        
//        
//        
//        
//        //farmresource record construction
//        FarmresourceDTO resourceRec = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes));
//        float remainingAmt = Float.parseFloat(amount) - amtapplied;
//        resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
//        
//        int rescropres = masterDataService.addResCropRecord(resourceCrop);
//        
//        if (rescropres == SUCCESS) {
//            sqlFlag = sqlFlag + 1;
//        } else {
//            if (rescropres == DB_DUPLICATE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure."
//                        , "Resource already applied with this application ID=" + resourceCrop.getApplicationId());
//                f.addMessage(null, message);
//            }
//            if (rescropres == DB_SEVERE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure."
//                        , "Failure on applying resource");
//                f.addMessage(null, message);
//            } 
//            redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
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
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure"
//                            , "Resource record does not exist");
//                    f.addMessage(null, message);
//                }
//                if (resres == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure"
//                            , "Failure on resource update");
//                    f.addMessage(null, message);
//                }
//                int delres = masterDataService.delResCropRecord(resourceCrop);
//                if (delres == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure"
//                            , "resourcecrop record could not be deleted");
//                    f.addMessage(null, message);
//                }
//                redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//                return redirectUrl;
//            }
//        }
//        if (sqlFlag == 2) {
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                    "Resource applied successfully with application ID=" + resourceCrop.getApplicationId());
//            f.addMessage(null, message);
//        }
//        return redirectUrl;
        
    }
    
    
    public String getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(String selectedTask) {
        this.selectedTask = selectedTask;
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

    public Date getTaskDt() {
        return taskDt;
    }

    public void setTaskDt(Date taskDt) {
        this.taskDt = taskDt;
    }

    

    public List<FarmresourceDTO> getAvailableresources() {
        return availableresources;
    }

    public void setAvailableresources(List<FarmresourceDTO> availableresources) {
        this.availableresources = availableresources;
    }

    public int getSelectedIndexRes() {
        return selectedIndexRes;
    }

    public void setSelectedIndexRes(int selectedIndexRes) {
        this.selectedIndexRes = selectedIndexRes;
    }

    public List<HarvestDTO> getActiveharvests() {
        return activeharvests;
    }

    public void setActiveharvests(List<HarvestDTO> activeharvests) {
        this.activeharvests = activeharvests;
    }

    public int getSelectedIndexHarvest() {
        return selectedIndexHarvest;
    }

    public void setSelectedIndexHarvest(int selectedIndexHarvest) {
        this.selectedIndexHarvest = selectedIndexHarvest;
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

    public float getAppliedcost() {
        return appliedcost;
    }

    public void setAppliedcost(float appliedcost) {
        this.appliedcost = appliedcost;
    }

    public boolean isResReadonly() {
        return resReadonly;
    }

    public void setResReadonly(boolean resReadonly) {
        this.resReadonly = resReadonly;
    }

    public boolean isCostReadonly() {
        return costReadonly;
    }

    public void setCostReadonly(boolean costReadonly) {
        this.costReadonly = costReadonly;
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
    
    
}
