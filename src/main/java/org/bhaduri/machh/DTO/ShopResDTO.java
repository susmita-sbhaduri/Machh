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
    private String Id;
    private String shopId;
    private String resourceId;
    private String shopName;
    private String resourceName;
    private String resRateDate;
    private String stockPerRate;
    private String amtApplied;
    private String resAppId;
    private String rate; 
    private String unit;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public String getResRateDate() {
        return resRateDate;
    }

    public void setResRateDate(String resRateDate) {
        this.resRateDate = resRateDate;
    }

    public String getStockPerRate() {
        return stockPerRate;
    }

    public void setStockPerRate(String stockPerRate) {
        this.stockPerRate = stockPerRate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmtApplied() {
        return amtApplied;
    }

    public void setAmtApplied(String amtApplied) {
        this.amtApplied = amtApplied;
    }

    public String getResAppId() {
        return resAppId;
    }

    public void setResAppId(String resAppId) {
        this.resAppId = resAppId;
    }
    
    
}
