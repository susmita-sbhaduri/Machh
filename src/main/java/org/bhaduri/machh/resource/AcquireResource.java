/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.resource;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "acquireResource")
@ViewScoped
public class AcquireResource implements Serializable {

//    List<ShopResDTO> existingresources;
//    private FarmresourceDTO selectedRes;
    private String selectedRes;
    private String selectedShop;
    private String rate;
    private String unit;
    private List<ShopResDTO> shopForSelectedRes;
    private List<FarmresourceDTO> existingresources;
    private ShopResDTO selectedShopRes;
    
    public AcquireResource() {
    }

    public String fillResourceValues() throws NamingException {
        String redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        MasterDataServices masterDataService = new MasterDataServices();
        existingresources = masterDataService.getResourceList();
        if (existingresources.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Before acquiring resource should be added.",
                    "Before acquiring resource should be added.");
            f.addMessage("resid", message);
            return redirectUrl;
        } else
            return null;
    }
    public void onResourceIdselect() throws NamingException {
//         FarmresourceDTO temp = selectedRes;
         System.out.println("No crop categories are found."+ selectedRes);
         MasterDataServices masterDataService = new MasterDataServices();
         shopForSelectedRes = masterDataService.getShopResForResid(selectedRes);

    }
    public void onShopResSelect() throws NamingException {
        System.out.println("No crop categories are found."+ selectedShop);
        MasterDataServices masterDataService = new MasterDataServices();
        selectedShopRes = masterDataService.getResShopForPk(selectedRes, selectedShop);
        rate = selectedShopRes.getRate();
        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
    }
    public void goToSaveRes(){
//         FarmresourceDTO temp = selectedRes;
         System.out.println("Fired! Selected: " + selectedRes);
    }

    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
    }

    public String getSelectedShop() {
        return selectedShop;
    }

    public void setSelectedShop(String selectedShop) {
        this.selectedShop = selectedShop;
    }

    public List<ShopResDTO> getShopForSelectedRes() {
        return shopForSelectedRes;
    }

    public void setShopForSelectedRes(List<ShopResDTO> shopForSelectedRes) {
        this.shopForSelectedRes = shopForSelectedRes;
    }

    public List<FarmresourceDTO> getExistingresources() {
        return existingresources;
    }

    public void setExistingresources(List<FarmresourceDTO> existingresources) {
        this.existingresources = existingresources;
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

    public ShopResDTO getSelectedShopRes() {
        return selectedShopRes;
    }

    public void setSelectedShopRes(ShopResDTO selectedShopRes) {
        this.selectedShopRes = selectedShopRes;
    }

    
        
}
