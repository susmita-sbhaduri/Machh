/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author sb
 */
public class ResCropAllSummaryDTO implements Serializable {
    private int resourceId;
    private BigDecimal appliedAmount; 
    private BigDecimal appliedCost;
    
    public ResCropAllSummaryDTO(int resourceId, BigDecimal appliedAmount, 
            BigDecimal appliedCost) {
        
        this.resourceId = resourceId;
        this.appliedAmount = appliedAmount;
        this.appliedCost = appliedCost;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public BigDecimal getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(BigDecimal appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public BigDecimal getAppliedCost() {
        return appliedCost;
    }

    public void setAppliedCost(BigDecimal appliedCost) {
        this.appliedCost = appliedCost;
    }

    
    
}
