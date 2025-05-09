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
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "labourcrop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Labourcrop.findAll", query = "SELECT l FROM Labourcrop l"),
    @NamedQuery(name = "Labourcrop.findByApplicationid", query = "SELECT l FROM Labourcrop l WHERE l.applicationid = :applicationid"),
    @NamedQuery(name = "Labourcrop.findByHarvestid", query = "SELECT l FROM Labourcrop l WHERE l.harvestid = :harvestid"),
    @NamedQuery(name = "Labourcrop.findByAppldate", query = "SELECT l FROM Labourcrop l WHERE l.appldate = :appldate")})
public class Labourcrop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "applicationid")
    private Integer applicationid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "harvestid")
    private int harvestid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "appldate")
    @Temporal(TemporalType.DATE)
    private Date appldate;

    public Labourcrop() {
    }

    public Labourcrop(Integer applicationid) {
        this.applicationid = applicationid;
    }

    public Labourcrop(Integer applicationid, int harvestid, Date appldate) {
        this.applicationid = applicationid;
        this.harvestid = harvestid;
        this.appldate = appldate;
    }

    public Integer getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }

    public int getHarvestid() {
        return harvestid;
    }

    public void setHarvestid(int harvestid) {
        this.harvestid = harvestid;
    }

    public Date getAppldate() {
        return appldate;
    }

    public void setAppldate(Date appldate) {
        this.appldate = appldate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationid != null ? applicationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Labourcrop)) {
            return false;
        }
        Labourcrop other = (Labourcrop) object;
        if ((this.applicationid == null && other.applicationid != null) || (this.applicationid != null && !this.applicationid.equals(other.applicationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Labourcrop[ applicationid=" + applicationid + " ]";
    }
    
}
