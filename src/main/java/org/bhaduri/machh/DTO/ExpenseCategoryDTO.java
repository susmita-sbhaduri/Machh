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
public class ExpenseCategoryDTO implements Serializable {
    private String expenseType;
    private String cropID;

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getCropID() {
        return cropID;
    }

    public void setCropID(String cropID) {
        this.cropID = cropID;
    }
    
}
