/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
@Entity
@Table(name = "operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o"),
    @NamedQuery(name = "Operation.findByDate", query = "SELECT o FROM Operation o WHERE o.operationPK.date = :date"),
    @NamedQuery(name = "Operation.findByEmployeeid", query = "SELECT o FROM Operation o WHERE o.operationPK.employeeid = :employeeid"),
    @NamedQuery(name = "Operation.findByTranscategory", query = "SELECT o FROM Operation o WHERE o.transcategory = :transcategory"),
    @NamedQuery(name = "Operation.findByAmount", query = "SELECT o FROM Operation o WHERE o.amount = :amount")})
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OperationPK operationPK;
    @Size(max = 45)
    @Column(name = "transcategory")
    private String transcategory;
    @Size(max = 45)
    @Column(name = "amount")
    private String amount;
    @JoinColumn(name = "employeeid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public Operation() {
    }

    public Operation(OperationPK operationPK) {
        this.operationPK = operationPK;
    }

    public Operation(Date date, int employeeid) {
        this.operationPK = new OperationPK(date, employeeid);
    }

    public OperationPK getOperationPK() {
        return operationPK;
    }

    public void setOperationPK(OperationPK operationPK) {
        this.operationPK = operationPK;
    }

    public String getTranscategory() {
        return transcategory;
    }

    public void setTranscategory(String transcategory) {
        this.transcategory = transcategory;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (operationPK != null ? operationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.operationPK == null && other.operationPK != null) || (this.operationPK != null && !this.operationPK.equals(other.operationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Operation[ operationPK=" + operationPK + " ]";
    }
    
}
