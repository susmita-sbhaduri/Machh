/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import org.bhaduri.machh.JPA.ResourceaquireJpaController;
import org.bhaduri.machh.entities.Farmresource;
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
}
