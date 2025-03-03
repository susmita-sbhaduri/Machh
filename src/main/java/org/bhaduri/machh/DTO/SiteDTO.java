/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;

/**
 *
 * @author sb
 */
public class SiteDTO implements Serializable {
    private String siteID;
    private String cropCategory;
    private String cropName;
    private String siteType;
    private String siteSizeSqft;
    private String siteSizeKatha;

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getCropCategory() {
        return cropCategory;
    }

    public void setCropCategory(String cropCategory) {
        this.cropCategory = cropCategory;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getSiteSizeSqft() {
        return siteSizeSqft;
    }

    public void setSiteSizeSqft(String siteSizeSqft) {
        this.siteSizeSqft = siteSizeSqft;
    }

    public String getSiteSizeKatha() {
        return siteSizeKatha;
    }

    public void setSiteSizeKatha(String siteSizeKatha) {
        this.siteSizeKatha = siteSizeKatha;
    }
    
    
}
