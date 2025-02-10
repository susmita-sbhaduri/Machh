/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author sb
 */
@Embeddable
public class ResourcePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "resourcecategory")
    private String resourcecategory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "resourcename")
    private String resourcename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cropcategory")
    private String cropcategory;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "crop")
    private String crop;

    public ResourcePK() {
    }

    public ResourcePK(String resourcecategory, String resourcename, String cropcategory, String crop) {
        this.resourcecategory = resourcecategory;
        this.resourcename = resourcename;
        this.cropcategory = cropcategory;
        this.crop = crop;
    }

    public String getResourcecategory() {
        return resourcecategory;
    }

    public void setResourcecategory(String resourcecategory) {
        this.resourcecategory = resourcecategory;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getCropcategory() {
        return cropcategory;
    }

    public void setCropcategory(String cropcategory) {
        this.cropcategory = cropcategory;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcecategory != null ? resourcecategory.hashCode() : 0);
        hash += (resourcename != null ? resourcename.hashCode() : 0);
        hash += (cropcategory != null ? cropcategory.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourcePK)) {
            return false;
        }
        ResourcePK other = (ResourcePK) object;
        if ((this.resourcecategory == null && other.resourcecategory != null) || (this.resourcecategory != null && !this.resourcecategory.equals(other.resourcecategory))) {
            return false;
        }
        if ((this.resourcename == null && other.resourcename != null) || (this.resourcename != null && !this.resourcename.equals(other.resourcename))) {
            return false;
        }
        if ((this.cropcategory == null && other.cropcategory != null) || (this.cropcategory != null && !this.cropcategory.equals(other.cropcategory))) {
            return false;
        }
        if ((this.crop == null && other.crop != null) || (this.crop != null && !this.crop.equals(other.crop))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.ResourcePK[ resourcecategory=" + resourcecategory + ", resourcename=" + resourcename + ", cropcategory=" + cropcategory + ", crop=" + crop + " ]";
    }
    
}
