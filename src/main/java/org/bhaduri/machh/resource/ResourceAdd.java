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
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ShopDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
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
    private int selectedIndex;
    private List<ShopDTO> shoplist;
    private float rate;
    private String unit;

    public String fillExistingDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        resid = masterDataService.getNextIdForRes(); 
        if(resid==null){
            resid="1";
        }
        shoplist = masterDataService.getShopList();
        String redirectUrl;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true); 
        if (shoplist.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Shop remains to be attached.",
                    "No Shop remains to be attached.");
//            f.addMessage("othershopid", message);
            f.addMessage(null, message);
//            redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
            return redirectUrl;
        }
        else return null;
    }
    
    public String goToSaveRes() throws NamingException{
        String redirectUrl = "/secured/home";
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        ShopDTO selectedShop = shoplist.get(selectedIndex);

        if (selectedShop.getShopId().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "First add shop.",
                    "First add shop.");
            f.addMessage("shopid", message);
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        } else {
            if (rate == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource Rate cannot be 0.",
                        "Resource Rate cannot be 0.");
                f.addMessage("rate", message);
                redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
            } else {
                if (unit.isBlank()) {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unit cannot be empty.",
                            "Unit cannot be empty.");
                    f.addMessage("unit", message);
                    redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
                } else {
                
                    ShopResDTO resShopUpdBean = new ShopResDTO();
                    MasterDataServices masterDataService = new MasterDataServices();
                    resShopUpdBean.setShopId(selectedShop.getShopId());
                    resShopUpdBean.setShopName(selectedShop.getShopName());
                    resShopUpdBean.setResourceId(resid);
                    resShopUpdBean.setResourceName(resname);
                    resShopUpdBean.setRate(String.format("%.2f", rate));
                    int response = masterDataService.addShopResource(resShopUpdBean);
                    redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
                if (response == SUCCESS) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Integer.toString(SUCCESS));

                } else {
                    if (response == DB_DUPLICATE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_DUPLICATE));

                    }
                    if (response == DB_SEVERE) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));

                    }
                }
                f.addMessage(null, message);
            }
        }
        return redirectUrl;
    }
    public ResourceAdd() {
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public String getSelectedShopid() {
        return selectedShopid;
    }

    public void setSelectedShopid(String selectedShopid) {
        this.selectedShopid = selectedShopid;
    }

    public List<ShopDTO> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<ShopDTO> shoplist) {
        this.shoplist = shoplist;
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
