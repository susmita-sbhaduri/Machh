/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author sb
 */
public class CropTransaction implements Serializable {
    private Date transactionDate;
    private String cropCategory;
    private String cropName;
    private String resourceCategory;
    private String resourceName;
    private String transactionType;
    private String quantity;
    private String totalPrice;
    private String comments;

    public CropTransaction(Date transactionDate, String cropCategory, String cropName, String resourceCategory, String resourceName, String transactionType, String quantity, String totalPrice, String comments) {
        this.transactionDate = transactionDate;
        this.cropCategory = cropCategory;
        this.cropName = cropName;
        this.resourceCategory = resourceCategory;
        this.resourceName = resourceName;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.comments = comments;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCropCategory() {
        return cropCategory;
    }

    public void setCropCategory(String cropCategory) {
        this.cropCategory = cropCategory;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(String resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
