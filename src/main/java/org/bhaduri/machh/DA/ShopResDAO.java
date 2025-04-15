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
import org.bhaduri.machh.JPA.ShopresourceJpaController;
import org.bhaduri.machh.entities.Shopresource;

/**
 *
 * @author sb
 */
public class ShopResDAO extends ShopresourceJpaController{
    
    public ShopResDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<Shopresource> getShopResList() {
        EntityManager em = getEntityManager();
        TypedQuery<Shopresource> query = em.createNamedQuery("Shopresource.shopResList", Shopresource.class);            
        List<Shopresource> shopreslist = query.getResultList();
        return shopreslist;
    }
    
    public List<Integer> getShopsForRes(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Integer> query = em.createNamedQuery("Shopresource.shopsPerRes", Integer.class);
        query.setParameter("resourceid", id);
        List<Integer> shopids = query.getResultList();
        return shopids;
    }
    
    public List<Shopresource> getShopDtlssForRes(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Shopresource> query = em.createNamedQuery("Shopresource.shopDtlsPerRes", Shopresource.class);
        query.setParameter("resourceid", id);
        List<Shopresource> shopids = query.getResultList();
        return shopids;
    }
}
