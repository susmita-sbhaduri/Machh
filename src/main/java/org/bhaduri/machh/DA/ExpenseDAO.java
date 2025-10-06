/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import java.util.Date;
import java.util.List;
import org.bhaduri.machh.JPA.ExpenseJpaController;
import org.bhaduri.machh.entities.Expense;

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
    public Expense getExpRecForLabHarvest(int refid, String type) {
        EntityManager em = getEntityManager();
        TypedQuery<Expense> query = em.createNamedQuery("Expense.getRecForLabHrvst", Expense.class);
        query.setParameter("expenserefid", refid);
        query.setParameter("expensetype", type);
        return query.getSingleResult();
    }
    
    public List<Expense> getEmpExpenses(Date sdate, Date edate) {
        EntityManager em = getEntityManager();
        TypedQuery<Expense> query = em.createNamedQuery("Expense.empPayments", Expense.class); 
        query.setParameter("startdate", sdate);
        query.setParameter("enddate", edate);
        return query.getResultList();
    }
    
    public List<Expense> getExpMonthly(Date sdate, Date edate) {
        EntityManager em = getEntityManager();
        TypedQuery<Expense> query = em.createNamedQuery("Expense.expMonthly", Expense.class); 
        query.setParameter("startdate", sdate);
        query.setParameter("enddate", edate);
        return query.getResultList();
    }
}
