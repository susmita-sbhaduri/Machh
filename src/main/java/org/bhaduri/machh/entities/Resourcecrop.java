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
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "resourcecrop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resourcecrop.findAll", query = "SELECT r FROM Resourcecrop r"),
    @NamedQuery(name = "Resourcecrop.findByResourcename", query = "SELECT r FROM Resourcecrop r WHERE r.resourcecropPK.resourcename = :resourcename"),
    @NamedQuery(name = "Resourcecrop.findByCrop", query = "SELECT r FROM Resourcecrop r WHERE r.resourcecropPK.crop = :crop"),
    @NamedQuery(name = "Resourcecrop.findByAquiredate", query = "SELECT r FROM Resourcecrop r WHERE r.resourcecropPK.aquiredate = :aquiredate"),
    @NamedQuery(name = "Resourcecrop.findByInitialstock", query = "SELECT r FROM Resourcecrop r WHERE r.initialstock = :initialstock"),
    @NamedQuery(name = "Resourcecrop.findByAvailablestock", query = "SELECT r FROM Resourcecrop r WHERE r.availablestock = :availablestock")})
public class Resourcecrop implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResourcecropPK resourcecropPK;
    @Size(max = 45)
    @Column(name = "initialstock")
    private String initialstock;
    @Size(max = 45)
    @Column(name = "availablestock")
    private String availablestock;

    public Resourcecrop() {
    }

    public Resourcecrop(ResourcecropPK resourcecropPK) {
        this.resourcecropPK = resourcecropPK;
    }

    public Resourcecrop(String resourcename, String crop, Date aquiredate) {
        this.resourcecropPK = new ResourcecropPK(resourcename, crop, aquiredate);
    }

    public ResourcecropPK getResourcecropPK() {
        return resourcecropPK;
    }

    public void setResourcecropPK(ResourcecropPK resourcecropPK) {
        this.resourcecropPK = resourcecropPK;
    }

    public String getInitialstock() {
        return initialstock;
    }

    public void setInitialstock(String initialstock) {
        this.initialstock = initialstock;
    }

    public String getAvailablestock() {
        return availablestock;
    }

    public void setAvailablestock(String availablestock) {
        this.availablestock = availablestock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcecropPK != null ? resourcecropPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resourcecrop)) {
            return false;
        }
        Resourcecrop other = (Resourcecrop) object;
        if ((this.resourcecropPK == null && other.resourcecropPK != null) || (this.resourcecropPK != null && !this.resourcecropPK.equals(other.resourcecropPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Resourcecrop[ resourcecropPK=" + resourcecropPK + " ]";
    }
    
}
