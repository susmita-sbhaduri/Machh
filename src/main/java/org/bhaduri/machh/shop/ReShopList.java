/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.shop;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import org.bhaduri.machh.DTO.ShopResDTO;

/**
 *
 * @author sb
 */
@Named(value = "reShopList")
@ViewScoped
public class ReShopList implements Serializable {
    private ShopResDTO selectedShop;
    List<ShopResDTO> existingshops;
    private String resourceId;
    private String resourceName;
    
    public ReShopList() {
    }
    
}
