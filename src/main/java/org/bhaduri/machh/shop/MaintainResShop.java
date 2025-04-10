/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.shop;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "maintainResShop")
@ViewScoped
public class MaintainResShop implements Serializable {
    private int resourceId;
    private String resourceName;
    private String shopid;
    private float rate;
    private List<String> shopids;
    private ShopResDTO resShopUpdBean;
    
    @PostConstruct
    public void init() {
        try {
            fillShopNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MaintainResShop() {
    }

    public void fillShopDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        cropEditBean = masterDataService.getOtherShops(resourceId, resourceName); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateStringSow = sdf.parse(cropEditBean.getSowingDate());
            dateStringHarvest = sdf.parse(cropEditBean.getHarvestDate());
        } catch (ParseException ex) {
            Logger.getLogger(CropEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String updShopToRes() throws NamingException {  
        String redirectUrl;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        if (shopid == null || shopid.trim().isEmpty()) {
            
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
                
            }
        }
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

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<String> getShopids() {
        return shopids;
    }

    public void setShopids(List<String> shopids) {
        this.shopids = shopids;
    }
    
}
