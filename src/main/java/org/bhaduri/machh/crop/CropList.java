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
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.CropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import org.bhaduri.machh.DTO.SiteDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "cropList")
@ViewScoped
public class CropList implements Serializable {
    private CropDTO selectedCrop;
    List<CropDTO> crops;
//    List<SiteDTO> sites;
    

    public CropDTO getSelectedCrop() {
        return selectedCrop;
    }

    public void setSelectedCrop(CropDTO selectedCrop) {
        this.selectedCrop = selectedCrop;
    }
    
    public List<CropDTO> getCrops() {
        return crops;
    }

    public void setCrops(List<CropDTO> crops) {
        this.crops = crops;
    }
    public CropList() {
    }
    public void fillCropValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        crops = masterDataService.getCropList();        
    }
    public String goToEditCrop() {
        String redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + selectedCrop.getCropCategory()+ "&cropnameEd=" + selectedCrop.getCropName();
        return redirectUrl;
    }
    
    public String deleteCrop(CropDTO crop)throws NamingException {
        String redirectUrl = "";
        
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        int response = masterDataService.existsSiteForCrop(selectedCrop.getCropCategory(), selectedCrop.getCropName());
        if( response > 0 && response != DB_SEVERE){
           message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "This crop is not harvested in site yet.", 
                    "This crop is not harvested in site yet.");
            f.addMessage(null, message);           
            redirectUrl = "/secured/crop/croplist?faces-redirect=true";
        } else {
            response = masterDataService.existsResourceForCrop(selectedCrop.getCropCategory(), selectedCrop.getCropName());
            if (response > 0 && response != DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is resources crop is not harvested in site yet.",
                        "This crop is not harvested in site yet.");
                f.addMessage(null, message);
                redirectUrl = "/secured/crop/croplist?faces-redirect=true";
            }
//            int response = masterDataService.existsSiteForCrop(selectedCrop.getCropCategory(), selectedCrop.getCropName());
           System.out.println("Record does not exist");
        }
        redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + selectedCrop.getCropCategory()+ "&cropnameEd=" + selectedCrop.getCropName();
        return redirectUrl;
    }
    
    public CropDTO getSelectedUser() {
        return selectedCrop;
    }
}
