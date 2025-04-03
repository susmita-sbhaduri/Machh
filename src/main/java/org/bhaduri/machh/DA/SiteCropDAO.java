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
import org.bhaduri.machh.JPA.SitecropJpaController;
import org.bhaduri.machh.entities.Sitecrop;

public class SiteCropDAO extends SitecropJpaController{
    
    public SiteCropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }  
    
    public List<Sitecrop> listSitCropForId(String siteid) {
        EntityManager em = getEntityManager();
        TypedQuery<Sitecrop> query = em.createNamedQuery("Sitecrop.listSitCropForId", Sitecrop.class);
        query.setParameter("id", siteid);
        List<Sitecrop> listofsite = query.getResultList();
        return listofsite;
    }

}

