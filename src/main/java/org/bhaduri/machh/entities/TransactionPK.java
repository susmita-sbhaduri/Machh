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
public class TransactionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "resourcetype")
    private String resourcetype;
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

    public TransactionPK() {
    }

    public TransactionPK(Date date, String resourcetype, String cropcategory, String crop) {
        this.date = date;
        this.resourcetype = resourcetype;
        this.cropcategory = cropcategory;
        this.crop = crop;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
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
        hash += (date != null ? date.hashCode() : 0);
        hash += (resourcetype != null ? resourcetype.hashCode() : 0);
        hash += (cropcategory != null ? cropcategory.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionPK)) {
            return false;
        }
        TransactionPK other = (TransactionPK) object;
        if ((this.date == null && other.date != null) || (this.date != null && !this.date.equals(other.date))) {
            return false;
        }
        if ((this.resourcetype == null && other.resourcetype != null) || (this.resourcetype != null && !this.resourcetype.equals(other.resourcetype))) {
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
        return "org.bhaduri.machh.entities.TransactionPK[ date=" + date + ", resourcetype=" + resourcetype + ", cropcategory=" + cropcategory + ", crop=" + crop + " ]";
    }
    
}
