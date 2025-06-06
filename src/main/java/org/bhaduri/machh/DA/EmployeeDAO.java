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
import org.bhaduri.machh.JPA.EmployeeJpaController;
import org.bhaduri.machh.entities.Employee;

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
    
    public List<Employee> getActiveList() {
        EntityManager em = getEntityManager();
        TypedQuery<Employee> query = em.createNamedQuery("Employee.activeList", Employee.class);
        return query.getResultList();
    }
    
     public Employee getEmpName(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Employee> query = em.createNamedQuery("Employee.nameForId", Employee.class);
        query.setParameter("id", id);       
        return query.getSingleResult();
    }
}
