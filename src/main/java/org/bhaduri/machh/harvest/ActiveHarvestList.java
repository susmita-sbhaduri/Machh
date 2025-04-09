/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.harvest;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.HarvestDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "activeHarvestList")
@ViewScoped
public class ActiveHarvestList implements Serializable {

    private HarvestDTO selectedHarvest;
    List<HarvestDTO> harvests;
    
    public ActiveHarvestList() {
    }

    public String fillHarvestValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        harvests = masterDataService.getActiveHarvestList();
        String redirectUrl;
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
        
        if(harvests.isEmpty()){
            
            redirectUrl = "/secured/errorpage";
            return redirectUrl;
        } else 
            return null;        
    }
    
//    public String goAcquireResource(){
////        String redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + selectedCrop.getCropCategory()+ "&cropnameEd=" + selectedCrop.getCropName();
//        String redirectUrl = "/secured/resource/acquireresource";
//        return redirectUrl;
//    }
    
    public String goApplyResource(){
//        String redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + selectedCrop.getCropCategory()+ "&cropnameEd=" + selectedCrop.getCropName();
        String redirectUrl = "/secured/userhome";
        return redirectUrl;
    }

    public HarvestDTO getSelectedHarvest() {
        return selectedHarvest;
    }

    public void setSelectedHarvest(HarvestDTO selectedHarvest) {
        this.selectedHarvest = selectedHarvest;
    }   

    public List<HarvestDTO> getHarvests() {
        return harvests;
    }

    public void setHarvests(List<HarvestDTO> harvests) {
        this.harvests = harvests;
    }
    
    
}
