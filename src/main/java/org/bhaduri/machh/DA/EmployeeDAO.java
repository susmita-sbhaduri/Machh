/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import org.bhaduri.machh.JPA.EmployeeJpaController;

/**
 *
 * @author sb
 */
public class EmployeeDAO extends EmployeeJpaController{
    public EmployeeDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxEmpId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Employee.getMaxEmpId", Integer.class);        
        return query.getSingleResult();
    }
}
