/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    @NamedQuery(name = "Shopresource.findByShopid", query = "SELECT s FROM Shopresource s WHERE s.shopresourcePK.shopid = :shopid"),
    @NamedQuery(name = "Shopresource.findByResourceid", query = "SELECT s FROM Shopresource s WHERE s.shopresourcePK.resourceid = :resourceid"),
    @NamedQuery(name = "Shopresource.findByRate", query = "SELECT s FROM Shopresource s WHERE s.rate = :rate")})
public class Shopresource implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ShopresourcePK shopresourcePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private BigDecimal rate;

    public Shopresource() {
    }

    public Shopresource(ShopresourcePK shopresourcePK) {
        this.shopresourcePK = shopresourcePK;
    }

    public Shopresource(ShopresourcePK shopresourcePK, BigDecimal rate) {
        this.shopresourcePK = shopresourcePK;
        this.rate = rate;
    }

    public Shopresource(int shopid, int resourceid) {
        this.shopresourcePK = new ShopresourcePK(shopid, resourceid);
    }

    public ShopresourcePK getShopresourcePK() {
        return shopresourcePK;
    }

    public void setShopresourcePK(ShopresourcePK shopresourcePK) {
        this.shopresourcePK = shopresourcePK;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shopresourcePK != null ? shopresourcePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shopresource)) {
            return false;
        }
        Shopresource other = (Shopresource) object;
        if ((this.shopresourcePK == null && other.shopresourcePK != null) || (this.shopresourcePK != null && !this.shopresourcePK.equals(other.shopresourcePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Shopresource[ shopresourcePK=" + shopresourcePK + " ]";
    }
    
}
