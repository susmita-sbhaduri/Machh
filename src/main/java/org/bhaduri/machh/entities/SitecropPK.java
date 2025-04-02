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
public class SitecropPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "siteid")
    private String siteid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "crop")
    private String crop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sowingdt")
    @Temporal(TemporalType.DATE)
    private Date sowingdt;

    public SitecropPK() {
    }

    public SitecropPK(String siteid, String crop, Date sowingdt) {
        this.siteid = siteid;
        this.crop = crop;
        this.sowingdt = sowingdt;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public Date getSowingdt() {
        return sowingdt;
    }

    public void setSowingdt(Date sowingdt) {
        this.sowingdt = sowingdt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siteid != null ? siteid.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        hash += (sowingdt != null ? sowingdt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SitecropPK)) {
            return false;
        }
        SitecropPK other = (SitecropPK) object;
        if ((this.siteid == null && other.siteid != null) || (this.siteid != null && !this.siteid.equals(other.siteid))) {
            return false;
        }
        if ((this.crop == null && other.crop != null) || (this.crop != null && !this.crop.equals(other.crop))) {
            return false;
        }
        if ((this.sowingdt == null && other.sowingdt != null) || (this.sowingdt != null && !this.sowingdt.equals(other.sowingdt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.SitecropPK[ siteid=" + siteid + ", crop=" + crop + ", sowingdt=" + sowingdt + " ]";
    }
    
}
