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
@Table(name = "taskplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taskplan.findAll", query = "SELECT t FROM Taskplan t"),
    @NamedQuery(name = "Taskplan.findById", query = "SELECT t FROM Taskplan t WHERE t.id = :id"),
    @NamedQuery(name = "Taskplan.findByTasktype", query = "SELECT t FROM Taskplan t WHERE t.tasktype = :tasktype"),
    @NamedQuery(name = "Taskplan.findByTaskdate", query = "SELECT t FROM Taskplan t WHERE t.taskdate = :taskdate"),
    @NamedQuery(name = "Taskplan.findByTaskname", query = "SELECT t FROM Taskplan t WHERE t.taskname = :taskname"),
    @NamedQuery(name = "Taskplan.findByHasvestid", query = "SELECT t FROM Taskplan t WHERE t.hasvestid = :hasvestid"),
    @NamedQuery(name = "Taskplan.findByResourceid", query = "SELECT t FROM Taskplan t WHERE t.resourceid = :resourceid"),
    @NamedQuery(name = "Taskplan.findByAppliedamt", query = "SELECT t FROM Taskplan t WHERE t.appliedamt = :appliedamt"),
    @NamedQuery(name = "Taskplan.findByAppamtcost", query = "SELECT t FROM Taskplan t WHERE t.appamtcost = :appamtcost"),
    @NamedQuery(name = "Taskplan.findByAppliedflag", query = "SELECT t FROM Taskplan t WHERE t.appliedflag = :appliedflag"),
    @NamedQuery(name = "Taskplan.findByComments", query = "SELECT t FROM Taskplan t WHERE t.comments = :comments")})
public class Taskplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tasktype")
    private String tasktype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "taskdate")
    @Temporal(TemporalType.DATE)
    private Date taskdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "taskname")
    private String taskname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasvestid")
    private int hasvestid;
    @Column(name = "resourceid")
    private Integer resourceid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "appliedamt")
    private BigDecimal appliedamt;
    @Column(name = "appamtcost")
    private BigDecimal appamtcost;
    @Size(max = 45)
    @Column(name = "appliedflag")
    private String appliedflag;
    @Size(max = 100)
    @Column(name = "comments")
    private String comments;

    public Taskplan() {
    }

    public Taskplan(Integer id) {
        this.id = id;
    }

    public Taskplan(Integer id, String tasktype, Date taskdate, String taskname, int hasvestid) {
        this.id = id;
        this.tasktype = tasktype;
        this.taskdate = taskdate;
        this.taskname = taskname;
        this.hasvestid = hasvestid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public Date getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(Date taskdate) {
        this.taskdate = taskdate;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public int getHasvestid() {
        return hasvestid;
    }

    public void setHasvestid(int hasvestid) {
        this.hasvestid = hasvestid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public BigDecimal getAppliedamt() {
        return appliedamt;
    }

    public void setAppliedamt(BigDecimal appliedamt) {
        this.appliedamt = appliedamt;
    }

    public BigDecimal getAppamtcost() {
        return appamtcost;
    }

    public void setAppamtcost(BigDecimal appamtcost) {
        this.appamtcost = appamtcost;
    }

    public String getAppliedflag() {
        return appliedflag;
    }

    public void setAppliedflag(String appliedflag) {
        this.appliedflag = appliedflag;
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
        if (!(object instanceof Taskplan)) {
            return false;
        }
        Taskplan other = (Taskplan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Taskplan[ id=" + id + " ]";
    }
    
}
