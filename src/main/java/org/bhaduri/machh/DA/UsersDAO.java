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

import org.bhaduri.machh.JPA.UsersJpaController;
import org.bhaduri.machh.entities.Users;

public class UsersDAO extends UsersJpaController{
    
     public UsersDAO(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }
    public Users getUserDetails(String username, String password) {
        System.out.println("Attempting to get EntityManager...");
        EntityManager em = getEntityManager();
        TypedQuery<Users> query = em.createNamedQuery("Users.getUserDetails", Users.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Users userdetails = query.getSingleResult();
            return userdetails;
//        EntityTransaction transaction = em.getTransaction();
//        Users userdetails;  
//        userdetails = new Users();             
//        try {
//            transaction.begin(); // Start the transaction
//            // Perform operations
//            TypedQuery<Users> query = em.createNamedQuery("Users.getUserDetails", Users.class); 
//            query.setParameter("username", username);
//            query.setParameter("password", password);
//            userdetails = query.getSingleResult();   
//            
//            transaction.commit(); // Commit the transaction
//            return userdetails;
//        }
//        
//        catch (Exception e) {
//            System.out.println(e + " has occurred in getUserDetails.");
//            if (transaction.isActive()) {
//                transaction.rollback(); // Rollback on error
//            }
//            return null;
//        } finally {
//            em.close(); // Ensure the EntityManager is closed            
//        }
       
    }
    
}
