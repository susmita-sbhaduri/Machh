/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "harvestDetails")
@ViewScoped
public class HarvestDetails implements Serializable {
    private String selectedRes;
    private String selectedHarvest;
    private String site;
    private String cropcat;
    private String cropname;
    private List<ResourceCropDTO> appliedreslist = new ArrayList<>();
    private List<ResourceCropDTO> applresourcelist = new ArrayList<>();
    private List<LabourCropDTO> appliedlablist = new ArrayList<>();
    private List<ResourceCropDTO> appliedcroplist = new ArrayList<>();
    /**
     * Creates a new instance of HarvestDetails
     */
    public HarvestDetails() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        appliedreslist = masterDataService.getResCropForHarvest(selectedHarvest);
        int j = 0;
        ResourceCropDTO record = new ResourceCropDTO();
        for (int i = 0; i < appliedreslist.size(); i++) {
            FarmresourceDTO farmres = masterDataService.getResourceNameForId(Integer.parseInt(
                    appliedreslist.get(i).getResourceId()));
            if(farmres.getCropwtunit()!=null){
                record.setResourceName(farmres.getResourceName());
                record.setAppliedAmount(appliedreslist.get(i).getAppliedAmount());
                record.setApplicationDt(appliedreslist.get(i).getApplicationDt());
                appliedcroplist.add(record);                
            } else  {
                applresourcelist.add(record);
            }
            record = new ResourceCropDTO();
        }
        
        HarvestDTO harvestRecord = masterDataService.getHarvestRecForId(selectedHarvest);
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
    }

    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
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

    public List<ResourceCropDTO> getAppliedreslist() {
        return appliedreslist;
    }

    public void setAppliedreslist(List<ResourceCropDTO> appliedreslist) {
        this.appliedreslist = appliedreslist;
    }

    public List<ResourceCropDTO> getApplresourcelist() {
        return applresourcelist;
    }

    public void setApplresourcelist(List<ResourceCropDTO> applresourcelist) {
        this.applresourcelist = applresourcelist;
    }

    public List<LabourCropDTO> getAppliedlablist() {
        return appliedlablist;
    }

    public void setAppliedlablist(List<LabourCropDTO> appliedlablist) {
        this.appliedlablist = appliedlablist;
    }

    public List<ResourceCropDTO> getAppliedcroplist() {
        return appliedcroplist;
    }

    public void setAppliedcroplist(List<ResourceCropDTO> appliedcroplist) {
        this.appliedcroplist = appliedcroplist;
    }
    
    
}
