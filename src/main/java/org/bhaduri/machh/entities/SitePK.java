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
public class SitePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id")
    private String id;
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

    public SitePK() {
    }

    public SitePK(String id, String cropcategory, String crop) {
        this.id = id;
        this.cropcategory = cropcategory;
        this.crop = crop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        hash += (id != null ? id.hashCode() : 0);
        hash += (cropcategory != null ? cropcategory.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SitePK)) {
            return false;
        }
        SitePK other = (SitePK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
        return "org.bhaduri.machh.entities.SitePK[ id=" + id + ", cropcategory=" + cropcategory + ", crop=" + crop + " ]";
    }
    
}
