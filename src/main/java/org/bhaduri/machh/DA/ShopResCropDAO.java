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
import org.bhaduri.machh.JPA.ShoprescropJpaController;
import org.bhaduri.machh.entities.Shoprescrop;
import org.bhaduri.machh.entities.Shopresource;

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
    public List<Shoprescrop> getShopResList(int rescropid) {
        EntityManager em = getEntityManager();
        TypedQuery<Shoprescrop> query = em.createNamedQuery("Shoprescrop.shopResCropList", Shoprescrop.class); 
        query.setParameter("recropid", rescropid);
//        List<Shoprescrop> shopreslist = query.getResultList();
        return query.getResultList();
    }
    
    public List<Shoprescrop> shopResListEmpty(int rescropid) {
        EntityManager em = getEntityManager();
        TypedQuery<Shoprescrop> query = em.createNamedQuery("Shoprescrop.shopResCropEmptyList", Shoprescrop.class); 
        query.setParameter("recropid", rescropid);
//        List<Shoprescrop> shopreslist = query.getResultList();
        return query.getResultList();
    }
    
    public Shoprescrop getShopResCrop(int rescropid, int shopresid) {
        EntityManager em = getEntityManager();
        TypedQuery<Shoprescrop> query = em.createNamedQuery("Shoprescrop.shopResResCrop", Shoprescrop.class); 
        query.setParameter("recropid", rescropid);
        query.setParameter("shopresid", shopresid);
//        List<Shoprescrop> shopreslist = query.getResultList();
        return query.getSingleResult();
    }
}
