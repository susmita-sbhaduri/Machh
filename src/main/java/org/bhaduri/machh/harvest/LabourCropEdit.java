/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.DTO.LabourCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "labourCropEdit")
@ViewScoped
public class LabourCropEdit implements Serializable {

    private String selectedLabcrop;
    private String site;
    private String cropcat;
    private String cropname;
    private float amountPaid;
    private Date applyDt = new Date();
    private String comments;
    private ExpenseDTO expensePrev;
    private LabourCropDTO labcropPrev;
    /**
     * Creates a new instance of LabourCropEdit
     */
    public LabourCropEdit()throws NamingException, ParseException {
        MasterDataServices masterDataService = new MasterDataServices();
        labcropPrev = masterDataService.getLabCropForId(selectedLabcrop);
        HarvestDTO harvestRecord = masterDataService
                .getHarvestRecForId(rescropPrev.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        existingresources = masterDataService.getResourceList();
    }
    
}
