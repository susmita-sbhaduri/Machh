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
import org.bhaduri.machh.JPA.ResourcecropJpaController;
import org.bhaduri.machh.entities.Resourcecrop;

/**
 *
 * @author sb
 */
public class ResourceCropDAO extends ResourcecropJpaController{
    public ResourceCropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxResCropId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Resourcecrop.getMaxResCropId", Integer.class);        
        return query.getSingleResult();
    }
    public List<Resourcecrop> getResCropHarvest(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Resourcecrop> query = em.createNamedQuery("Resourcecrop.getResCropHarvest", Resourcecrop.class);        
        query.setParameter("harvestid", id);
        return query.getResultList();
    }
    
    public Resourcecrop getResCropHarvestId(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Resourcecrop> query = em.createNamedQuery("Resourcecrop.getResCropId", Resourcecrop.class);        
        query.setParameter("applicationid", id);
        return query.getSingleResult();
    }
}
