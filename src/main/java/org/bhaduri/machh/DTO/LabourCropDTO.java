/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;

/**
 *
 * @author sb
 */
public class LabourCropDTO implements Serializable {
    private String applicationId;
    private String harvestId;
    private String labourCategory;
    private String applicationDate;
    private String appliedAmount;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    
    public String getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(String harvestId) {
        this.harvestId = harvestId;
    }

    public String getLabourCategory() {
        return labourCategory;
    }

    public void setLabourCategory(String labourCategory) {
        this.labourCategory = labourCategory;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(String appliedAmount) {
        this.appliedAmount = appliedAmount;
    }
    
    
    
}
