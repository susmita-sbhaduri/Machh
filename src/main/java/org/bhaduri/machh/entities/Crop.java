/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    @NamedQuery(name = "Crop.findByCropid", query = "SELECT c FROM Crop c WHERE c.cropid = :cropid"),
    @NamedQuery(name = "Crop.findByCropcategory", query = "SELECT c FROM Crop c WHERE c.cropcategory = :cropcategory"),
    @NamedQuery(name = "Crop.findByCrop", query = "SELECT c FROM Crop c WHERE c.crop = :crop"),
    @NamedQuery(name = "Crop.findByDetails", query = "SELECT c FROM Crop c WHERE c.details = :details")})
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cropid")
    private Integer cropid;
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
    @Size(max = 45)
    @Column(name = "details")
    private String details;

    public Crop() {
    }

    public Crop(Integer cropid) {
        this.cropid = cropid;
    }

    public Crop(Integer cropid, String cropcategory, String crop) {
        this.cropid = cropid;
        this.cropcategory = cropcategory;
        this.crop = crop;
    }

    public Integer getCropid() {
        return cropid;
    }

    public void setCropid(Integer cropid) {
        this.cropid = cropid;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cropid != null ? cropid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crop)) {
            return false;
        }
        Crop other = (Crop) object;
        if ((this.cropid == null && other.cropid != null) || (this.cropid != null && !this.cropid.equals(other.cropid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Crop[ cropid=" + cropid + " ]";
    }
    
}
