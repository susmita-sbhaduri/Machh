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
import org.bhaduri.machh.JPA.ResourceaquireJpaController;
import org.bhaduri.machh.entities.Resourceaquire;


/**
 *
 * @author sb
 */
public class ResAcquireDAO extends ResourceaquireJpaController{
    public ResAcquireDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public int getMaxAqId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Resourceaquire.getMaxAqid", Integer.class);        
        return query.getSingleResult();
    }
    
    public Resourceaquire resAcqPerDt(Date acdate, int resid){
        EntityManager em = getEntityManager();
        TypedQuery<Resourceaquire> query = em.createNamedQuery("Resourceaquire.resAcqPerDt", Resourceaquire.class);
        query.setParameter("aquiredate", acdate);
        query.setParameter("resourceid", resid);
        query.setMaxResults(1); // Limit to 1 result
        List<Resourceaquire> result = query.getResultList();
        return result.get(0);
    }
}
