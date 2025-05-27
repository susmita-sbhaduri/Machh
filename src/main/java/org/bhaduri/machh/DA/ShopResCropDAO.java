/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import org.bhaduri.machh.JPA.ShoprescropJpaController;

/**
 *
 * @author sb
 */
public class ShopResCropDAO extends ShoprescropJpaController{
    private UserTransaction myUtx;
    public ShopResCropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
        this.myUtx = utx; // store reference for your own use
    }
    public int getMaxId() {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Shoprescrop.getMaxId", Integer.class);        
        return query.getSingleResult();
    }
}
