/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
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
    /**
     * Creates a new instance of TaskApply
     */
    public TaskApply() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        TaskPlanDTO taskplanRec = masterDataService.getTaskPlanForId(selectedTask);
        
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(taskplanRec.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        if (taskplanRec.getTaskType().equals("RES")) {
            FarmresourceDTO resourceRec = masterDataService
                    .getResourceNameForId(Integer.parseInt(taskplanRec.getResourceId()));
            resname = resourceRec.getResourceName();
            amount = resourceRec.getAvailableAmt();
            unit = resourceRec.getUnit();
            amtapplied = taskplanRec.getAppliedAmount();
            
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
            appliedcost = taskplanRec.getAppliedAmtCost();
            resname = "";
            amount = "";
            unit = "";
            amtapplied = "";
            rescat = "";
            cropwt = "";
            cropwtunit = "";
        }
        appDt = taskplanRec.getTaskDt();
    }
}
