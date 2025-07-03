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
import org.bhaduri.machh.JPA.HarvestJpaController;
import org.bhaduri.machh.entities.Harvest;

/**
 *
 * @author sb
 */
public class HarvestDAO extends HarvestJpaController{
    
    public HarvestDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    
    public List<Harvest> getActiveList() {
        EntityManager em = getEntityManager();
        TypedQuery<Harvest> query = em.createNamedQuery("Harvest.activeList", Harvest.class);            
        List<Harvest> activecroplist = query.getResultList();
        return activecroplist;
    }
    
    public Harvest getHarvestForId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Harvest> query = em.createNamedQuery("Harvest.harvestPerId", Harvest.class);  
        query.setParameter("harvestid", id); 
        Harvest rec = query.getSingleResult();
        return rec;
    }
    
    public int getMaxId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Harvest.getMaxId", Integer.class);        
        return query.getSingleResult();
    }
}
