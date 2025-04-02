/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Embeddable
public class ResourcecropPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "resourcename")
    private String resourcename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "crop")
    private String crop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aquiredate")
    @Temporal(TemporalType.DATE)
    private Date aquiredate;

    public ResourcecropPK() {
    }

    public ResourcecropPK(String resourcename, String crop, Date aquiredate) {
        this.resourcename = resourcename;
        this.crop = crop;
        this.aquiredate = aquiredate;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Date getAquiredate() {
        return aquiredate;
    }

    public void setAquiredate(Date aquiredate) {
        this.aquiredate = aquiredate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcename != null ? resourcename.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        hash += (aquiredate != null ? aquiredate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResourcecropPK)) {
            return false;
        }
        ResourcecropPK other = (ResourcecropPK) object;
        if ((this.resourcename == null && other.resourcename != null) || (this.resourcename != null && !this.resourcename.equals(other.resourcename))) {
            return false;
        }
        if ((this.crop == null && other.crop != null) || (this.crop != null && !this.crop.equals(other.crop))) {
            return false;
        }
        if ((this.aquiredate == null && other.aquiredate != null) || (this.aquiredate != null && !this.aquiredate.equals(other.aquiredate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.ResourcecropPK[ resourcename=" + resourcename + ", crop=" + crop + ", aquiredate=" + aquiredate + " ]";
    }
    
}
