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

/**
 *
 * @author sb
 */
@Entity
@Table(name = "shop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shop.findAll", query = "SELECT s FROM Shop s"),
    @NamedQuery(name = "Shop.findByShopid", query = "SELECT s FROM Shop s WHERE s.shopid = :shopid"),
    @NamedQuery(name = "Shop.findByLocation", query = "SELECT s FROM Shop s WHERE s.location = :location"),
    @NamedQuery(name = "Shop.findByContact", query = "SELECT s FROM Shop s WHERE s.contact = :contact"),
    @NamedQuery(name = "Shop.findByAvailabilitytime", query = "SELECT s FROM Shop s WHERE s.availabilitytime = :availabilitytime")})
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "shopid")
    private Integer shopid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "contact")
    private String contact;
    @Size(max = 100)
    @Column(name = "availabilitytime")
    private String availabilitytime;

    public Shop() {
    }

    public Shop(Integer shopid) {
        this.shopid = shopid;
    }

    public Shop(Integer shopid, String location, String contact) {
        this.shopid = shopid;
        this.location = location;
        this.contact = contact;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAvailabilitytime() {
        return availabilitytime;
    }

    public void setAvailabilitytime(String availabilitytime) {
        this.availabilitytime = availabilitytime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shopid != null ? shopid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shop)) {
            return false;
        }
        Shop other = (Shop) object;
        if ((this.shopid == null && other.shopid != null) || (this.shopid != null && !this.shopid.equals(other.shopid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Shop[ shopid=" + shopid + " ]";
    }
    
}
