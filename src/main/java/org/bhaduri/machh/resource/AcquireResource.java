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
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "acquireResource")
@ViewScoped
public class AcquireResource implements Serializable {

    List<ShopResDTO> existingresources;
    public AcquireResource() {
    }
    public void fillResourceValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        existingresources = masterDataService.getShopResName();
        
        
        if(!existingresources.isEmpty()){
            
        }       
    }

    public List<ShopResDTO> getExistingresources() {
        return existingresources;
    }

    public void setExistingresources(List<ShopResDTO> existingresources) {
        this.existingresources = existingresources;
    }
    
}
