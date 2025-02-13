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
@Table(name = "site")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Site.findAll", query = "SELECT s FROM Site s"),
    @NamedQuery(name = "Site.findById", query = "SELECT s FROM Site s WHERE s.sitePK.id = :id"),
    @NamedQuery(name = "Site.findByCropcategory", query = "SELECT s FROM Site s WHERE s.sitePK.cropcategory = :cropcategory"),
    @NamedQuery(name = "Site.findByCrop", query = "SELECT s FROM Site s WHERE s.sitePK.crop = :crop"),
    @NamedQuery(name = "Site.findBySitetype", query = "SELECT s FROM Site s WHERE s.sitetype = :sitetype"),
    @NamedQuery(name = "Site.findBySizesqft", query = "SELECT s FROM Site s WHERE s.sizesqft = :sizesqft"),
    @NamedQuery(name = "Site.findBySizekatha", query = "SELECT s FROM Site s WHERE s.sizekatha = :sizekatha")})
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SitePK sitePK;
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

    public Site(SitePK sitePK) {
        this.sitePK = sitePK;
    }

    public Site(String id, String cropcategory, String crop) {
        this.sitePK = new SitePK(id, cropcategory, crop);
    }

    public SitePK getSitePK() {
        return sitePK;
    }

    public void setSitePK(SitePK sitePK) {
        this.sitePK = sitePK;
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
        hash += (sitePK != null ? sitePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Site)) {
            return false;
        }
        Site other = (Site) object;
        if ((this.sitePK == null && other.sitePK != null) || (this.sitePK != null && !this.sitePK.equals(other.sitePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Site[ sitePK=" + sitePK + " ]";
    }
    
}
