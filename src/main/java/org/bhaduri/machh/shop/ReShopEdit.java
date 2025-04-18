/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.shop;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import javax.naming.NamingException;
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
        editBean = masterDataService.getResShopForPk(resourceId, resourceId); 
        rate = Float.parseFloat(editBean.getRate());        
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
