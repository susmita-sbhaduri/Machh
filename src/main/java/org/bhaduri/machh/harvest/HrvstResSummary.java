/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "hrvstResSummary")
@ViewScoped
public class HrvstResSummary implements Serializable {
    List<ResourceCropDTO> resoures;
    /**
     * Creates a new instance of HrvstResSummary
     */
    public HrvstResSummary() {
    }
    public void fillValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        resoures = masterDataService.getResSummaryPerID();
        System.out.println("No resourcecrop record is found for this harvest.");
    }

    public List<ResourceCropDTO> getResoures() {
        return resoures;
    }

    public void setResoures(List<ResourceCropDTO> resoures) {
        this.resoures = resoures;
    }
    
    
}
