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
import java.util.Date;
import java.util.List;
import org.bhaduri.machh.JPA.CropJpaController;


import org.bhaduri.machh.entities.Crop;
/**
 *
 * @author sb
 */
public class CropDAO extends CropJpaController{
    
    public CropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<String> listCropCat() {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Crop.listAllCropCat", String.class);
//        query.setParameter("scripid", scripid);        
        List<String> listofcropcat = query.getResultList();
        return listofcropcat;
    }
    
    public List<String> listCropName(String cropcategory) {
        EntityManager em = getEntityManager();
        TypedQuery<String> query = em.createNamedQuery("Crop.listNameByCat", String.class);
        query.setParameter("cropcategory", cropcategory);        
        List<String> listofcroppk = query.getResultList();
        return listofcroppk;
    }
    
    public List<Crop> getCropListAll() {
        EntityManager em = getEntityManager();
        TypedQuery<Crop> query = em.createNamedQuery("Crop.listAll", Crop.class);            
        List<Crop> listofcrop = query.getResultList();
        return listofcrop;
    }
    
    public List<Crop> getCropsPerSite(int siteid) {
        EntityManager em = getEntityManager();
        TypedQuery<Crop> query = em.createNamedQuery("Crop.getCropsPerSite", Crop.class);   
        query.setParameter("siteid", siteid);
        List<Crop> listofcrop = query.getResultList();
        return listofcrop;
    }
    
    public Crop getCropPerPK(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Crop> query = em.createNamedQuery("Crop.cropPerPK", Crop.class); 
        query.setParameter("cropid", id);
        Crop cropsperpk = query.getSingleResult();
        return cropsperpk;
    }
    
   
    
}
