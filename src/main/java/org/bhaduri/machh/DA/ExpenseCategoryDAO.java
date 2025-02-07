/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

import org.bhaduri.machh.JPA.TransactionJpaController;
import org.bhaduri.machh.entities.Transaction;
/**
 *
 * @author sb
 */
public class ExpenseCategoryDAO extends ExpenseCategoryJpaController{
    public ExpenseCategoryDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<ExpenseCategory> listExpenseCategory() {
        EntityManager em = getEntityManager();
        TypedQuery<ExpenseCategory> query = em.createNamedQuery("ExpenseCategory.listAllCategory", ExpenseCategory.class);
//        query.setParameter("scripid", scripid);        
        List<ExpenseCategory> listofcategory = query.getResultList();
        return listofcategory;
    }
    
}
