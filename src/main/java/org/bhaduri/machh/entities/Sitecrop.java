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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "sitecrop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sitecrop.findAll", query = "SELECT s FROM Sitecrop s"),
    @NamedQuery(name = "Sitecrop.findBySiteid", query = "SELECT s FROM Sitecrop s WHERE s.sitecropPK.siteid = :siteid"),
    @NamedQuery(name = "Sitecrop.findByCrop", query = "SELECT s FROM Sitecrop s WHERE s.sitecropPK.crop = :crop"),
    @NamedQuery(name = "Sitecrop.findBySowingdt", query = "SELECT s FROM Sitecrop s WHERE s.sitecropPK.sowingdt = :sowingdt"),
    @NamedQuery(name = "Sitecrop.findByHarvestingdt", query = "SELECT s FROM Sitecrop s WHERE s.harvestingdt = :harvestingdt")})
public class Sitecrop implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SitecropPK sitecropPK;
    @Column(name = "harvestingdt")
    @Temporal(TemporalType.DATE)
    private Date harvestingdt;

    public Sitecrop() {
    }

    public Sitecrop(SitecropPK sitecropPK) {
        this.sitecropPK = sitecropPK;
    }

    public Sitecrop(String siteid, String crop, Date sowingdt) {
        this.sitecropPK = new SitecropPK(siteid, crop, sowingdt);
    }

    public SitecropPK getSitecropPK() {
        return sitecropPK;
    }

    public void setSitecropPK(SitecropPK sitecropPK) {
        this.sitecropPK = sitecropPK;
    }

    public Date getHarvestingdt() {
        return harvestingdt;
    }

    public void setHarvestingdt(Date harvestingdt) {
        this.harvestingdt = harvestingdt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sitecropPK != null ? sitecropPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sitecrop)) {
            return false;
        }
        Sitecrop other = (Sitecrop) object;
        if ((this.sitecropPK == null && other.sitecropPK != null) || (this.sitecropPK != null && !this.sitecropPK.equals(other.sitecropPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Sitecrop[ sitecropPK=" + sitecropPK + " ]";
    }
    
}
