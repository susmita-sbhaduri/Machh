/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.CropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_DUPLICATE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "cropAdd")
@ViewScoped
public class CropAdd implements Serializable {

    private CropDTO cropAddBean;
    private Date dateStringSow;
    private Date dateStringHarvest;
    private String cropcategory;
    private String cropname;
    private String details;
    
    @PostConstruct
    public void init() {       
        dateStringSow = new Date();
        dateStringHarvest = new Date();
    }
    public CropAdd() {
        System.out.println("blah.");
    }
//    public String save() throws NamingException {
//        String redirectUrl = "";
//        FacesMessage message = null;
//        FacesContext f = FacesContext.getCurrentInstance();
//        f.getExternalContext().getFlash().setKeepMessages(true);
//        if (cropcategory == null || cropcategory.trim().isEmpty()) {
//            
//            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Crop Category is required.", 
//                    "Crop Category is required.");
//            f.addMessage("cropcat", message);           
//            redirectUrl = "/secured/crop/croplist?faces-redirect=true";
//        } else {
//            if (cropname == null || cropname.trim().isEmpty()) {
//                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Crop Name is required.", 
//                "Crop Name is required.");
//                f.addMessage("cropname", message);
////              
//                redirectUrl = "/secured/crop/croplist?faces-redirect=true";
//            } else {
//                cropAddBean = new CropDTO();
//                cropAddBean.setCropCategory(cropcategory);
//                cropAddBean.setCropName(cropname);
//                cropAddBean.setDetails(details);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                cropAddBean.setSowingDate(sdf.format(dateStringSow));
//                cropAddBean.setHarvestDate(sdf.format(dateStringHarvest));
//                MasterDataServices masterDataService = new MasterDataServices();
//                int response = masterDataService.addCrop(cropAddBean);                
//                
//                if (response == SUCCESS) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Integer.toString(SUCCESS));
//                    redirectUrl = "/secured/crop/croplist?faces-redirect=true";
//                } else {
//                    if (response == DB_DUPLICATE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_DUPLICATE));
//                        redirectUrl = "/secured/crop/croplist?faces-redirect=true";
//                    }
//                    if (response == DB_SEVERE) {
//                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
//                        redirectUrl = "/secured/crop/croplist?faces-redirect=true";
//                    }
//                }
//                f.addMessage(null, message);
//            }
//        }
//        
//        return redirectUrl;
//    }

    public CropDTO getCropAddBean() {
        return cropAddBean;
    }

    public void setCropAddBean(CropDTO cropAddBean) {
        this.cropAddBean = cropAddBean;
    }
   
    
    public Date getDateStringSow() {
        return dateStringSow;
    }

    public void setDateStringSow(Date dateStringSow) {
        this.dateStringSow = dateStringSow;
    }

    public Date getDateStringHarvest() {
        return dateStringHarvest;
    }

    public void setDateStringHarvest(Date dateStringHarvest) {
        this.dateStringHarvest = dateStringHarvest;
    }

    public String getCropcategory() {
        return cropcategory;
    }

    public void setCropcategory(String cropcategory) {
        this.cropcategory = cropcategory;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    
}
