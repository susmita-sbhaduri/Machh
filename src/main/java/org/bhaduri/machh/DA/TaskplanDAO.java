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
import org.bhaduri.machh.JPA.TaskplanJpaController;
import org.bhaduri.machh.entities.Taskplan;

/**
 *
 * @author sb
 */
public class TaskplanDAO extends TaskplanJpaController{
     public TaskplanDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
     
     public List<Taskplan> getListForDate(Date dateinput) {
        EntityManager em = getEntityManager();
        TypedQuery<Taskplan> query = em.createNamedQuery("Taskplan.activeList", Taskplan.class);  
        query.setParameter("tdate", dateinput);
        List<Taskplan> activecroplist = query.getResultList();
        return activecroplist;
    }
    
}
