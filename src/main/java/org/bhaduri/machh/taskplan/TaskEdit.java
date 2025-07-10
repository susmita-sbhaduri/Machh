/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
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
@Named(value = "taskEdit")
@ViewScoped
public class TaskEdit implements Serializable {
    private String selectedTask;
    private String taskName;
    private String taskType;
    private String taskDt;
    private List<FarmresourceDTO> availableresources;
    private int selectedIndexRes;
    private List<HarvestDTO> activeharvests;
    private int selectedIndexHarvest;
    private String unit;
    private float amtapplied;
    private float appliedcost;
    private boolean resReadonly = false; // default not readonly
    private boolean costReadonly = false;
    /**
     * Creates a new instance of TaskEdit
     */
    public TaskEdit() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        TaskPlanDTO harvestRecord = masterDataService.getHarvestRecForId(selectedHarvest);
//        SiteDTO siteRecord = masterDataService.getSiteNameForId(harvestRecord.getSiteName());
//        CropDTO cropRecord = masterDataService.getCropPerPk(harvestRecord.getCropName());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        existingresources = masterDataService.getNonzeroResList();
    }
}
