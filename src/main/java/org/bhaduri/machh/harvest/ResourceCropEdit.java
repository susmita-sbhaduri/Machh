/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 *## this version includues the logic to switch the resource doing active harvests -->review resources
 */
package org.bhaduri.machh.harvest;


import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import org.bhaduri.machh.DTO.HarvestDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResAcquireDTO;
import org.bhaduri.machh.DTO.ResourceCropDTO;
import org.bhaduri.machh.DTO.ShopResCropDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "resourceCropEdit")
@ViewScoped
public class ResourceCropEdit implements Serializable {

    private String selectedRes;
    private String resName;
    private String selectedRescrop;
    private String site;
    private String cropcat;
    private String cropname;
//    private List<FarmresourceDTO> existingresources;
    private String amount;
    private String unit;
    private float amtapplied;
//    private float amtappliedprev;
    private Date applyDt;
//    private Date applyDtPrev;
    private FarmresourceDTO farmresPrev;
    private ResourceCropDTO rescropPrev;
//    private float resCropAppliedCostPrev;
    private float resCropAppliedCost=0;

    public ResourceCropEdit() {
    }
    
    public void fillValues() throws NamingException, ParseException {
        MasterDataServices masterDataService = new MasterDataServices();
        rescropPrev = masterDataService.getResCropForId(selectedRescrop);
//        resCropAppliedCost = Float.parseFloat(rescropPrev.getAppliedAmtCost());
        HarvestDTO harvestRecord = masterDataService
                .getHarvestRecForId(rescropPrev.getHarvestId());
        site = harvestRecord.getSiteName();
        cropcat = harvestRecord.getCropCategory();
        cropname = harvestRecord.getCropName();
        
        resName = rescropPrev.getResourceName();        
        selectedRes = rescropPrev.getResourceId();
        
        farmresPrev = masterDataService.
                getResourceNameForId(Integer.parseInt(rescropPrev.getResourceId()));
        amount = farmresPrev.getAvailableAmt();
        unit = farmresPrev.getUnit();
//        existingresources = masterDataService.getNonzeroResList();
        
       // for autofill        
        this.amtapplied = Float.parseFloat(rescropPrev.getAppliedAmount());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        this.applyDt = formatter.parse(rescropPrev.getApplicationDt());
    }

//    public void onResSelect() throws NamingException {        
//        MasterDataServices masterDataService = new MasterDataServices();
//        amount = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes))
//                .getAvailableAmt();
//        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes))
//                .getUnit();
//    }
    
    public String goToSave() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        
        String redirectUrl = "/secured/harvest/appliedresperharvest?faces-redirect=true&appliedHarvest=" 
                + rescropPrev.getHarvestId();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        float remainingAmt =0;
        float remainingPrevAmt = 0;
        String shopResResponse;
        String shopResDeduct;
        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
        
        if (amtapplied == Float.parseFloat(rescropPrev.getAppliedAmount())) {
            remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt());
            resCropAppliedCost = Float.parseFloat(rescropPrev.getAppliedAmtCost());
        } else {
            remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt())
                    - amtapplied
                    + Float.parseFloat(rescropPrev.getAppliedAmount());
            shopResResponse = calcShopResAmtAdd(selectedRescrop, selectedRes);
            if(shopResResponse.equals("OKADD")){
                shopResResponse = calcShopResAmtDeduct(amtapplied, selectedRescrop, selectedRes);
                if(shopResResponse.equals("OKDEDUCT")){
                    System.out.println(" atlast done.");
                }
            }
        }    
//            Float.parseFloat(rescropPrev.getAppliedAmount()) will be added back to shopresource
//            amtapplied will again be deducted.
//            if (Float.parseFloat(rescropPrev.getAppliedAmount()) < amtapplied) {
//                shopResResponse = calcShopResAmtDeduct(amtapplied
//                        - Float.parseFloat(rescropPrev.getAppliedAmount()), selectedRes);
//            }
//            if (Float.parseFloat(rescropPrev.getAppliedAmount()) > amtapplied) {
//                shopResResponse = calcShopResAmtAdd(Float.parseFloat(
//                        rescropPrev.getAppliedAmount()) - amtapplied, selectedRes);
//            }
//            shopResAdd = calcShopResAmtAdd(Float.parseFloat(rescropPrev.getAppliedAmount()),
//                    selectedRes);
//            shopResDeduct = calcShopResAmtDeduct(amtapplied, selectedRes);
//            if ((Float.parseFloat(rescropPrev.getAppliedAmount()) - amtapplied) < 0) {
//                shopResResponse = calcShopResAmtDeduct(amtapplied
//                        - Float.parseFloat(rescropPrev.getAppliedAmount()), selectedRes);
//            }
//            if ((Float.parseFloat(rescropPrev.getAppliedAmount()) - amtapplied) > 0) {
//                shopResResponse = calcShopResAmtAdd(Float.parseFloat(
//                        rescropPrev.getAppliedAmount()) - amtapplied, selectedRes);
//            }
//        }
        /*if resource id is changed then both resource id and resource amount is updated in 
        cropresource and farmresource(remainingAmt field) table. */
