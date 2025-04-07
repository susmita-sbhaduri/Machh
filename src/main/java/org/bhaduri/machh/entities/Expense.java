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
@Table(name = "expense")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expense.findAll", query = "SELECT e FROM Expense e"),
    @NamedQuery(name = "Expense.findByExpenseid", query = "SELECT e FROM Expense e WHERE e.expenseid = :expenseid"),
    @NamedQuery(name = "Expense.findByDate", query = "SELECT e FROM Expense e WHERE e.date = :date"),
    @NamedQuery(name = "Expense.findByExpensetype", query = "SELECT e FROM Expense e WHERE e.expensetype = :expensetype"),
    @NamedQuery(name = "Expense.findByExpenserefid", query = "SELECT e FROM Expense e WHERE e.expenserefid = :expenserefid"),
    @NamedQuery(name = "Expense.findByExpediture", query = "SELECT e FROM Expense e WHERE e.expediture = :expediture"),
    @NamedQuery(name = "Expense.findByComments", query = "SELECT e FROM Expense e WHERE e.comments = :comments")})
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "expenseid")
    private Integer expenseid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "expensetype")
    private String expensetype;
    @Column(name = "expenserefid")
    private Integer expenserefid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "expediture")
    private BigDecimal expediture;
    @Size(max = 100)
    @Column(name = "comments")
    private String comments;

    public Expense() {
    }

    public Expense(Integer expenseid) {
        this.expenseid = expenseid;
    }

    public Expense(Integer expenseid, Date date, String expensetype, BigDecimal expediture) {
        this.expenseid = expenseid;
        this.date = date;
        this.expensetype = expensetype;
        this.expediture = expediture;
    }

    public Integer getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(Integer expenseid) {
        this.expenseid = expenseid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(String expensetype) {
        this.expensetype = expensetype;
    }

    public Integer getExpenserefid() {
        return expenserefid;
    }

    public void setExpenserefid(Integer expenserefid) {
        this.expenserefid = expenserefid;
    }

    public BigDecimal getExpediture() {
        return expediture;
    }

    public void setExpediture(BigDecimal expediture) {
        this.expediture = expediture;
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
        hash += (expenseid != null ? expenseid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) object;
        if ((this.expenseid == null && other.expenseid != null) || (this.expenseid != null && !this.expenseid.equals(other.expenseid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Expense[ expenseid=" + expenseid + " ]";
    }
    
}
