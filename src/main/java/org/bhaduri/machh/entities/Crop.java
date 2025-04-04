/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "crop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crop.findAll", query = "SELECT c FROM Crop c"),
    @NamedQuery(name = "Crop.findByCropcategory", query = "SELECT c FROM Crop c WHERE c.cropPK.cropcategory = :cropcategory"),
    @NamedQuery(name = "Crop.findByCrop", query = "SELECT c FROM Crop c WHERE c.cropPK.crop = :crop"),
    @NamedQuery(name = "Crop.findByDetails", query = "SELECT c FROM Crop c WHERE c.details = :details")})
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CropPK cropPK;
    @Size(max = 45)
    @Column(name = "details")
    private String details;

    public Crop() {
    }

    public Crop(CropPK cropPK) {
        this.cropPK = cropPK;
    }

    public Crop(String cropcategory, String crop) {
        this.cropPK = new CropPK(cropcategory, crop);
    }

    public CropPK getCropPK() {
        return cropPK;
    }

    public void setCropPK(CropPK cropPK) {
        this.cropPK = cropPK;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cropPK != null ? cropPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crop)) {
            return false;
        }
        Crop other = (Crop) object;
        if ((this.cropPK == null && other.cropPK != null) || (this.cropPK != null && !this.cropPK.equals(other.cropPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Crop[ cropPK=" + cropPK + " ]";
    }
    
}
