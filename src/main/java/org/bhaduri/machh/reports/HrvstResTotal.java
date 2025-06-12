/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.reports;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "hrvstResTotal")
@ViewScoped
public class HrvstResTotal implements Serializable {
    List<ResourceCropDTO> rescrops;
    private String startDt;
    private String endDt;
    private String harvestId;
    /**
     * Creates a new instance of HrvstResTotal
     */
    public HrvstResTotal() {
        System.out.println("No resourcecrop record is found for this harvest.");
    }
    public void fillValues() throws NamingException, ParseException {
        Date startDate;
        Date endDate;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        startDate = formatter.parse(startDt);
        endDate = formatter.parse(endDt);
        MasterDataServices masterDataService = new MasterDataServices();
        rescrops = masterDataService.getSummaryPerResForHrvst(startDate, endDate, harvestId);
        System.out.println("No resourcecrop record is found for this harvest.");
    }

    public List<ResourceCropDTO> getRescrops() {
        return rescrops;
    }

    public void setRescrops(List<ResourceCropDTO> rescrops) {
        this.rescrops = rescrops;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(String harvestId) {
        this.harvestId = harvestId;
    }
    
}
