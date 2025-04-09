/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;

/**
 *
 * @author sb
 */
public class ShopResDTO implements Serializable {
    private int reShopRefId;
    private String shopName;
    private String resourceName;
    private float rate;
    private String unit;   

    public int getReShopRefId() {
        return reShopRefId;
    }

    public void setReShopRefId(int reShopRefId) {
        this.reShopRefId = reShopRefId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    
}
