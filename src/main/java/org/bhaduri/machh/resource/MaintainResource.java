/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.resource;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
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
    public String fillResourceValues() throws NamingException {
        String redirectUrl;
        MasterDataServices masterDataService = new MasterDataServices();
        existingresources = masterDataService.getResourceList();      
        
        if(existingresources.isEmpty()){            
            redirectUrl = "/secured/userhome";
            return redirectUrl;
        } else {
            return null;    
        }
    }
    
    public String deleteRes() throws NamingException {
        String redirectUrl = "/secured/shop/maintainresshop?faces-redirect=true&resourceId=" + 
                selectedRes.getResourceId()+ "&resourceName=" + selectedRes.getResourceName();
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        int shopres = masterDataService.deleteShopResForResid(selectedRes.getResourceId());
        int res = masterDataService.delResource(selectedRes);
        
        if (existingshops.size() > 1) {
            MasterDataServices masterDataService = new MasterDataServices();
            int response = masterDataService.deleteShopForRes(selectedResShop);
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
            f.addMessage(null, message);
        }
        return redirectUrl;
    }
    
    public String goToShopForRes() {        
        String redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + selectedRes.getResourceId()+ "&resourceName=" + selectedRes.getResourceName();
        return redirectUrl;
//        return "/secured/userhome";
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
