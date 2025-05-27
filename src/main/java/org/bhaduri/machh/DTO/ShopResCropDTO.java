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
public class ShopResCropDTO implements Serializable {
    private String Id;
    private String shopResId;
    private String resCropId;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getShopResId() {
        return shopResId;
    }

    public void setShopResId(String shopResId) {
        this.shopResId = shopResId;
    }

    public String getResCropId() {
        return resCropId;
    }

    public void setResCropId(String resCropId) {
        this.resCropId = resCropId;
    }
    
}
