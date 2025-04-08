/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.DA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.bhaduri.machh.JPA.ResourceJpaController;


import org.bhaduri.machh.entities.Resource;

public class ResourceDAO extends ResourceJpaController{
    public ResourceDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<String> listResCat(String cropcat, String cropname) {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Resource.listResCatPerCrop", String.class);
        query.setParameter("cropcategory", cropcat);  
        query.setParameter("crop", cropname);     
        List<String> listrescatpercrop = query.getResultList();
        return listrescatpercrop;
    }
    
    public List<String> listResDet(String cropcat, String cropname, String rescat) {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Resource.listResDetPerCrop", String.class);
        query.setParameter("cropcategory", cropcat);  
        query.setParameter("crop", cropname);  
        query.setParameter("resourcecategory", rescat); 
        List<String> listresdetpercrop = query.getResultList();
        return listresdetpercrop;
    }
    
    public int listResourceForCrop(String cropcategory, String crop) {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Resource.listResourceForCrop", String.class);
        query.setParameter("cropcategory", cropcategory);
        query.setParameter("crop", crop);
//        int countofsiteid = query.getResultList().size();
        return query.getResultList().size();
    }
}
