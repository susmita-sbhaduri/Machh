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
import org.bhaduri.machh.JPA.CropJpaController;

import org.bhaduri.machh.JPA.TransactionJpaController;
import org.bhaduri.machh.entities.CropPK;
import org.bhaduri.machh.entities.Transaction;
/**
 *
 * @author sb
 */
public class CropDAO extends CropJpaController{
    public CropDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public List<CropPK> listCropPk() {
        EntityManager em = getEntityManager();
        TypedQuery<CropPK> query = em.createNamedQuery("CropPK.listAllCropPk", CropPK.class);
//        query.setParameter("scripid", scripid);        
        List<CropPK> listofcroppk = query.getResultList();
        return listofcroppk;
    }
    
}
