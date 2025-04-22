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
@Named(value = "maintainResShop")
@ViewScoped
public class MaintainResShop implements Serializable {
    private String resourceId;
    private String resourceName;
    private int selectedShopid;
    private float rate;
    private List<ShopDTO> shoplist;
    private ShopResDTO resShopUpdBean;
    private String resourceUnit;
    
    
    public MaintainResShop() {
    }

    public String fillShopDetails() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        shoplist = masterDataService.getOtherShopsFor(resourceId); 
        resourceUnit = masterDataService.getResourceNameForId(Integer.parseInt(resourceId)).getUnit();
        String redirectUrl;
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true); 
        if (shoplist.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Shop remains to be attached.",
                    "No Shop remains to be attached.");
            f.addMessage(null, message);
            redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
            return redirectUrl;
        }
        else return null;
    }
    
    public String updShopToRes() throws NamingException {
        String redirectUrl;
        FacesMessage message = null;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        ShopDTO selectedShop = shoplist.get(selectedShopid);

        if (selectedShop.getShopId().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Shop name is required.",
                    "Shop name is required.");
            f.addMessage("othershopid", message);
            redirectUrl = "/secured/shop/maintainresshop?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;

        } else {
            if (rate == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource Rate cannot be 0.",
                        "Resource Rate cannot be 0.");
                f.addMessage("rate", message);
                redirectUrl = "/secured/shop/maintainresshop?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
            } else {
                resShopUpdBean = new ShopResDTO();
                MasterDataServices masterDataService = new MasterDataServices();
                resShopUpdBean.setShopId(selectedShop.getShopId());
                resShopUpdBean.setShopName(selectedShop.getShopName());
                resShopUpdBean.setResourceId(resourceId);
                resShopUpdBean.setResourceName(resourceName);
                resShopUpdBean.setRate(String.format("%.2f", rate));
                int response = masterDataService.addShopResource(resShopUpdBean);
                redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
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
    
    
    
    public String goToReShopList() {        
        String redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
        return redirectUrl;
//        return "/secured/userhome";
    }
    
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getSelectedShopid() {
        return selectedShopid;
    }

    public void setSelectedShopid(int selectedShopid) {
        this.selectedShopid = selectedShopid;
    }

    public String getResourceUnit() {
        return resourceUnit;
    }

    public void setResourceUnit(String resourceUnit) {
        this.resourceUnit = resourceUnit;
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
