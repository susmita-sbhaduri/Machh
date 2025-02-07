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
public class CropPK implements Serializable {

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

    public CropPK() {
    }

    public CropPK(String cropcategory, String crop) {
        this.cropcategory = cropcategory;
        this.crop = crop;
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
        hash += (cropcategory != null ? cropcategory.hashCode() : 0);
        hash += (crop != null ? crop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CropPK)) {
            return false;
        }
        CropPK other = (CropPK) object;
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
        return "org.bhaduri.machh.entities.CropPK[ cropcategory=" + cropcategory + ", crop=" + crop + " ]";
    }
    
}
