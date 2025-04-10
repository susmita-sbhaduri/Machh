/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author sb
 */
@Embeddable
public class ShopresourcePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "shopid")
    private int shopid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private int resourceid;

    public ShopresourcePK() {
    }

    public ShopresourcePK(int shopid, int resourceid) {
        this.shopid = shopid;
        this.resourceid = resourceid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) shopid;
        hash += (int) resourceid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShopresourcePK)) {
            return false;
        }
        ShopresourcePK other = (ShopresourcePK) object;
        if (this.shopid != other.shopid) {
            return false;
        }
        if (this.resourceid != other.resourceid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.ShopresourcePK[ shopid=" + shopid + ", resourceid=" + resourceid + " ]";
    }
    
}
