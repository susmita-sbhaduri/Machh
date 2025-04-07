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
import java.math.BigDecimal;
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
    @NamedQuery(name = "Resourcecrop.findByApplicationid", query = "SELECT r FROM Resourcecrop r WHERE r.applicationid = :applicationid"),
    @NamedQuery(name = "Resourcecrop.findByResourceid", query = "SELECT r FROM Resourcecrop r WHERE r.resourceid = :resourceid"),
    @NamedQuery(name = "Resourcecrop.findByHarvestid", query = "SELECT r FROM Resourcecrop r WHERE r.harvestid = :harvestid"),
    @NamedQuery(name = "Resourcecrop.findByAppldate", query = "SELECT r FROM Resourcecrop r WHERE r.appldate = :appldate"),
    @NamedQuery(name = "Resourcecrop.findByAppliedamt", query = "SELECT r FROM Resourcecrop r WHERE r.appliedamt = :appliedamt"),
    @NamedQuery(name = "Resourcecrop.findByUnit", query = "SELECT r FROM Resourcecrop r WHERE r.unit = :unit")})
public class Resourcecrop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "applicationid")
    private Integer applicationid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private int resourceid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "harvestid")
    private int harvestid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "appldate")
    @Temporal(TemporalType.DATE)
    private Date appldate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "appliedamt")
    private BigDecimal appliedamt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "unit")
    private String unit;

    public Resourcecrop() {
    }

    public Resourcecrop(Integer applicationid) {
        this.applicationid = applicationid;
    }

    public Resourcecrop(Integer applicationid, int resourceid, int harvestid, Date appldate, BigDecimal appliedamt, String unit) {
        this.applicationid = applicationid;
        this.resourceid = resourceid;
        this.harvestid = harvestid;
        this.appldate = appldate;
        this.appliedamt = appliedamt;
        this.unit = unit;
    }

    public Integer getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
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

    public BigDecimal getAppliedamt() {
        return appliedamt;
    }

    public void setAppliedamt(BigDecimal appliedamt) {
        this.appliedamt = appliedamt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
        if (!(object instanceof Resourcecrop)) {
            return false;
        }
        Resourcecrop other = (Resourcecrop) object;
        if ((this.applicationid == null && other.applicationid != null) || (this.applicationid != null && !this.applicationid.equals(other.applicationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Resourcecrop[ applicationid=" + applicationid + " ]";
    }
    
}
