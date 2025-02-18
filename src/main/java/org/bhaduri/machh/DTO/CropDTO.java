/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
public class CropDTO implements Serializable {
    private String cropCategory;
    private String cropName;
    private String details;
    private Date sowingDate;
    private Date harvestDate;

    public String getCropCategory() {
        return cropCategory;
    }

    public void setCropCategory(String cropCategory) {
        this.cropCategory = cropCategory;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(Date sowingDate) {
        this.sowingDate = sowingDate;
    }

    public Date getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(Date harvestDate) {
        this.harvestDate = harvestDate;
    }
    
    
}
