/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.resource;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "maintainResource")
@ViewScoped
public class MaintainResource implements Serializable {
    private FarmresourceDTO selectedRes;
    List<FarmresourceDTO> existingresources;
    
    public MaintainResource() {
    }
    public void fillResourceValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        existingresources = masterDataService.getResourceList();      
        
        if(existingresources.isEmpty()){
            
        }       
    }
    
    
    public String goToShopForRes() {
        
        String redirectUrl = "/secured/shop/maintainresshop?faces-redirect=true&resourceId=" + selectedRes.getResourceId()+ 
                "&resourceName=" + selectedRes.getResourceName();
        return redirectUrl;
    }

    public List<FarmresourceDTO> getExistingresources() {
        return existingresources;
    }

    public void setExistingresources(List<FarmresourceDTO> existingresources) {
        this.existingresources = existingresources;
    }

    public FarmresourceDTO getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(FarmresourceDTO selectedRes) {
        this.selectedRes = selectedRes;
    }
    
    
}
