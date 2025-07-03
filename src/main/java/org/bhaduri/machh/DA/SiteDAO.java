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
import org.bhaduri.machh.JPA.SiteJpaController;
import org.bhaduri.machh.entities.Site;

/**
 *
 * @author sb
 */
public class SiteDAO extends SiteJpaController{
    public SiteDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
        
    public List<Site> listSite() {
        EntityManager em = getEntityManager();
        TypedQuery<Site> query = em.createNamedQuery("Site.listSite", Site.class);
        List<Site> listofsite = query.getResultList();
        return listofsite;
    }
    
    public Site getSiteName(int id) {
        EntityManager em = getEntityManager();
        TypedQuery<Site> query = em.createNamedQuery("Site.siteNameForId", Site.class); 
        query.setParameter("id", id);
        Site siterec = query.getSingleResult();
        return siterec;
    }
//    public List<Site> listSiteForId(String siteid) {
//        EntityManager em = getEntityManager();
//        TypedQuery<Site> query = em.createNamedQuery("Site.listSiteForId", Site.class);
//        query.setParameter("id", siteid);
//        List<Site> listofsite = query.getResultList();
//        return listofsite;
//    }
//    public int listSiteForCrop(String cropcategory, String crop) {
//        EntityManager em = getEntityManager();
//        TypedQuery<String> query = em.createNamedQuery("Site.listSiteForCrop", String.class);
//        query.setParameter("cropcategory", cropcategory);
//        query.setParameter("crop", crop);
////        int countofsiteid = query.getResultList().size();
//        return query.getResultList().size();
//    }
    
    
}
