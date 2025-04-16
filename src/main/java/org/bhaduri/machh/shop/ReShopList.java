/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.shop;

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
@Named(value = "reShopList")
@ViewScoped
public class ReShopList implements Serializable {
    private ShopResDTO selectedResShop;
    List<ShopResDTO> existingshops;
    private String resourceId;
    private String resourceName;
    
    public ReShopList() {
    }
    public String fillShopDetails() throws NamingException {
        String redirectUrl;
        MasterDataServices masterDataService = new MasterDataServices();
        existingshops = masterDataService.getShopResForResid(resourceId);      
        
        if(existingshops.isEmpty()){            
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
            return redirectUrl;
        } else {
            return null;    
        }
    }
    public String goToAddShopForRes() {
        String redirectUrl = "/secured/shop/maintainresshop?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
        return redirectUrl;
    }
    public String goToDeleteShopForRes() {
        String redirectUrl = "/secured/userhome";
        return redirectUrl;
    }
    public String goToEditShopForRes() {
        String redirectUrl = "/secured/userhome";
        return redirectUrl;
    }
    public ShopResDTO getSelectedResShop() {
        return selectedResShop;
    }

    public void setSelectedResShop(ShopResDTO selectedResShop) {
        this.selectedResShop = selectedResShop;
    }

    

    public List<ShopResDTO> getExistingshops() {
        return existingshops;
    }

    public void setExistingshops(List<ShopResDTO> existingshops) {
        this.existingshops = existingshops;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
}
