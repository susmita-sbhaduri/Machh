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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.ExpenseDTO;
import org.bhaduri.machh.DTO.FarmresourceDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.DTO.ResAcquireDTO;
import org.bhaduri.machh.DTO.ShopResDTO;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "acquireResource")
@ViewScoped
public class AcquireResource implements Serializable {

    private boolean saveDisabled = true;
    private String selectedRes;
    private String selectedShop;
    private float rate;
    private String unit;
    private List<ShopResDTO> shopForSelectedRes;
    private List<FarmresourceDTO> existingresources;
    private ShopResDTO selectedShopRes;
    private float amount;
    private Date purchaseDt = new Date();
    private String comments;

    public AcquireResource() {
    }

    public void fillResourceValues() throws NamingException {

        MasterDataServices masterDataService = new MasterDataServices();
        shopForSelectedRes = masterDataService.getShopResForResid(selectedRes);
        
        
    }

    public void onShopResSelect() throws NamingException {
        System.out.println("No crop categories are found." + selectedShop);
        MasterDataServices masterDataService = new MasterDataServices();
        selectedShopRes = masterDataService.getResShopForPk(selectedRes, selectedShop);
//        rate = Float.parseFloat(selectedShopRes.getRate());
        rate = 0;
        unit = masterDataService.getResourceNameForId(Integer.parseInt(selectedRes)).getUnit();
//        String redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="+ selectedShopRes.getResourceId();
//        FacesMessage message;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        if(Float.parseFloat(selectedShopRes.getRate())==0){
//           message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
//                    "This resource is getting acquired for the first time.");
//            f.addMessage("rate", message); 
//            return redirectUrl;
//        } else return null; 
    }

    public String goToReviewRes() {
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        if (selectedShop == null || selectedShop.trim().isEmpty()) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Select one shop.");
            f.addMessage("shopid", message);
            return null;
        }
        
        if(rate==0){
           message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide a non-zero rate.");
            f.addMessage("rate", message); 
            return null;
        }
        
        if (amount == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failure",
                    "Provide non-zero purchase amount.");
            f.addMessage("amount", message);
            return null;
        }
        float calculatedAmount = rate*amount;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Total cost for "+selectedShopRes.getResourceName()
                +" for shop "+selectedShopRes.getShopName(),
                    "=Rs."+String.format("%.2f", calculatedAmount));
        
        f.addMessage(null, message);
        saveDisabled = false; // Enable the save button
        return null;
    }

    public String goToSaveRes() throws NamingException {
        int sqlFlag = 0;
//        String redirectUrl = "/secured/resource/maintainresource?faces-redirect=true";
        String redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
                        + selectedShopRes.getResourceId();
        FacesMessage message;
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);

        MasterDataServices masterDataService = new MasterDataServices();
        
//        construction of shopresource record
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ShopResDTO shopResRec = new ShopResDTO();
        int shopresflag = 0;
        shopResRec.setShopId(selectedShopRes.getShopId());
        shopResRec.setShopName(selectedShopRes.getShopName());
        shopResRec.setResourceId(selectedShopRes.getResourceId());
        shopResRec.setResourceName(selectedShopRes.getResourceName());
        shopResRec.setRate(String.format("%.2f", rate));
        shopResRec.setResRateDate(sdf.format(purchaseDt));
        shopResRec.setStockPerRate(String.format("%.2f", amount));
        if (Float.parseFloat(selectedShopRes.getRate())==0){ 
            shopResRec.setId(selectedShopRes.getId());
           

            shopresflag = 1;
        } else {            
            int shopresid = masterDataService.getMaxIdForShopRes();
            if (shopresid == 0 || shopresid == DB_SEVERE) {
                shopresid = 1;
            } else {
                shopresid = shopresid + 1;
            }
            shopResRec.setId(String.valueOf(shopresid));
            shopresflag =2;
        }
//        contruction of resourceacquire record
        ResAcquireDTO resAcquireRec = new ResAcquireDTO();        
        int acquireid = masterDataService.getNextIdForResAquire();
        if (acquireid == 0 || acquireid == DB_SEVERE) {
            resAcquireRec.setAcquireId("1");
        } else {
            resAcquireRec.setAcquireId(String.valueOf(acquireid + 1));
        }
        
        resAcquireRec.setResoureId(selectedShopRes.getResourceId());
        resAcquireRec.setAmount(String.format("%.2f", amount));
        resAcquireRec.setAcquireDate(sdf.format(purchaseDt));

//        contruction of expense record
        ExpenseDTO expenseRec = new ExpenseDTO();
        int expenseid = masterDataService.getNextIdForExpense();
        if (expenseid == 0 || expenseid == DB_SEVERE) {
            expenseRec.setExpenseId("1");
        } else {
            expenseRec.setExpenseId(String.valueOf(expenseid + 1));
        }
        expenseRec.setDate(sdf.format(purchaseDt));
        expenseRec.setExpenseType("RES");
        expenseRec.setExpenseRefId(resAcquireRec.getAcquireId()); //######resourcecrop acq id
        float calculatedAmount = rate * amount;
        expenseRec.setExpenditure(String.format("%.2f", calculatedAmount));
        expenseRec.setCommString(comments);


