/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package org.bhaduri.machh.crop;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import javax.naming.NamingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.bhaduri.machh.DTO.CropDTO;
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
    
    @PostConstruct
    public void init() {
        try {
            fillSiteNames();
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
}
