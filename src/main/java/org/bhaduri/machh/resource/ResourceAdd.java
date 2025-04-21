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
import org.bhaduri.machh.DTO.ShopDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "resourceAdd")
@ViewScoped
public class ResourceAdd implements Serializable {
    private String resid;
    private String resname;
    private String selectedShopid;
    private List<ShopDTO> shoplist;
    private float rate;
    private String unit;

    public String fillExistingDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        shoplist = masterDataService.getNextIdForRes(resid); 
        String redirectUrl;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true); 
        if (shoplist.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Shop remains to be attached.",
                    "No Shop remains to be attached.");
//            f.addMessage("othershopid", message);
            f.addMessage(null, message);
            redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
            return redirectUrl;
        }
        else return null;
    }
    public ResourceAdd() {
    }
    
}
