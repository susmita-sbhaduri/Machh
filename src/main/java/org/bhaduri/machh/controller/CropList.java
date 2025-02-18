/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.controller;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import org.bhaduri.machh.DTO.CropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "cropList")
@ViewScoped
public class CropList implements Serializable {

    List<CropDTO> crops;

    public List<CropDTO> getCrops() {
        return crops;
    }

    public void setCrops(List<CropDTO> crops) {
        this.crops = crops;
    }
    public CropList() {
    }
    public void fillCropValues() {
        MasterDataServices masterDataService = new MasterDataServices();
        crops = masterDataService.getCropList();        
    }
}
