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
@Table(name = "shopresource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shopresource.findAll", query = "SELECT s FROM Shopresource s"),
    @NamedQuery(name = "Shopresource.findByResshoprefid", query = "SELECT s FROM Shopresource s WHERE s.resshoprefid = :resshoprefid"),
    @NamedQuery(name = "Shopresource.findByShopid", query = "SELECT s FROM Shopresource s WHERE s.shopid = :shopid"),
    @NamedQuery(name = "Shopresource.findByResourceid", query = "SELECT s FROM Shopresource s WHERE s.resourceid = :resourceid"),
    @NamedQuery(name = "Shopresource.findByRate", query = "SELECT s FROM Shopresource s WHERE s.rate = :rate"),
    @NamedQuery(name = "Shopresource.findByUnit", query = "SELECT s FROM Shopresource s WHERE s.unit = :unit")})
public class Shopresource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "resshoprefid")
    private Integer resshoprefid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "shopid")
    private int shopid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private int resourceid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private BigDecimal rate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "unit")
    private String unit;

    public Shopresource() {
    }

    public Shopresource(Integer resshoprefid) {
        this.resshoprefid = resshoprefid;
    }

    public Shopresource(Integer resshoprefid, int shopid, int resourceid, BigDecimal rate, String unit) {
        this.resshoprefid = resshoprefid;
        this.shopid = shopid;
        this.resourceid = resourceid;
        this.rate = rate;
        this.unit = unit;
    }

    public Integer getResshoprefid() {
        return resshoprefid;
    }

    public void setResshoprefid(Integer resshoprefid) {
        this.resshoprefid = resshoprefid;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
        hash += (resshoprefid != null ? resshoprefid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shopresource)) {
            return false;
        }
        Shopresource other = (Shopresource) object;
        if ((this.resshoprefid == null && other.resshoprefid != null) || (this.resshoprefid != null && !this.resshoprefid.equals(other.resshoprefid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Shopresource[ resshoprefid=" + resshoprefid + " ]";
    }
    
}
