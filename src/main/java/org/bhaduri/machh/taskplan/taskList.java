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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.bhaduri.machh.DTO.ShopResCropDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.DTO.TaskPlanDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "taskList")
@ViewScoped
public class taskList implements Serializable {

    private Date selectedDate;
    List<TaskPlanDTO> tasks;
    private TaskPlanDTO selectedTask;
    private String taskName;
    private String selectedTaskType;
    private List<FarmresourceDTO> availableresources;
    private int selectedIndexRes;
    private List<HarvestDTO> activeharvests;
    private int selectedIndexHarvest;
    private String unit;
    private float amtapplied;
    private float appliedcost;
    private boolean resReadonly = false; // default not readonly
    private boolean costReadonly = false;
    private float resCropAppliedCost=0;
    /**
     * Creates a new instance of taskList
     */
    public taskList() {
    }

    public String onDateSelect() throws NamingException {
        String redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        tasks = masterDataService.getTaskPlanListForDate(selectedDate);
        activeharvests = masterDataService.getActiveHarvestList();
        availableresources = masterDataService.getNonzeroResList();
        if (tasks.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "No task is added to this date.");
            f.addMessage(null, message);
            return redirectUrl;
        } else {
            return null;
        }
    }

    public String goApplyResource(){
        String redirectUrl = "/secured/taskplan/taskapply?faces-redirect=true&selectedTask=" + selectedTask.getTaskId();
        return redirectUrl;
    
//        String redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
//        
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        
//        int sqlFlag = 0;
//        MasterDataServices masterDataService = new MasterDataServices();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //Fetching taskplan record
//        TaskPlanDTO taskplanRec = masterDataService.getTaskPlanForId(selectedTask.getTaskId());
//        
//        if (taskplanRec.getTaskType().equals("RES")) {
//            //resourcecrop record construction
//            ResourceCropDTO resourceCrop = new ResourceCropDTO();
//            int applicationid = masterDataService.getMaxIdForResCrop();
//            if (applicationid == 0 || applicationid == DB_SEVERE) {
//                resourceCrop.setApplicationId("1");
//            } else {
//                resourceCrop.setApplicationId(String.valueOf(applicationid + 1));
//            }
//            resourceCrop.setHarvestId(taskplanRec.getHarvestId());
//            resourceCrop.setResourceId(taskplanRec.getResourceId());
//            resourceCrop.setAppliedAmount(taskplanRec.getAppliedAmount());
//            
//            //shopresource record construction and update
//            String shopResupdate = calcShopResAmt(taskplanRec.getAppliedAmount(),
//                    resourceCrop.getApplicationId(), taskplanRec.getResourceId());
//            if (shopResupdate.equals("OK")) {
//                resourceCrop.setAppliedAmtCost((String.format("%.2f", resCropAppliedCost)));
//            } else {
//                resourceCrop.setAppliedAmtCost(null);
//            }
//            resourceCrop.setApplicationDt(sdf.format(selectedDate));
//            
//            //farmresource record construction
//            FarmresourceDTO resourceRec = masterDataService
//                    .getResourceNameForId(Integer.parseInt(taskplanRec.getResourceId()));
//            String amount = resourceRec.getAvailableAmt();
//            float remainingAmt = Float.parseFloat(amount) - Float.parseFloat(taskplanRec.getAppliedAmount());
//            resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
//            
//            //taskplan record construction for update
//            taskplanRec.setAppliedFlag("Y");
//            
//            //Addition and update started
//            int rescropres = masterDataService.addResCropRecord(resourceCrop);
//            
//            if (rescropres == SUCCESS) {
//                sqlFlag = sqlFlag + 1;
//            } else {
//                if (rescropres == DB_DUPLICATE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
//                             "Resource already applied with this application ID=" + resourceCrop.getApplicationId());
//                    f.addMessage(null, message);
//                }
//                if (rescropres == DB_SEVERE) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
//                             "Failure on applying task");
//                    f.addMessage(null, message);
//                }
////                redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//                return redirectUrl;
//            }
//            
//            if (sqlFlag == 1) {
//                int resres = masterDataService.editResource(resourceRec);
//                if (resres == SUCCESS) {
//                    sqlFlag = sqlFlag + 1;
//                } else {
//                    if (resres == DB_NON_EXISTING) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                                 "Resource record does not exist");
//                        f.addMessage(null, message);
//                    }
//                    if (resres == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                                 "Failure on resource update");
//                        f.addMessage(null, message);
//                    }
//                    int delres = masterDataService.delResCropRecord(resourceCrop);
//                    if (delres == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
//                                 "resourcecrop record could not be deleted");
//                        f.addMessage(null, message);
//                    }
////                    redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//                    return redirectUrl;
//                }
//            }
//            
//            if (sqlFlag == 2) {
//                int taskres = masterDataService.editTaskplanRecord(taskplanRec);
//                if (taskres == SUCCESS) {
//                    sqlFlag = sqlFlag + 1;
//                } else {
//                    if (taskres == DB_NON_EXISTING) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                                "Taskplan record does not exist");
//                        f.addMessage(null, message);
//                    }
//                    if (taskres == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                                "Failure on taskplan update");
//                        f.addMessage(null, message);
//                    }
//                    int delres = masterDataService.delResCropRecord(resourceCrop);
//                    if (delres == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
//                                "resourcecrop record could not be deleted");
//                        f.addMessage(null, message);
//                    }
//                    //rollback changes in farmresource
//                    resourceRec.setAvailableAmt(amount);
//                    int delfarmres = masterDataService.editResource(resourceRec);
//                    if (delfarmres == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
//                                "farmresource record could not be updated");
//                        f.addMessage(null, message);
//                    }
////                    redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//                    return redirectUrl;
//                }
//            }
//            
//            if (sqlFlag == 3) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                        "Task applied successfully with application ID=" + resourceCrop.getApplicationId());
//                f.addMessage(null, message);
//            }            
//        }
//        
//        if (taskplanRec.getTaskType().equals("LABHRVST")) {
//            
//        }
//        return redirectUrl;
//    }
//    public String calcShopResAmt(String quantityApplied, String applId, String resId) 
//            throws NamingException {
//        MasterDataServices masterDataService = new MasterDataServices();
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        String redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
//        List<ShopResDTO> shopResListResid = masterDataService.getShopResForResid(resId);
//
//        ShopResDTO shopResRec = new ShopResDTO();
//        float appliedQuantity = Float.parseFloat(quantityApplied);
////        int breaki = 0;
//        for (int i = 0; i < shopResListResid.size(); i++) {
//            shopResRec.setId(shopResListResid.get(i).getId());
//            shopResRec.setRate(shopResListResid.get(i).getRate());
//            shopResRec.setResRateDate(shopResListResid.get(i).getResRateDate());
//            shopResRec.setResourceId(shopResListResid.get(i).getResourceId());
//            shopResRec.setShopId(shopResListResid.get(i).getShopId());
////            shopResRec.setResAppId(applId);
//            ///
////            ShopResCropDTO shoprescrop = new ShopResCropDTO();
////            int maxid = masterDataService.getMaxIdForShopResCrop();
////            if (maxid == 0 || maxid == DB_SEVERE) {
////                shoprescrop.setId("1");
////            } else {
////                shoprescrop.setId(String.valueOf(maxid + 1));
////            }
////            shoprescrop.setResCropId(applId);
////            shoprescrop.setShopResId(shopResListResid.get(i).getId());
//            ///
//            float shopResStock = Float.parseFloat(shopResListResid.get(i).getStockPerRate());
//            float shopResRate = Float.parseFloat(shopResListResid.get(i).getRate());
//            if (appliedQuantity <= shopResStock) {
//                //in this case the applied resource is deducted from the stock(consumed
//                //from the stock completely and stock is not more than 0, hence break from this loop.
//                shopResStock = shopResStock - appliedQuantity;                
//                resCropAppliedCost = resCropAppliedCost + (appliedQuantity * shopResRate);                
//                shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
////                shopResRec.setAmtApplied(String.format("%.2f", appliedQuantity));
//                
//                ///                
////                shoprescrop.setAmtApplied(String.format("%.2f", appliedQuantity));
//                ///
//                appliedQuantity = 0;
//                
//                int shopres = masterDataService.editShopForRes(shopResRec);
//                if (shopres != SUCCESS) {
//                    resCropAppliedCost = 0;
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                            "shopresource record could not be updated");
//                    f.addMessage(null, message);
//                    return redirectUrl;
//                }
//                
//                ///
////                int shoprescropres = masterDataService.addShopResCrop(shoprescrop);
////                if (shoprescropres != SUCCESS) {
////                    resCropAppliedCost = 0;
////                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
////                            "shoprescrop record could not be inserted");
////                    f.addMessage(null, message);
////                    return redirectUrl;
////                }
//                ///
////                breaki = i;
//                break;
//            }
//            if (appliedQuantity  > shopResStock) {
//                resCropAppliedCost = resCropAppliedCost + (shopResStock * shopResRate);
//                appliedQuantity = appliedQuantity - shopResStock;
////                shopResRec.setAmtApplied(String.format("%.2f", shopResStock));
//                ///                
////                shoprescrop.setAmtApplied(String.format("%.2f", shopResStock));
//                ///
//                shopResStock = 0;
//                shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
//            }
//            int shopres = masterDataService.editShopForRes(shopResRec);
//            if (shopres != SUCCESS) {
//                resCropAppliedCost = 0;
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
//                        "shopresource record could not be updated");
//                f.addMessage(null, message);
//                return redirectUrl;
//            }
//            ///
////            int shoprescropres = masterDataService.addShopResCrop(shoprescrop);
////            if (shoprescropres != SUCCESS) {
////                resCropAppliedCost = 0;
////                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
////                        "shoprescrop record could not be inserted");
////                f.addMessage(null, message);
////                return redirectUrl;
////            }
//                ///
//        }
//
//        return "OK";
    }
    public String goEditResource(){
        String redirectUrl = "/secured/taskplan/taskedit?faces-redirect=true&selectedTask=" + selectedTask.getTaskId();
        return redirectUrl;
    }
    public void onTaskTypeSelect() throws NamingException{
//        System.out.println("No crop categories are found." + selectedShop);
        if (selectedTaskType.equals("lab")) {
            resReadonly = true;
            costReadonly = false;
            unit = "Rs.";
        }
        if (selectedTaskType.equals("res")) {
            resReadonly = false;
            costReadonly = true;
        }
    }
    
    public void onResourceSelect() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(availableresources.
                get(selectedIndexRes).getResourceId())).getUnit();
    }
    
    public String goToSaveTask() throws NamingException {
        String redirectUrl = "/secured/taskplan/tasklist?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        Date today = new Date(); // current date and time
        if (selectedDate.before(today)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                    "Task cannot be added in a past date.");
            f.addMessage(null, message);
            return redirectUrl;
        }

        if (taskName.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Task name is mandatory.");
            f.addMessage("taskname", message);
            return redirectUrl;
        }
        if (selectedTaskType.equals("res") && amtapplied == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide non-zero resource amount.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        }

        if (selectedTaskType.equals("lab") && appliedcost == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide non-zero cost.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        }

        TaskPlanDTO taskplanRec = new TaskPlanDTO();
        int taskid = masterDataService.getMaxTaskplanId();
        if (taskid == 0 || taskid == DB_SEVERE) {
            taskplanRec.setTaskId(String.valueOf("1"));
        } else {
            taskplanRec.setTaskId(String.valueOf(taskid + 1));
        }
        taskplanRec.setTaskName(taskName);
        taskplanRec.setHarvestId(activeharvests.get(selectedIndexHarvest).getHarvestid());
        if (selectedTaskType.equals("res")) {
            taskplanRec.setTaskType("RES");
            taskplanRec.setResourceId(availableresources.get(selectedIndexRes).getResourceId());
            taskplanRec.setAppliedAmount(String.format("%.2f", amtapplied));
            taskplanRec.setAppliedAmtCost(null);
        }
        if (selectedTaskType.equals("lab")) {
            taskplanRec.setTaskType("LABHRVST");
            taskplanRec.setResourceId(null);
            taskplanRec.setAppliedAmount(null);
            taskplanRec.setAppliedAmtCost(String.format("%.2f", appliedcost));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        taskplanRec.setTaskDt(sdf.format(selectedDate));
        taskplanRec.setAppliedFlag(null);
        
        int response = masterDataService.addTaskplanRecord(taskplanRec);
        if (response == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Task is added to the date successfully");
            f.addMessage(null, message);
        } else {
            if (response == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                         "Task exists already.");
                f.addMessage(null, message);
            }
            if (response == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure.",
                         "Failure on adding task");
                f.addMessage(null, message);
            }
//            redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";

        }
        return redirectUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<TaskPlanDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskPlanDTO> tasks) {
        this.tasks = tasks;
    }

    public TaskPlanDTO getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(TaskPlanDTO selectedTask) {
        this.selectedTask = selectedTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSelectedTaskType() {
        return selectedTaskType;
    }

    public void setSelectedTaskType(String selectedTaskType) {
        this.selectedTaskType = selectedTaskType;
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

}
