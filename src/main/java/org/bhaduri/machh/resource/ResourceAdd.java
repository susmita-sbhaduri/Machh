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
    private String rescat;
    private String unitcrop;
    private boolean unitcropReadonly = true; // default as readonly

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
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "No shops to be linked.");
//            f.addMessage("othershopid", message);
            f.addMessage(null, message);
//            redirectUrl = "/secured/shop/reshoplist?faces-redirect=true&resourceId=" + resourceId + "&resourceName=" + resourceName;
            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
            return redirectUrl;
        }
        else return null;
    }
    public void onResourceCatChange() {
        if ("other".equalsIgnoreCase(rescat)) {
            unitcropReadonly = true;
            unitcrop = ""; // optionally clear the field
        } 
        if ("crop".equalsIgnoreCase(rescat)) {
            unitcropReadonly = false;
        } 
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

        if (unit.isBlank()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unit cannot be empty.",
                    "Unit cannot be empty.");
            f.addMessage("unit", message);
            return redirectUrl;
        }
        
        
        
        if ("crop".equalsIgnoreCase(rescat) && unitcrop.isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Unit for crop is mandatory.");
            f.addMessage("unitcrop", message);
            return redirectUrl;
        }
        
        MasterDataServices masterDataService = new MasterDataServices();
        FarmresourceDTO existingreswithName = masterDataService.getResourceIdForName(resname);
        // in Farmresource table, ideally there should be unique id and and associated resourcename
        if (existingreswithName != null) {
            String residprev = existingreswithName.getResourceId();
            //for a newly added resourceid shop id combination ideally there should be one record but as 
            //resourceacquire keeps on adding records in shopresource so there might be many records
            
            List<ShopResDTO> existingResShopIdList = masterDataService.getResShopForPk(residprev, selectedShop.getShopId());
//            ShopResDTO existingResShopId = existingResShopIdList.get(0);
            if (!existingResShopIdList.isEmpty()) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                        "Same Resource and Shop combition already exists.");
                f.addMessage(null, message);
                return redirectUrl;
            }
        }
        
        redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        
        int resres = 999;
        FarmresourceDTO resAddBean = new FarmresourceDTO();
        if (existingreswithName == null) { // there is no record in Farmresource with the given resourcename
            resAddBean.setResourceId(resid);
            resAddBean.setResourceName(resname);
            resAddBean.setUnit(unit);
            resAddBean.setAvailableAmt(String.format("%.2f", 0.00));
            if ("crop".equalsIgnoreCase(rescat) && !unitcrop.isEmpty()){
                resAddBean.setCropwtunit(unitcrop);
            }
            if ("other".equalsIgnoreCase(rescat)){
                resAddBean.setCropwtunit(null);
            }
            resres = masterDataService.addResource(resAddBean);
            if (resres != SUCCESS) {
                if (resres == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Duplicate record error for farmresource table");
                    f.addMessage(null, message);
                }
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Failure on insert in farmresource table");
                    f.addMessage(null, message);
                }
                return redirectUrl;
            }
        } 
//        else {
//            resid = existingreswithName.getResourceId();
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
//                    "Resource with same name already exists.");
//            f.addMessage(null, message);
//            return redirectUrl;
//        }

        ShopResDTO resShopUpdBean = new ShopResDTO();
        int shopresid = masterDataService.getMaxIdForShopRes();
        if (shopresid == 0 || shopresid == DB_SEVERE) {
            shopresid = 1;
        } else {
            shopresid = shopresid + 1;
        }
        resShopUpdBean.setId(String.valueOf(shopresid));
        resShopUpdBean.setShopId(selectedShop.getShopId());
        resShopUpdBean.setShopName(selectedShop.getShopName());
        resShopUpdBean.setResourceId(resid);
        resShopUpdBean.setResourceName(resname);
        resShopUpdBean.setRate(String.format("%.2f", 0.00));
        resShopUpdBean.setStockPerRate(String.format("%.2f", 0.00));
        int shopres = masterDataService.addShopResource(resShopUpdBean);
        if (shopres != SUCCESS) {
            if (shopres == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "Duplicate record error for shopresource table");
                f.addMessage(null, message);
            }
            if (shopres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "Failure on insert in shopresource table");
                f.addMessage(null, message);
            }
            if (resres == SUCCESS) { //farmresource record is added
                int delres = masterDataService.delResource(resAddBean);
                if (delres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                            "Farmresource record could not be deleted");
                    f.addMessage(null, message);
                }
            }
            return redirectUrl;
        }
        if (resres == SUCCESS) {
            if (shopres == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Resource added successfully");
                f.addMessage(null, message);
            }
        } else {
            if (shopres == SUCCESS) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                        "Resource added successfully");
                f.addMessage(null, message);
            }
        }
//        if (resres == SUCCESS && shopres == SUCCESS) {
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resource added", Integer.toString(SUCCESS));
//            redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
//        } else {
//            if (resres == DB_DUPLICATE || shopres == DB_DUPLICATE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Duplicate record", Integer.toString(DB_DUPLICATE));
//            }
//            if (resres == DB_SEVERE || shopres == DB_SEVERE) {
//                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
//            }
//        }
//        f.addMessage(null, message);
//        if (sqlFlag==2) {
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", 
//                    "Resource added successfully");
//            f.addMessage(null, message);
//        }
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

    public String getRescat() {
        return rescat;
    }

    public void setRescat(String rescat) {
        this.rescat = rescat;
    }

    public String getUnitcrop() {
        return unitcrop;
    }

    public void setUnitcrop(String unitcrop) {
        this.unitcrop = unitcrop;
    }

    public boolean isUnitcropReadonly() {
        return unitcropReadonly;
    }

    public void setUnitcropReadonly(boolean unitcropReadonly) {
        this.unitcropReadonly = unitcropReadonly;
    }
    
}
