/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DTO;

import java.io.Serializable;

/**
 *
 * @author sb
 */
public class FarmresourceDTO implements Serializable {
    private int resourceId;
    private String resourceName;
    private float availableAmt;
    private String unit;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public float getAvailableAmt() {
        return availableAmt;
    }

    public void setAvailableAmt(float availableAmt) {
        this.availableAmt = availableAmt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    
}
