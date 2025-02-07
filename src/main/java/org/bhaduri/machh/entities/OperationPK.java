/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Embeddable
public class OperationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "employeeid")
    private int employeeid;

    public OperationPK() {
    }

    public OperationPK(Date date, int employeeid) {
        this.date = date;
        this.employeeid = employeeid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (date != null ? date.hashCode() : 0);
        hash += (int) employeeid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperationPK)) {
            return false;
        }
        OperationPK other = (OperationPK) object;
        if ((this.date == null && other.date != null) || (this.date != null && !this.date.equals(other.date))) {
            return false;
        }
        if (this.employeeid != other.employeeid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.OperationPK[ date=" + date + ", employeeid=" + employeeid + " ]";
    }
    
}
