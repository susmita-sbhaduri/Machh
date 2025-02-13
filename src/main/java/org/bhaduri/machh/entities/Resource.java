/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "resource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByResourcecategory", query = "SELECT r FROM Resource r WHERE r.resourcePK.resourcecategory = :resourcecategory"),
    @NamedQuery(name = "Resource.findByResourcename", query = "SELECT r FROM Resource r WHERE r.resourcePK.resourcename = :resourcename"),
    @NamedQuery(name = "Resource.findByCropcategory", query = "SELECT r FROM Resource r WHERE r.resourcePK.cropcategory = :cropcategory"),
    @NamedQuery(name = "Resource.findByCrop", query = "SELECT r FROM Resource r WHERE r.resourcePK.crop = :crop"),
    @NamedQuery(name = "Resource.findByResourcelocation", query = "SELECT r FROM Resource r WHERE r.resourcelocation = :resourcelocation"),
    @NamedQuery(name = "Resource.findByResourcecontact", query = "SELECT r FROM Resource r WHERE r.resourcecontact = :resourcecontact"),
    @NamedQuery(name = "Resource.findByAvailibilitytime", query = "SELECT r FROM Resource r WHERE r.availibilitytime = :availibilitytime"),
    @NamedQuery(name = "Resource.findByTransportmeans", query = "SELECT r FROM Resource r WHERE r.transportmeans = :transportmeans")})
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResourcePK resourcePK;
    @Size(max = 100)
    @Column(name = "resourcelocation")
    private String resourcelocation;
    @Size(max = 100)
    @Column(name = "resourcecontact")
    private String resourcecontact;
    @Size(max = 100)
    @Column(name = "availibilitytime")
    private String availibilitytime;
    @Size(max = 100)
    @Column(name = "transportmeans")
    private String transportmeans;

    public Resource() {
    }

    public Resource(ResourcePK resourcePK) {
        this.resourcePK = resourcePK;
    }

    public Resource(String resourcecategory, String resourcename, String cropcategory, String crop) {
        this.resourcePK = new ResourcePK(resourcecategory, resourcename, cropcategory, crop);
    }

    public ResourcePK getResourcePK() {
        return resourcePK;
    }

    public void setResourcePK(ResourcePK resourcePK) {
        this.resourcePK = resourcePK;
    }

    public String getResourcelocation() {
        return resourcelocation;
    }

    public void setResourcelocation(String resourcelocation) {
        this.resourcelocation = resourcelocation;
    }

    public String getResourcecontact() {
        return resourcecontact;
    }

    public void setResourcecontact(String resourcecontact) {
        this.resourcecontact = resourcecontact;
    }

    public String getAvailibilitytime() {
        return availibilitytime;
    }

    public void setAvailibilitytime(String availibilitytime) {
        this.availibilitytime = availibilitytime;
    }

    public String getTransportmeans() {
        return transportmeans;
    }

    public void setTransportmeans(String transportmeans) {
        this.transportmeans = transportmeans;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcePK != null ? resourcePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.resourcePK == null && other.resourcePK != null) || (this.resourcePK != null && !this.resourcePK.equals(other.resourcePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Resource[ resourcePK=" + resourcePK + " ]";
    }
    
}