//        if (selectedRes.equals(rescropPrev.getResourceId())) {
//            if (amtapplied == Float.parseFloat(rescropPrev.getAppliedAmount())) {
//                remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt());
//                resCropAppliedCost= resCropAppliedCostPrev;
//            } else {
//                remainingAmt = Float.parseFloat(farmresPrev.getAvailableAmt())
//                        - amtapplied
//                        + Float.parseFloat(rescropPrev.getAppliedAmount());
//                if(Float.parseFloat(rescropPrev.getAppliedAmount())< amtapplied){
//                    shopResResponse = calcShopResAmtDeduct(amtapplied-
//                            Float.parseFloat(rescropPrev.getAppliedAmount()), selectedRes);
//                }
//                if((Float.parseFloat(rescropPrev.getAppliedAmount())-amtapplied)>0){
//                    shopResResponse = calcShopResAmtAdd(Float.parseFloat(
//                            rescropPrev.getAppliedAmount())-amtapplied , selectedRes);
//                }
//            }
//        } else {
//            remainingAmt = Float.parseFloat(amount) - amtapplied;
//            remainingPrevAmt = Float.parseFloat(farmresPrev.getAvailableAmt())
//                    + Float.parseFloat(rescropPrev.getAppliedAmount());
//            shopResNewid = calcShopResAmtAdd(Float.parseFloat(
//                    rescropPrev.getAppliedAmount()), rescropPrev.getResourceId());
//            shopResNewid = calcShopResAmtDeduct(amtapplied, selectedRes);
//        } 
        
        if (amtapplied == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Apply non-zero amount of resource.",
                    "Apply non-zero amount of resource.");
            f.addMessage("amtapplied", message);
            return redirectUrl;
        } else {
//            float remainingAmt = Float.parseFloat(amount) - amtapplied +amtappliedprev;
            if (remainingAmt == 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Strored resource would be finished after this application.",
                        "Stored resource would be finished after this application.");
                f.addMessage("amtapplied", message);
            }
            if (remainingAmt < 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resource cannot be applied.",
                        "Stored resource is less than applied resource.");
                f.addMessage("amtapplied", message);
                return redirectUrl;
            }
        }
        
        int sqlFlag = 0;
//        ResourceCropDTO rescropRecord = masterDataService.getResCropForId(selectedRescrop);
        rescropRecord.setResourceId(selectedRes);// for resourcecrop table
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        rescropRecord.setAppliedAmount(String.format("%.2f", amtapplied));
        rescropRecord.setApplicationDt(sdf.format(applyDt));
        
        FarmresourceDTO resourceRec = masterDataService.
                getResourceNameForId(Integer.parseInt(selectedRes)); // for farmresouce table
        resourceRec.setAvailableAmt(String.format("%.2f", remainingAmt));
