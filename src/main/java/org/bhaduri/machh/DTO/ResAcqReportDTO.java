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
public class ResAcqReportDTO implements Serializable {
    
    private String resName;
    private String acqDt;
    private String acqAmt;
    private String cost;

    
    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getAcqDt() {
        return acqDt;
    }

    public void setAcqDt(String acqDt) {
        this.acqDt = acqDt;
    }

    public String getAcqAmt() {
        return acqAmt;
    }

    public void setAcqAmt(String acqAmt) {
        this.acqAmt = acqAmt;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
    
}
