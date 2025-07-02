/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    @NamedQuery(name = "Harvest.findByCropid", query = "SELECT h FROM Harvest h WHERE h.cropid = :cropid"),
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
    @Column(name = "siteid")
    private int siteid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cropid")
    private int cropid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sowingdt")
    @Temporal(TemporalType.DATE)
    private Date sowingdt;
    @Column(name = "harvestingdt")
    @Temporal(TemporalType.DATE)
    private Date harvestingdt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;

    public Harvest() {
    }

    public Harvest(Integer harvestid) {
        this.harvestid = harvestid;
    }

    public Harvest(Integer harvestid, int siteid, int cropid, Date sowingdt) {
        this.harvestid = harvestid;
        this.siteid = siteid;
        this.cropid = cropid;
        this.sowingdt = sowingdt;
    }

    public Integer getHarvestid() {
        return harvestid;
    }

    public void setHarvestid(Integer harvestid) {
        this.harvestid = harvestid;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getCropid() {
        return cropid;
    }

    public void setCropid(int cropid) {
        this.cropid = cropid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
