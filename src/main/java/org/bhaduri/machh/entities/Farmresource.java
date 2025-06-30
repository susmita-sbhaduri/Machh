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
import java.math.BigDecimal;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "farmresource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Farmresource.findAll", query = "SELECT f FROM Farmresource f"),
    @NamedQuery(name = "Farmresource.findByResourceid", query = "SELECT f FROM Farmresource f WHERE f.resourceid = :resourceid"),
    @NamedQuery(name = "Farmresource.findByResourcename", query = "SELECT f FROM Farmresource f WHERE f.resourcename = :resourcename"),
    @NamedQuery(name = "Farmresource.findByAvailableamount", query = "SELECT f FROM Farmresource f WHERE f.availableamount = :availableamount"),
    @NamedQuery(name = "Farmresource.findByUnit", query = "SELECT f FROM Farmresource f WHERE f.unit = :unit"),
    @NamedQuery(name = "Farmresource.findByCropweight", query = "SELECT f FROM Farmresource f WHERE f.cropweight = :cropweight"),
    @NamedQuery(name = "Farmresource.findByCropwtunit", query = "SELECT f FROM Farmresource f WHERE f.cropwtunit = :cropwtunit")})
public class Farmresource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private Integer resourceid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "resourcename")
    private String resourcename;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "availableamount")
    private BigDecimal availableamount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "unit")
    private String unit;
    @Column(name = "cropweight")
    private BigDecimal cropweight;
    @Size(max = 45)
    @Column(name = "cropwtunit")
    private String cropwtunit;

    public Farmresource() {
    }

    public Farmresource(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public Farmresource(Integer resourceid, String resourcename, BigDecimal availableamount, String unit) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.availableamount = availableamount;
        this.unit = unit;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public BigDecimal getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(BigDecimal availableamount) {
        this.availableamount = availableamount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCropweight() {
        return cropweight;
    }

    public void setCropweight(BigDecimal cropweight) {
        this.cropweight = cropweight;
    }

    public String getCropwtunit() {
        return cropwtunit;
    }

    public void setCropwtunit(String cropwtunit) {
        this.cropwtunit = cropwtunit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceid != null ? resourceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Farmresource)) {
            return false;
        }
        Farmresource other = (Farmresource) object;
        if ((this.resourceid == null && other.resourceid != null) || (this.resourceid != null && !this.resourceid.equals(other.resourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Farmresource[ resourceid=" + resourceid + " ]";
    }
    
}
