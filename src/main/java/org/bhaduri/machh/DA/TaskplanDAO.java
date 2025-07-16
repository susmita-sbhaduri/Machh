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
import org.bhaduri.machh.entities.Harvest;
import org.bhaduri.machh.entities.Taskplan;

/**
 *
 * @author sb
 */
public class TaskplanDAO extends TaskplanJpaController{
     public TaskplanDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Taskplan.getMaxId", Integer.class);        
        return query.getSingleResult();
    } 
    public List<Taskplan> getListForDate(Date dateinput) {
        EntityManager em = getEntityManager();
        TypedQuery<Taskplan> query = em.createNamedQuery("Taskplan.activeList", Taskplan.class);  
        query.setParameter("taskdate", dateinput);
        List<Taskplan> recordlist = query.getResultList();
        return recordlist;
    }
    
    public List<Taskplan> detailsBetweenDates(Date startdate, Date enddate) {
        EntityManager em = getEntityManager();
        TypedQuery<Taskplan> query = em.createNamedQuery("Taskplan.detailsBetweenDates", Taskplan.class);  
        query.setParameter("startdate", startdate);
        query.setParameter("enddate", enddate);
        List<Taskplan> recordlist = query.getResultList();
        return recordlist;
    }
    public Taskplan getTaskplanPerId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Taskplan> query = em.createNamedQuery("Taskplan.taskplanPerId", Taskplan.class);  
        query.setParameter("id", id); 
        Taskplan rec = query.getSingleResult();
        return rec;
    }
}
