/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.bhaduri.machh.JPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.util.List;
import org.bhaduri.machh.JPA.exceptions.NonexistentEntityException;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.JPA.exceptions.RollbackFailureException;
import org.bhaduri.machh.entities.Harvest;

/**
 *
 * @author sb
 */
public class HarvestJpaController implements Serializable {

    public HarvestJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Harvest harvest) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(harvest);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHarvest(harvest.getHarvestid()) != null) {
                throw new PreexistingEntityException("Harvest " + harvest + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Harvest harvest) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            harvest = em.merge(harvest);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = harvest.getHarvestid();
                if (findHarvest(id) == null) {
                    throw new NonexistentEntityException("The harvest with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Harvest harvest;
            try {
                harvest = em.getReference(Harvest.class, id);
                harvest.getHarvestid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The harvest with id " + id + " no longer exists.", enfe);
            }
            em.remove(harvest);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Harvest> findHarvestEntities() {
        return findHarvestEntities(true, -1, -1);
    }

    public List<Harvest> findHarvestEntities(int maxResults, int firstResult) {
        return findHarvestEntities(false, maxResults, firstResult);
    }

    private List<Harvest> findHarvestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Harvest.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Harvest findHarvest(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Harvest.class, id);
        } finally {
            em.close();
        }
    }

    public int getHarvestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Harvest> rt = cq.from(Harvest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
