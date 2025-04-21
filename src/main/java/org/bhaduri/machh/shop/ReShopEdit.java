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
@Named(value = "reShopEdit")
@ViewScoped
public class ReShopEdit implements Serializable {
    
    private String resourceId;
    private String shopId;
    private ShopResDTO editBean;
    private float rate;
    
    public ReShopEdit() {
    }
    public void fillDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        editBean = masterDataService.getResShopForPk(resourceId, shopId); 
        rate = Float.parseFloat(editBean.getRate());        
    }
    public String goToSaveShopRes() throws NamingException {
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        editBean.setRate(String.format("%.2f",rate));
        int response = masterDataService.editShopForRes(editBean);
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
        String redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + editBean.getResourceId() + "&resourceName=" + editBean.getResourceName();
        return redirectUrl;
    }
    
    public String goToShopForRes(){
        String redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + editBean.getResourceId() + "&resourceName=" + editBean.getResourceName();
        return redirectUrl;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public ShopResDTO getEditBean() {
        return editBean;
    }

    public void setEditBean(ShopResDTO editBean) {
        this.editBean = editBean;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
    
}
