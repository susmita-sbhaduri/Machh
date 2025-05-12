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
public class ResCropSummaryDTO implements Serializable {
    private int resourceId;
    private int harvestId;
    private BigDecimal appliedAmount;
    
    public ResCropSummaryDTO(int harvestId, int resourceId, BigDecimal appliedAmount) {
        this.harvestId = harvestId;
        this.resourceId = resourceId;
        this.appliedAmount = appliedAmount;
        
    }

    public int getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(int harvestId) {
        this.harvestId = harvestId;
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

    
    
}
