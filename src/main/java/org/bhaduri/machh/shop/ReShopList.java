/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.shop;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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
    public String goToDeleteShopForRes() throws NamingException {
        String redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);

        if (existingshops.size() > 1) {
            MasterDataServices masterDataService = new MasterDataServices();
            int response = masterDataService.deleteShopForRes(selectedResShop);
//        String redirectUrl;
//        FacesMessage message = null;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
            if (response == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Integer.toString(SUCCESS));
            } else {
                if (response == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_NON_EXISTING));
                }
                if (response == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
                }
            }
            f.addMessage(null, message);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atleast one shop should be there for existing Resource.",
                    "Atleast one shop should be there for existing Resource.");
//            f.addMessage("othershopid", message);
            f.addMessage(null, message);
        }
        return redirectUrl;
    }

    public String goToEditShopForRes() throws NamingException {
        String redirectUrl = "/secured/shop/reshopedit?faces-redirect=true&resourceId=" + selectedResShop.getResourceId() + "&shopId=" + selectedResShop.getShopId();
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
