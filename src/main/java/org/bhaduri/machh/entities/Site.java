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
@Table(name = "site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Site.findAll", query = "SELECT s FROM Site s"),
    @NamedQuery(name = "Site.findById", query = "SELECT s FROM Site s WHERE s.id = :id"),
    @NamedQuery(name = "Site.findBySitetype", query = "SELECT s FROM Site s WHERE s.sitetype = :sitetype"),
    @NamedQuery(name = "Site.findBySizesqft", query = "SELECT s FROM Site s WHERE s.sizesqft = :sizesqft"),
    @NamedQuery(name = "Site.findBySizekatha", query = "SELECT s FROM Site s WHERE s.sizekatha = :sizekatha")})
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id")
    private String id;
    @Size(max = 45)
    @Column(name = "sitetype")
    private String sitetype;
    @Size(max = 45)
    @Column(name = "sizesqft")
    private String sizesqft;
    @Size(max = 45)
    @Column(name = "sizekatha")
    private String sizekatha;

    public Site() {
    }

    public Site(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSitetype() {
        return sitetype;
    }

    public void setSitetype(String sitetype) {
        this.sitetype = sitetype;
    }

    public String getSizesqft() {
        return sizesqft;
    }

    public void setSizesqft(String sizesqft) {
        this.sizesqft = sizesqft;
    }

    public String getSizekatha() {
        return sizekatha;
    }

    public void setSizekatha(String sizekatha) {
        this.sizekatha = sizekatha;
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
        if (!(object instanceof Site)) {
            return false;
        }
        Site other = (Site) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Site[ id=" + id + " ]";
    }
    
}
