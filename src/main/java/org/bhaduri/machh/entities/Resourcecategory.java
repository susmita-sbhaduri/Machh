/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "resourcecategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resourcecategory.findAll", query = "SELECT r FROM Resourcecategory r"),
    @NamedQuery(name = "Resourcecategory.findByResourcecategory", query = "SELECT r FROM Resourcecategory r WHERE r.resourcecategory = :resourcecategory")})
public class Resourcecategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "resourcecategory")
    private String resourcecategory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resourcecategory1")
    private List<Resource> resourceList;

    public Resourcecategory() {
    }

    public Resourcecategory(String resourcecategory) {
        this.resourcecategory = resourcecategory;
    }

    public String getResourcecategory() {
        return resourcecategory;
    }

    public void setResourcecategory(String resourcecategory) {
        this.resourcecategory = resourcecategory;
    }

    @XmlTransient
    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourcecategory != null ? resourcecategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resourcecategory)) {
            return false;
        }
        Resourcecategory other = (Resourcecategory) object;
        if ((this.resourcecategory == null && other.resourcecategory != null) || (this.resourcecategory != null && !this.resourcecategory.equals(other.resourcecategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Resourcecategory[ resourcecategory=" + resourcecategory + " ]";
    }
    
}
