/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import java.util.List;
import org.bhaduri.machh.JPA.EmpexpenseJpaController;
import org.bhaduri.machh.entities.Empexpense;

/**
 *
 * @author sb
 */
public class EmpexpenseDAO extends EmpexpenseJpaController{
    public EmpexpenseDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<Empexpense> getExpenseList(int empid, String expcat) {
        EntityManager em = getEntityManager();
        TypedQuery<Empexpense> query = em.createNamedQuery("Empexpense.expenseList", Empexpense.class);
        query.setParameter("employeeid", empid);
        query.setParameter("expcategory", expcat);
        return query.getResultList();
    }
    public List<Empexpense> getLoanList() {
        EntityManager em = getEntityManager();
        TypedQuery<Empexpense> query = em.createNamedQuery("Empexpense.loanList", Empexpense.class);
//        query.setParameter("employeeid", empid);
//        query.setParameter("expcategory", expcat);
        return query.getResultList();
    }
    
    public List<Empexpense> getPaybkLst(int empid, int refid) {
        EntityManager em = getEntityManager();
        TypedQuery<Empexpense> query = em.createNamedQuery("Empexpense.paybackList", Empexpense.class);
        query.setParameter("employeeid", empid);
        query.setParameter("refid", refid);
        return query.getResultList();
    }
    
    public int getMaxId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Empexpense.getMaxId", Integer.class);        
        return query.getSingleResult();
    }
}
