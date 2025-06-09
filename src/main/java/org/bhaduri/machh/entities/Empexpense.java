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
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "empexpense")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empexpense.findAll", query = "SELECT e FROM Empexpense e"),
    @NamedQuery(name = "Empexpense.findById", query = "SELECT e FROM Empexpense e WHERE e.id = :id"),
    @NamedQuery(name = "Empexpense.findByEmployeeid", query = "SELECT e FROM Empexpense e WHERE e.employeeid = :employeeid"),
    @NamedQuery(name = "Empexpense.findByExpcategory", query = "SELECT e FROM Empexpense e WHERE e.expcategory = :expcategory"),
    @NamedQuery(name = "Empexpense.findByTotalloan", query = "SELECT e FROM Empexpense e WHERE e.totalloan = :totalloan"),
    @NamedQuery(name = "Empexpense.findByOutstanding", query = "SELECT e FROM Empexpense e WHERE e.outstanding = :outstanding"),
    @NamedQuery(name = "Empexpense.findByStartdate", query = "SELECT e FROM Empexpense e WHERE e.startdate = :startdate"),
    @NamedQuery(name = "Empexpense.findByEnddate", query = "SELECT e FROM Empexpense e WHERE e.enddate = :enddate")})
public class Empexpense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeid")
    private int employeeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "expcategory")
    private String expcategory;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalloan")
    private BigDecimal totalloan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "outstanding")
    private BigDecimal outstanding;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;

    public Empexpense() {
    }

    public Empexpense(Integer id) {
        this.id = id;
    }

    public Empexpense(Integer id, int employeeid, String expcategory, BigDecimal totalloan, BigDecimal outstanding, Date startdate) {
        this.id = id;
        this.employeeid = employeeid;
        this.expcategory = expcategory;
        this.totalloan = totalloan;
        this.outstanding = outstanding;
        this.startdate = startdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getExpcategory() {
        return expcategory;
    }

    public void setExpcategory(String expcategory) {
        this.expcategory = expcategory;
    }

    public BigDecimal getTotalloan() {
        return totalloan;
    }

    public void setTotalloan(BigDecimal totalloan) {
        this.totalloan = totalloan;
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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
        if (!(object instanceof Empexpense)) {
            return false;
        }
        Empexpense other = (Empexpense) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Empexpense[ id=" + id + " ]";
    }
    
}
