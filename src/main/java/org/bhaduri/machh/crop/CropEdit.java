/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.CropDTO;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_NON_EXISTING;
import static org.bhaduri.machh.DTO.MachhResponseCodes.DB_SEVERE;
import static org.bhaduri.machh.DTO.MachhResponseCodes.SUCCESS;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "cropEdit")
@ViewScoped
public class CropEdit implements Serializable {
    private String cropcatEd;
    private String cropnameEd;
    private CropDTO cropEditBean;
    private Date dateStringSow;
    private Date dateStringHarvest;

    

    public void fillCropValues() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        cropEditBean = masterDataService.getCropsPerPk(cropcatEd, cropnameEd); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateStringSow = sdf.parse(cropEditBean.getSowingDate());
            dateStringHarvest = sdf.parse(cropEditBean.getHarvestDate());
        } catch (ParseException ex) {
            Logger.getLogger(CropEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String save() throws NamingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cropEditBean.setSowingDate(sdf.format(dateStringSow));
        cropEditBean.setHarvestDate(sdf.format(dateStringHarvest));
        MasterDataServices masterDataService = new MasterDataServices();
        int response = masterDataService.editCrop(cropEditBean);
        FacesMessage message = null;
        String redirectUrl = "";
        if(response==SUCCESS){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", Integer.toString(SUCCESS));
            redirectUrl = "/secured/crop/croplist?faces-redirect=true";
        } else {
            if (response == DB_NON_EXISTING) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_NON_EXISTING));
                redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + cropcatEd+ "&cropnameEd=" + cropnameEd;
            }
            if (response == DB_SEVERE) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Failure", Integer.toString(DB_SEVERE));
                redirectUrl = "/secured/crop/cropedit?faces-redirect=true&cropcatEd=" + cropcatEd+ "&cropnameEd=" + cropnameEd;              
            }
        }
        FacesContext f = FacesContext.getCurrentInstance();
        f.getExternalContext().getFlash().setKeepMessages(true);
        f.addMessage(null, message);
        return redirectUrl;
    }
    
    public String testAction() {
        System.out.println("Test action called");
        return null; // Just to see if this gets called
    }

    public CropEdit() {
        System.out.println("PostConstruct: Bean initialized");
    }

    public String getCropcatEd() {
        return cropcatEd;
    }

    public void setCropcatEd(String cropcatEd) {
        this.cropcatEd = cropcatEd;
    }

    public String getCropnameEd() {
        return cropnameEd;
    }

    public void setCropnameEd(String cropnameEd) {
        this.cropnameEd = cropnameEd;
    }

    public CropDTO getCropEditBean() {
        return cropEditBean;
    }

    public void setCropEditBean(CropDTO cropEditBean) {
        this.cropEditBean = cropEditBean;
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

        
}
