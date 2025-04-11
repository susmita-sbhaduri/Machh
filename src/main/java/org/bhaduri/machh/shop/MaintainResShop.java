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
import org.bhaduri.machh.DTO.ShopDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "maintainResShop")
@ViewScoped
public class MaintainResShop implements Serializable {
    private String resourceId;
    private String resourceName;
    private int shopid;
    private float rate;
    private List<ShopDTO> shoplist;
    private ShopResDTO resShopUpdBean;
    
    
    public MaintainResShop() {
    }

    public void fillShopDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        shoplist = masterDataService.getOtherShopsFor(resourceId); 
               
    }
    
    public String updShopToRes() throws NamingException {  
        String redirectUrl;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        if (shopid == 0) {            
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Shop name is required.",
                    "Shop name is required.");
            f.addMessage("shopid", message);
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
//            return redirectUrl;
        } else {
            if (rate == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource Rate is required.", 
                "Resource Rate is required.");
                f.addMessage("rate", message);
                redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
            }
            else{
                resShopUpdBean = new ShopResDTO();
                redirectUrl = "/secured/userhome?faces-redirect=true";
            }
        }
        return redirectUrl;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<ShopDTO> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<ShopDTO> shoplist) {
        this.shoplist = shoplist;
    }

    public ShopResDTO getResShopUpdBean() {
        return resShopUpdBean;
    }

    public void setResShopUpdBean(ShopResDTO resShopUpdBean) {
        this.resShopUpdBean = resShopUpdBean;
    }
    
}
