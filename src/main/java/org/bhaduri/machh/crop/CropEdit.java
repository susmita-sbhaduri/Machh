/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import org.bhaduri.machh.DTO.CropDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "cropEdit")
@ViewScoped
public class CropEdit implements Serializable {
    private String cropcatEd;
    private String cropnameEd;
    private CropDTO cropEditBean;
    
    public void fillCropValues() {
        MasterDataServices masterDataService = new MasterDataServices();
        cropEditBean = masterDataService.getCropsPerPk(cropcatEd, cropnameEd);        
    }
    
    public void save() {
        MasterDataServices masterDataService = new MasterDataServices();
        
        System.out.println("PostConstruct: Bean initialized");
    }
    
    public CropEdit() {
    }

    public String getCropcatEd() {
        return cropcatEd;
    }

    public void setCropcatEd(String cropcatEd) {
        this.cropcatEd = cropcatEd;
    }

    public String getCropnameEd() {
        return cropnameEd;
    }

    public void setCropnameEd(String cropnameEd) {
        this.cropnameEd = cropnameEd;
    }

    public CropDTO getCropEditBean() {
        return cropEditBean;
    }

    public void setCropEditBean(CropDTO cropEditBean) {
        this.cropEditBean = cropEditBean;
    }
    
}
