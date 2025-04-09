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
import org.bhaduri.machh.JPA.ShopJpaController;
import org.bhaduri.machh.entities.Shop;
import org.bhaduri.machh.entities.Shopresource;

/**
 *
 * @author sb
 */
public class ShopDAO extends ShopJpaController{
    public ShopDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    
    public Shop getShopName(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Shop> query = em.createNamedQuery("Shop.shopNameForId", Shop.class); 
        query.setParameter("shopid", id);
        Shop shoprec = query.getSingleResult();
        return shoprec;
    }
    
}