//        contruction of farmresource record
        FarmresourceDTO resourceRec = masterDataService.getResourceNameForId(Integer.parseInt(
                selectedShopRes.getResourceId()));
        float amountAcquired = amount + Float.parseFloat(resourceRec.getAvailableAmt());
        resourceRec.setAvailableAmt(String.format("%.2f", amountAcquired));

        int acqres = masterDataService.addAcquireResource(resAcquireRec);
        if (acqres == SUCCESS) {
            sqlFlag = sqlFlag + 1;
        } else {
            if (acqres == DB_DUPLICATE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        "Duplicate record error for resourceacquire table");
                f.addMessage(null, message);
            }
            if (acqres == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                        "Failure on insert in resourceacquire table");
                f.addMessage(null, message);
            }

//            redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
//                        + selectedShopRes.getResourceId();
//            return redirectUrl;
        }

        if (sqlFlag == 1) {

            int expres = masterDataService.addExpenseRecord(expenseRec);
            if (expres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (expres == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "Duplicate record error for expense table");
                    f.addMessage(null, message);
                }
                if (expres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure"
                            , "Failure on insert in expense table");
                    f.addMessage(null, message);
                }
                int delacq = masterDataService.delAcquireResource(resAcquireRec);
                if (delacq == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "acquireresource record could not be deleted");
                    f.addMessage(null, message);
                }
//                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
//                        + selectedShopRes.getResourceId();
//                return redirectUrl;
            }
        }

        if (sqlFlag == 2) {

            int resres = masterDataService.editResource(resourceRec);
            if (resres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (resres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "This resource record does not exist.");
                    f.addMessage(null, message);
                }
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "Failure on edit in resource table");
                    f.addMessage(null, message);
                }
                
                int delacq = masterDataService.delAcquireResource(resAcquireRec);
                if (delacq == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "acquireresource record could not be deleted");
                    f.addMessage(null, message);
                }
                int delexpense = masterDataService.delExpenseRecord(expenseRec);
                if (delexpense == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "expense record could not be deleted");
                    f.addMessage(null, message);
                }
//                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
//                        + selectedShopRes.getResourceId();
//                return redirectUrl;
            }
        }
        
        if (sqlFlag == 3) {
            
            int shopres;
            if(shopresflag==1){
                shopres = masterDataService.editShopForRes(shopResRec);
            } else {
                shopres = masterDataService.addShopResource(shopResRec);
            }
            
            if (shopres == SUCCESS) {
                sqlFlag = sqlFlag + 1;
            } else {
                if (shopres == DB_NON_EXISTING) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "This shopresource record does not exist.");
                    f.addMessage(null, message);
                }
                if (shopres == DB_DUPLICATE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "This shopresource record does not exist.");
                    f.addMessage(null, message);
                }
                if (shopres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "Failure on edit in shopresource table");
                    f.addMessage(null, message);
                }
                
                int delacq = masterDataService.delAcquireResource(resAcquireRec);
                if (delacq == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "acquireresource record could not be deleted");
                    f.addMessage(null, message);
                }
                int delexpense = masterDataService.delExpenseRecord(expenseRec);
                if (delexpense == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "expense record could not be deleted");
                    f.addMessage(null, message);
                }
                amountAcquired = Float.parseFloat(resourceRec.getAvailableAmt())-amount;
                resourceRec.setAvailableAmt(String.format("%.2f", amountAcquired));
                int resres = masterDataService.editResource(resourceRec);
                if (resres == DB_SEVERE) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", 
                            "farmresource record could not be corrected");
                    f.addMessage(null, message);
                }
//                redirectUrl = "/secured/resource/acquireresource?faces-redirect=true&selectedRes="
//                        + selectedShopRes.getResourceId();
//                return redirectUrl;
            }
        }
        
        if (sqlFlag==4) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", 
                    "Resource acquired successfully");
            f.addMessage(null, message);
        }
        return redirectUrl;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public ShopResDTO getSelectedShopRes() {
        return selectedShopRes;
    }

    public void setSelectedShopRes(ShopResDTO selectedShopRes) {
        this.selectedShopRes = selectedShopRes;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getPurchaseDt() {
        return purchaseDt;
    }

    public void setPurchaseDt(Date purchaseDt) {
        this.purchaseDt = purchaseDt;
    }

    public boolean isSaveDisabled() {
        return saveDisabled;
    }

    public void setSaveDisabled(boolean saveDisabled) {
        this.saveDisabled = saveDisabled;
    }

    
}
