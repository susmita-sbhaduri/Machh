/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import org.bhaduri.machh.services.MasterDataServices;

/**
 *
 * @author sb
 */
@Named(value = "newCropSow")
@ViewScoped
public class NewCropSow implements Serializable {
    private String siteid;   
    private List<String> sites;
    private String cropcat;   
    private List<String> cropcats;
    private String cropname;
    private List<String> cropnames;
    private Date dateStringSow;
    
    @PostConstruct
    public void init() {
        try {
            fillSiteNames();
            fillcroCats();
            dateStringSow = new Date();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public NewCropSow() {
        System.out.println("Test action called");
    }
    
    public void fillSiteNames() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        sites = masterDataService.getSiteNames();        
    }
    
    public void fillcroCats() throws NamingException {
        MasterDataServices masterDataService = new MasterDataServices();
        cropcats = masterDataService.getCropCat();        
    }
    
    
    
    public void onSiteChange() throws NamingException {
        
        MasterDataServices masterDataService = new MasterDataServices();
        List<String> cropnamesforcat = masterDataService.getCropNameForCat(cropcat);
        for (int i = 0; i < cropnamesforcat.size(); i++) {
            cropnames.add(cropnamesforcat.get(i));
        }
    }
     public void save() throws NamingException {
         System.out.println("Test action called");
     }
    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }
    public List<String> getSites() {
        return sites;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }

    public String getCropcat() {
        return cropcat;
    }

    public void setCropcat(String cropcat) {
        this.cropcat = cropcat;
    }

    public List<String> getCropcats() {
        return cropcats;
    }

    public void setCropcats(List<String> cropcats) {
        this.cropcats = cropcats;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public List<String> getCropnames() {
        return cropnames;
    }

    public void setCropnames(List<String> cropnames) {
        this.cropnames = cropnames;
    }

    public Date getDateStringSow() {
        return dateStringSow;
    }

    public void setDateStringSow(Date dateStringSow) {
        this.dateStringSow = dateStringSow;
    }
    
    
}
