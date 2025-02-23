/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import org.bhaduri.machh.DTO.CropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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
    
    public String save() {
        MasterDataServices masterDataService = new MasterDataServices();
        int response = masterDataService.editCrop(cropEditBean);
        FacesMessage message = null;
        String redirectUrl = "";
        if(response==SUCCESS){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Integer.toString(SUCCESS));
            redirectUrl = "/secured/crop/croplist";
        } else {
            if (response == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_NON_EXISTING));
                redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + cropcatEd+ "&cropnameEd=" + cropnameEd;
            }
            if (response == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
                redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + cropcatEd+ "&cropnameEd=" + cropnameEd;              
            }
        }
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        f.addMessage(null, message);
        return redirectUrl;
    }
    
    public String testAction() {
        System.out.println("Test action called");
        return null; // Just to see if this gets called
    }

    public CropEdit() {
        System.out.println("PostConstruct: Bean initialized");
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
