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
@Table(name = "resourceaquire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resourceaquire.findAll", query = "SELECT r FROM Resourceaquire r"),
    @NamedQuery(name = "Resourceaquire.findByAquireid", query = "SELECT r FROM Resourceaquire r WHERE r.aquireid = :aquireid"),
    @NamedQuery(name = "Resourceaquire.findByResourceid", query = "SELECT r FROM Resourceaquire r WHERE r.resourceid = :resourceid"),
    @NamedQuery(name = "Resourceaquire.findByAquiredate", query = "SELECT r FROM Resourceaquire r WHERE r.aquiredate = :aquiredate"),
    @NamedQuery(name = "Resourceaquire.findByAmount", query = "SELECT r FROM Resourceaquire r WHERE r.amount = :amount")})
public class Resourceaquire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "aquireid")
    private Integer aquireid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resourceid")
    private int resourceid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aquiredate")
    @Temporal(TemporalType.DATE)
    private Date aquiredate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    public Resourceaquire() {
    }

    public Resourceaquire(Integer aquireid) {
        this.aquireid = aquireid;
    }

    public Resourceaquire(Integer aquireid, int resourceid, Date aquiredate, BigDecimal amount) {
        this.aquireid = aquireid;
        this.resourceid = resourceid;
        this.aquiredate = aquiredate;
        this.amount = amount;
    }

    public Integer getAquireid() {
        return aquireid;
    }

    public void setAquireid(Integer aquireid) {
        this.aquireid = aquireid;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public Date getAquiredate() {
        return aquiredate;
    }

    public void setAquiredate(Date aquiredate) {
        this.aquiredate = aquiredate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aquireid != null ? aquireid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resourceaquire)) {
            return false;
        }
        Resourceaquire other = (Resourceaquire) object;
        if ((this.aquireid == null && other.aquireid != null) || (this.aquireid != null && !this.aquireid.equals(other.aquireid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Resourceaquire[ aquireid=" + aquireid + " ]";
    }
    
}
