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
    @NamedQuery(name = "Expense.findByDate", query = "SELECT e FROM Expense e WHERE e.expensePK.date = :date"),
    @NamedQuery(name = "Expense.findByExpensecategory", query = "SELECT e FROM Expense e WHERE e.expensePK.expensecategory = :expensecategory"),
    @NamedQuery(name = "Expense.findByAmount", query = "SELECT e FROM Expense e WHERE e.amount = :amount"),
    @NamedQuery(name = "Expense.findByDetails", query = "SELECT e FROM Expense e WHERE e.details = :details")})
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExpensePK expensePK;
    @Size(max = 100)
    @Column(name = "amount")
    private String amount;
    @Size(max = 100)
    @Column(name = "details")
    private String details;

    public Expense() {
    }

    public Expense(ExpensePK expensePK) {
        this.expensePK = expensePK;
    }

    public Expense(Date date, String expensecategory) {
        this.expensePK = new ExpensePK(date, expensecategory);
    }

    public ExpensePK getExpensePK() {
        return expensePK;
    }

    public void setExpensePK(ExpensePK expensePK) {
        this.expensePK = expensePK;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expensePK != null ? expensePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) object;
        if ((this.expensePK == null && other.expensePK != null) || (this.expensePK != null && !this.expensePK.equals(other.expensePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Expense[ expensePK=" + expensePK + " ]";
    }
    
}
