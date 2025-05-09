/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import org.bhaduri.machh.JPA.ExpenseJpaController;

/**
 *
 * @author sb
 */
public class ExpenseDAO extends ExpenseJpaController{
    public ExpenseDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxExpId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Expense.getMaxExpId", Integer.class);        
        return query.getSingleResult();
    }
    
}
