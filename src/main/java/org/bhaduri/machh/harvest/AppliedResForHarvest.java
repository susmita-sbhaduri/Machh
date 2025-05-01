/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import org.bhaduri.machh.DTO.ResourceCropDTO;

/**
 *
 * @author sb
 */
@Named(value = "appliedResForHarvest")
@ViewScoped
public class AppliedResForHarvest implements Serializable {
    private List<ResourceCropDTO> appliedresources;
    private String appliedHarvest;   
    private String sitename;
    private String cropcat;
    private String cropname;
    /**
     * Creates a new instance of AppliedResForHarvest
     */
    public AppliedResForHarvest() {
    }

    public List<ResourceCropDTO> getAppliedresources() {
        return appliedresources;
    }

    public void setAppliedresources(List<ResourceCropDTO> appliedresources) {
        this.appliedresources = appliedresources;
    }

    

    public String getAppliedHarvest() {
        return appliedHarvest;
    }

    public void setAppliedHarvest(String appliedHarvest) {
        this.appliedHarvest = appliedHarvest;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
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
    
}
