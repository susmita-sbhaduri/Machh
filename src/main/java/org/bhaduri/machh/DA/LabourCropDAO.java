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
import org.bhaduri.machh.JPA.LabourcropJpaController;
import org.bhaduri.machh.entities.Labourcrop;

/**
 *
 * @author sb
 */
public class LabourCropDAO extends LabourcropJpaController{
    public LabourCropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxLabCropId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Labourcrop.getMaxLabCropId", Integer.class);        
        return query.getSingleResult();
    }
    
    public List<Labourcrop> getLabCropHarvest(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Labourcrop> query = em.createNamedQuery("Labourcrop.getLabCropHarvest", Labourcrop.class);        
        query.setParameter("harvestid", id);
        return query.getResultList();
    }
    
    public Labourcrop getLabCropHarvestId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Labourcrop> query = em.createNamedQuery("Labourcrop.getLabCropId", Labourcrop.class);        
        query.setParameter("applicationid", id);
        return query.getSingleResult();
    }
    
    public List<Labourcrop> getForHrstDtRange(int id, Date sdate, Date edate) {
        EntityManager em = getEntityManager();
        TypedQuery<Labourcrop> query = em.createNamedQuery("Labourcrop.detailsForHarvest", Labourcrop.class); 
        
        query.setParameter("harvestid", id);
        query.setParameter("startdate", sdate);
        query.setParameter("enddate", edate);
        return query.getResultList();
    }
}
