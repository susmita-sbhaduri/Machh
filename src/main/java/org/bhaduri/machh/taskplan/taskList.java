/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.taskplan;

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
    private String taskname;
    private String selectedTaskType;
    private List<FarmresourceDTO> availableresources;
    private int selectedIndexRes;
    private List<HarvestDTO> activeharvests;
    private int selectedIndexHarvest;
    private String amount;
    private Date applyDt;
    /**
     * Creates a new instance of taskList
     */
    public taskList() {
    }
    public void onDateSelect() throws NamingException {
//        System.out.println("No crop categories are found." + selectedShop);
        
        MasterDataServices masterDataService = new MasterDataServices();
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        tasks = masterDataService.getActiveHarvestList();
//        selectedShopResLst = masterDataService.getResShopForPk(selectedRes, selectedShop);
//        selectedShopRes = selectedShopResLst.get(0);
//        rate = 0;
//        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
    }
    public void goApplyResource() throws NamingException {
//        System.out.println("No crop categories are found." + selectedShop);
    }
    public void goToSaveTask() throws NamingException {
//        System.out.println("No crop categories are found." + selectedShop);
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

    public String getSelectedTaskType() {
        return selectedTaskType;
    }

    public void setSelectedTaskType(String selectedTaskType) {
        this.selectedTaskType = selectedTaskType;
    }
    
}