//        FarmresourceDTO resourceRecPrev = new FarmresourceDTO();
//        if (remainingPrevAmt > 0) {//## implies that resourceid is changed
//            //###      correction for previously selected resource
//            resourceRecPrev = masterDataService.
//                    getResourceNameForId(Integer.parseInt(rescropPrev.getResourceId()));
//            resourceRecPrev.setAvailableAmt(String.format("%.2f", remainingPrevAmt));
//        }
        
        int rescropres = masterDataService.editResCropRecord(rescropRecord);
        
        if (rescropres == SUCCESS) {
            sqlFlag = sqlFlag + 1;
        } else {
            if (rescropres == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure" 
                                                , "Resourcecrop record doesn't exist");
                f.addMessage(null, message);
            }
            if (rescropres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        Integer.toString(DB_SEVERE));
                f.addMessage(null, message);
            } 
            return redirectUrl;
        }
        
        
        if (sqlFlag == 1) {
            int resres = masterDataService.editResource(resourceRec);
            if (resres == SUCCESS) {
//This is if the resource(selectedRes) is NOT updated from existing one
                    sqlFlag = sqlFlag + 1;
//                This is if the resource(selectedRes) is updated from existing one
//                if (remainingPrevAmt > 0) {
//                    int resprev = masterDataService.editResource(resourceRecPrev);
//                    if (resprev == SUCCESS) {
//                        sqlFlag = sqlFlag + 1;
//                    } else {
//                        if (resprev == DB_NON_EXISTING) {
//                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resource record does not exist", Integer.toString(DB_NON_EXISTING));
//                            f.addMessage(null, message);
//                        }
//                        if (resprev == DB_SEVERE) {
//                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resource update", Integer.toString(DB_SEVERE));
//                            f.addMessage(null, message);
//                        }
//                        resres = masterDataService.editResCropRecord(rescropPrev);
//
//                        if (resres == DB_SEVERE) {
//                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resourcecrop table update",
//                                    Integer.toString(DB_SEVERE));
//                            f.addMessage(null, message);
//                        }
//                        return redirectUrl;
//                    }
//                } else {
////                    This is if the resource(selectedRes) is NOT updated from existing one
//                    sqlFlag = sqlFlag + 1;
//                }
                
            } else {
                if (resres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Resource record does not exist", Integer.toString(DB_NON_EXISTING));
                    f.addMessage(null, message);
                }
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resource update", Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                resres = masterDataService.editResCropRecord(rescropPrev);

                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure on resourcecrop table update",
                            Integer.toString(DB_SEVERE));
                    f.addMessage(null, message);
                }
                return redirectUrl;
            }
        }
        if (sqlFlag == 2) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Resource updated successfully with application ID=" + rescropRecord.getApplicationId());
            f.addMessage(null, message);
        }
        return redirectUrl;        
    }
    
    public String calcShopResAmtDeduct(float quantityApplied, String applId, String resId) throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        String redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
        List<ShopResDTO> shopResListResid = masterDataService.getShopResForResid(resId);

        ShopResDTO shopResRec = new ShopResDTO();
        float appliedQuantity = quantityApplied;
        for (int i = 0; i < shopResListResid.size(); i++) {
            float shopResStock = Float.parseFloat(shopResListResid.get(i).getStockPerRate());
            float shopResRate = Float.parseFloat(shopResListResid.get(i).getRate());
            if ((Integer.parseInt(shopResListResid.get(i).getResAppId())== 
                    Integer.parseInt(applId))
                ||((Integer.parseInt(shopResListResid.get(i).getResAppId())==0||
                    shopResListResid.get(i).getResAppId() == null) && appliedQuantity > 0))
            {
                shopResRec.setId(shopResListResid.get(i).getId());
                shopResRec.setRate(shopResListResid.get(i).getRate());
                shopResRec.setResRateDate(shopResListResid.get(i).getResRateDate());
                shopResRec.setResourceId(resId);
                shopResRec.setShopId(shopResListResid.get(i).getShopId());
                shopResRec.setResAppId(applId);
                if (appliedQuantity <= shopResStock) {
                //in this case the applied resource is deducted from the stock(consumed
                //from the stock completely and stock is not more than 0, hence break from this loop.
                    shopResStock = shopResStock - appliedQuantity;                
                    resCropAppliedCost = resCropAppliedCost + (appliedQuantity * shopResRate);                
                    shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
                    shopResRec.setAmtApplied(String.format("%.2f", appliedQuantity));
                    appliedQuantity = 0;
                    if (Integer.parseInt(shopResListResid.get(i).getResAppId())
                            == Integer.parseInt(applId)) {
                        int shopres = masterDataService.editShopForRes(shopResRec);
                        if (shopres != SUCCESS) {
//                    resCropAppliedCost = 0;
                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                    "shopresource record could not be updated");
                            f.addMessage(null, message);
                            return redirectUrl;
                        }
                    }
                    if ((Integer.parseInt(shopResListResid.get(i).getResAppId()) == 0
                            || shopResListResid.get(i).getResAppId() == null) && appliedQuantity > 0) {
                        shopResRec.setId(shopResListResid.get(i).getId());
                        shopResRec.setRate(shopResListResid.get(i).getRate());
                        shopResRec.setResRateDate(shopResListResid.get(i).getResRateDate());
                        shopResRec.setResourceId(resId);
                        shopResRec.setShopId(shopResListResid.get(i).getShopId());
                        shopResRec.setResAppId(applId);
                        int shopres = masterDataService.addShopResource(shopResRec);
                        if (shopres != SUCCESS) {
//                    resCropAppliedCost = 0;
                            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                    "shopresource record could not be updated");
                            f.addMessage(null, message);
                            return redirectUrl;
                        }
                    }
                    break;
                }
                if (appliedQuantity > shopResStock) {
                    resCropAppliedCost = resCropAppliedCost + (shopResStock * shopResRate);
                    appliedQuantity = appliedQuantity - shopResStock;
                    shopResRec.setAmtApplied(String.format("%.2f", shopResStock));
                    shopResStock = 0;
                    shopResRec.setStockPerRate(String.format("%.2f", shopResStock));
                }
                if (Integer.parseInt(shopResListResid.get(i).getResAppId())
                        == Integer.parseInt(applId)) {
                    int shopres = masterDataService.editShopForRes(shopResRec);
                    if (shopres != SUCCESS) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "shopresource record could not be updated");
                        f.addMessage(null, message);
                        return redirectUrl;
                    }
                }
                if ((Integer.parseInt(shopResListResid.get(i).getResAppId()) == 0
                        || shopResListResid.get(i).getResAppId() == null) && appliedQuantity > 0) {
                    shopResRec.setId(shopResListResid.get(i).getId());
                    shopResRec.setRate(shopResListResid.get(i).getRate());
                    shopResRec.setResRateDate(shopResListResid.get(i).getResRateDate());
                    shopResRec.setResourceId(resId);
                    shopResRec.setShopId(shopResListResid.get(i).getShopId());
                    shopResRec.setResAppId(applId);
                    int shopres = masterDataService.addShopResource(shopResRec);
                    if (shopres != SUCCESS) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                                "shopresource record could not be updated");
                        f.addMessage(null, message);
                        return redirectUrl;
                    }
                }
            }
            
        }
        return "OKDEDUCT";
    }
    
    public String calcShopResAmtAdd(String applyid, String resId) throws NamingException{
        MasterDataServices masterDataService = new MasterDataServices();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        String redirectUrl = "/secured/harvest/activehrvstlst?faces-redirect=true";
//        float appliedQuantity = quantityApplied;
        List<ShopResDTO> listperApplyId = masterDataService.getShopResForApplyRes(applyid, resId);
        ShopResDTO recToUpd = new ShopResDTO();
        for (int i = 0; i < listperApplyId.size(); i++) {
            recToUpd.setId(listperApplyId.get(i).getId());
            recToUpd.setRate(listperApplyId.get(i).getRate());
            recToUpd.setResRateDate(listperApplyId.get(i).getResRateDate());
            recToUpd.setResourceId(resId);
            recToUpd.setShopId(listperApplyId.get(i).getShopId());
            recToUpd.setResAppId(applyid);
            
            recToUpd.setAmtApplied(String.format("%.2f",0.00));
            float stock = Float.parseFloat(listperApplyId.get(i).getStockPerRate())+
                    Float.parseFloat(listperApplyId.get(i).getAmtApplied());
            recToUpd.setStockPerRate(String.format("%.2f", stock));
            int shopres = masterDataService.editShopForRes(recToUpd);
            if (shopres != SUCCESS) {
                resCropAppliedCost = 0;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure",
                        "shopresource record could not be updated");
                f.addMessage(null, message);
                return redirectUrl;
            }            
        }
        return "OKADD";
    }

    public float getResCropAppliedCost() {
        return resCropAppliedCost;
    }

    public void setResCropAppliedCost(float resCropAppliedCost) {
        this.resCropAppliedCost = resCropAppliedCost;
    }
    
    public String goToAppliedRes() {
        String redirectUrl = "/secured/harvest/appliedresperharvest?faces-redirect=true&appliedHarvest=" + rescropPrev.getHarvestId();
        return redirectUrl; 
    }

    public String getSelectedRes() {
        return selectedRes;
    }

    public void setSelectedRes(String selectedRes) {
        this.selectedRes = selectedRes;
    }

    public String getSelectedRescrop() {
        return selectedRescrop;
    }

    public void setSelectedRescrop(String selectedRescrop) {
        this.selectedRescrop = selectedRescrop;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCropcat() {
        return cropcat;
    }

    public void setCropcat(String cropcat) {
        this.cropcat = cropcat;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }    

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getAmtapplied() {
        return amtapplied;
    }

    public void setAmtapplied(float amtapplied) {
        this.amtapplied = amtapplied;
    }

    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }

    public FarmresourceDTO getFarmresPrev() {
        return farmresPrev;
    }

    public void setFarmresPrev(FarmresourceDTO farmresPrev) {
        this.farmresPrev = farmresPrev;
    }

    public ResourceCropDTO getRescropPrev() {
        return rescropPrev;
    }

    public void setRescropPrev(ResourceCropDTO rescropPrev) {
        this.rescropPrev = rescropPrev;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }
    
    
}
