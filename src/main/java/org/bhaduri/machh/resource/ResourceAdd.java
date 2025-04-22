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
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
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
    
    public String goToSaveRes() throws NamingException {
        String redirectUrl = "/secured/resource/addinventory?faces-redirect=true";
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        ShopDTO selectedShop = shoplist.get(selectedIndex);

        if (resname.isBlank()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource name cannot be empty.",
                    "Resource name cannot be empty.");
            f.addMessage("unit", message);
            return redirectUrl;
        }

        if (selectedShop.getShopId().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "First add shop.",
                    "First add shop.");
            f.addMessage("shopid", message);
            return redirectUrl;
        }
        if (rate == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource Rate cannot be 0.",
                    "Resource Rate cannot be 0.");
            f.addMessage("rate", message);
            return redirectUrl;
        }

        if (unit.isBlank()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unit cannot be empty.",
                    "Unit cannot be empty.");
            f.addMessage("unit", message);
            return redirectUrl;
        }
        redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        MasterDataServices masterDataService = new MasterDataServices();
        FarmresourceDTO resAddBean = new FarmresourceDTO();
        resAddBean.setResourceId(resid);
        resAddBean.setResourceName(resname);
        resAddBean.setUnit(unit);
        int resres = masterDataService.addResource(resAddBean);

        ShopResDTO resShopUpdBean = new ShopResDTO();
        resShopUpdBean.setShopId(selectedShop.getShopId());
        resShopUpdBean.setShopName(selectedShop.getShopName());
        resShopUpdBean.setResourceId(resid);
        resShopUpdBean.setResourceName(resname);
        resShopUpdBean.setRate(String.format("%.2f", rate));
        int shopres = masterDataService.addShopResource(resShopUpdBean);
        if (resres == SUCCESS && shopres == SUCCESS) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inventory added", Integer.toString(SUCCESS));
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        } else {
            if (resres == DB_DUPLICATE || shopres == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate record", Integer.toString(DB_DUPLICATE));
            }
            if (resres == DB_SEVERE || shopres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
            }
        }
        f.addMessage(null, message);
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

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
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
