/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.UserTransaction;
import java.util.List;
import org.bhaduri.machh.JPA.ShopresourceJpaController;
import org.bhaduri.machh.entities.Shopresource;


public class ShopResDAO extends ShopresourceJpaController{
    private UserTransaction myUtx;
    public ShopResDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
        this.myUtx = utx; // store reference for your own use
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
    
    public Shopresource getShopResSingle(int resid, int shopid) {
        EntityManager em = getEntityManager();
        TypedQuery<Shopresource> query = em.createNamedQuery("Shopresource.shopDtlsPerPk", Shopresource.class);
        query.setParameter("resourceid", resid);
        query.setParameter("shopid", shopid);
        Shopresource singlerec = query.getSingleResult();
        return singlerec;
    }
    
    public int delForResid(int resid) throws Exception {
        EntityManager em = null;
        int rowsDeleted = 0;
        try {
            myUtx.begin();
            em = getEntityManager();
//            Query query = em.createQuery("DELETE FROM Shopresource s WHERE s.shopresourcePK.resourceid = :resourceid");
            Query query = em.createNamedQuery("Shopresource.delForResid");
            query.setParameter("resourceid", resid);
            rowsDeleted = query.executeUpdate();
            myUtx.commit();
        } catch (Exception ex) {
            if (myUtx != null) myUtx.rollback();
            throw ex;
        } finally {
            if (em != null) em.close();
        }
        return rowsDeleted;
    }
}


