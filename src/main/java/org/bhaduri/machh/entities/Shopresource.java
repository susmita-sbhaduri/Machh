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
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "shopresource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shopresource.findAll", query = "SELECT s FROM Shopresource s"),
    @NamedQuery(name = "Shopresource.findById", query = "SELECT s FROM Shopresource s WHERE s.id = :id"),
    @NamedQuery(name = "Shopresource.findByResourceid", query = "SELECT s FROM Shopresource s WHERE s.resourceid = :resourceid"),
    @NamedQuery(name = "Shopresource.findByShopid", query = "SELECT s FROM Shopresource s WHERE s.shopid = :shopid"),
    @NamedQuery(name = "Shopresource.findByRate", query = "SELECT s FROM Shopresource s WHERE s.rate = :rate"),
    @NamedQuery(name = "Shopresource.findByResrtdate", query = "SELECT s FROM Shopresource s WHERE s.resrtdate = :resrtdate"),
    @NamedQuery(name = "Shopresource.findByStockperrt", query = "SELECT s FROM Shopresource s WHERE s.stockperrt = :stockperrt")})
public class Shopresource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private int resourceid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "shopid")
    private int shopid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "resrtdate")
    @Temporal(TemporalType.DATE)
    private Date resrtdate;
    @Column(name = "stockperrt")
    private BigDecimal stockperrt;

    public Shopresource() {
    }

    public Shopresource(Integer id) {
        this.id = id;
    }

    public Shopresource(Integer id, int resourceid, int shopid) {
        this.id = id;
        this.resourceid = resourceid;
        this.shopid = shopid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getResrtdate() {
        return resrtdate;
    }

    public void setResrtdate(Date resrtdate) {
        this.resrtdate = resrtdate;
    }

    public BigDecimal getStockperrt() {
        return stockperrt;
    }

    public void setStockperrt(BigDecimal stockperrt) {
        this.stockperrt = stockperrt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shopresource)) {
            return false;
        }
        Shopresource other = (Shopresource) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Shopresource[ id=" + id + " ]";
    }
    
}
