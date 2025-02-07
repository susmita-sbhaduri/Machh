/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
@Table(name = "transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t"),
    @NamedQuery(name = "Transaction.findByDate", query = "SELECT t FROM Transaction t WHERE t.transactionPK.date = :date"),
    @NamedQuery(name = "Transaction.findByResourcetype", query = "SELECT t FROM Transaction t WHERE t.transactionPK.resourcetype = :resourcetype"),
    @NamedQuery(name = "Transaction.findByCropcategory", query = "SELECT t FROM Transaction t WHERE t.transactionPK.cropcategory = :cropcategory"),
    @NamedQuery(name = "Transaction.findByCrop", query = "SELECT t FROM Transaction t WHERE t.transactionPK.crop = :crop"),
    @NamedQuery(name = "Transaction.findByTransactiontype", query = "SELECT t FROM Transaction t WHERE t.transactiontype = :transactiontype"),
    @NamedQuery(name = "Transaction.findByQuntity", query = "SELECT t FROM Transaction t WHERE t.quntity = :quntity"),
    @NamedQuery(name = "Transaction.findByPrice", query = "SELECT t FROM Transaction t WHERE t.price = :price"),
    @NamedQuery(name = "Transaction.findByComments", query = "SELECT t FROM Transaction t WHERE t.comments = :comments")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransactionPK transactionPK;
    @Size(max = 45)
    @Column(name = "transactiontype")
    private String transactiontype;
    @Size(max = 50)
    @Column(name = "quntity")
    private String quntity;
    @Size(max = 50)
    @Column(name = "price")
    private String price;
    @Size(max = 100)
    @Column(name = "comments")
    private String comments;
    @JoinColumns({
        @JoinColumn(name = "resourcetype", referencedColumnName = "resourcetype", insertable = false, updatable = false),
        @JoinColumn(name = "cropcategory", referencedColumnName = "cropcategory", insertable = false, updatable = false),
        @JoinColumn(name = "crop", referencedColumnName = "crop", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Resource resource;

    public Transaction() {
    }

    public Transaction(TransactionPK transactionPK) {
        this.transactionPK = transactionPK;
    }

    public Transaction(Date date, String resourcetype, String cropcategory, String crop) {
        this.transactionPK = new TransactionPK(date, resourcetype, cropcategory, crop);
    }

    public TransactionPK getTransactionPK() {
        return transactionPK;
    }

    public void setTransactionPK(TransactionPK transactionPK) {
        this.transactionPK = transactionPK;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getQuntity() {
        return quntity;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionPK != null ? transactionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionPK == null && other.transactionPK != null) || (this.transactionPK != null && !this.transactionPK.equals(other.transactionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.bhaduri.machh.entities.Transaction[ transactionPK=" + transactionPK + " ]";
    }
    
}
