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
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "empleave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleave.findAll", query = "SELECT e FROM Empleave e"),
    @NamedQuery(name = "Empleave.findById", query = "SELECT e FROM Empleave e WHERE e.id = :id"),
    @NamedQuery(name = "Empleave.findByEmployeeid", query = "SELECT e FROM Empleave e WHERE e.employeeid = :employeeid"),
    @NamedQuery(name = "Empleave.findByLeavedate", query = "SELECT e FROM Empleave e WHERE e.leavedate = :leavedate"),
    @NamedQuery(name = "Empleave.findByComments", query = "SELECT e FROM Empleave e WHERE e.comments = :comments")})
public class Empleave implements Serializable {

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
    @Column(name = "leavedate")
    @Temporal(TemporalType.DATE)
    private Date leavedate;
    @Size(max = 100)
    @Column(name = "comments")
    private String comments;

    public Empleave() {
    }

    public Empleave(Integer id) {
        this.id = id;
    }

    public Empleave(Integer id, int employeeid, Date leavedate) {
        this.id = id;
        this.employeeid = employeeid;
        this.leavedate = leavedate;
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

    public Date getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(Date leavedate) {
        this.leavedate = leavedate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof Empleave)) {
            return false;
        }
        Empleave other = (Empleave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Empleave[ id=" + id + " ]";
    }
    
}
