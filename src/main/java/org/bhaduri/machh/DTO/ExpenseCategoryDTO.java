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
    private CropTransaction cropTransaction;
    private OpsTransaction opsTransaction;

    public ExpenseCategoryDTO(String expenseType, CropTransaction cropTransaction) {
        this.expenseType = expenseType;
        this.cropTransaction = cropTransaction;
    }

    public ExpenseCategoryDTO(String expenseType, OpsTransaction opsTransaction) {
        this.expenseType = expenseType;
        this.opsTransaction = opsTransaction;
    }
    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

}
