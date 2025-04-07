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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "harvest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Harvest.findAll", query = "SELECT h FROM Harvest h"),
    @NamedQuery(name = "Harvest.findByHarvestid", query = "SELECT h FROM Harvest h WHERE h.harvestid = :harvestid"),
    @NamedQuery(name = "Harvest.findBySiteid", query = "SELECT h FROM Harvest h WHERE h.siteid = :siteid"),
    @NamedQuery(name = "Harvest.findByCrop", query = "SELECT h FROM Harvest h WHERE h.crop = :crop"),
    @NamedQuery(name = "Harvest.findBySowingdt", query = "SELECT h FROM Harvest h WHERE h.sowingdt = :sowingdt"),
    @NamedQuery(name = "Harvest.findByHarvestingdt", query = "SELECT h FROM Harvest h WHERE h.harvestingdt = :harvestingdt")})
public class Harvest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "harvestid")
    private Integer harvestid;
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
    @Column(name = "harvestingdt")
    @Temporal(TemporalType.DATE)
    private Date harvestingdt;

    public Harvest() {
    }

    public Harvest(Integer harvestid) {
        this.harvestid = harvestid;
    }

    public Harvest(Integer harvestid, String siteid, String crop, Date sowingdt) {
        this.harvestid = harvestid;
        this.siteid = siteid;
        this.crop = crop;
        this.sowingdt = sowingdt;
    }

    public Integer getHarvestid() {
        return harvestid;
    }

    public void setHarvestid(Integer harvestid) {
        this.harvestid = harvestid;
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

    public Date getHarvestingdt() {
        return harvestingdt;
    }

    public void setHarvestingdt(Date harvestingdt) {
        this.harvestingdt = harvestingdt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (harvestid != null ? harvestid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Harvest)) {
            return false;
        }
        Harvest other = (Harvest) object;
        if ((this.harvestid == null && other.harvestid != null) || (this.harvestid != null && !this.harvestid.equals(other.harvestid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Harvest[ harvestid=" + harvestid + " ]";
    }
    
}
