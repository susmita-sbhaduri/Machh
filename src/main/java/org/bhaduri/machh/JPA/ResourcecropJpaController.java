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
import org.bhaduri.machh.entities.Resourcecrop;
import org.bhaduri.machh.entities.ResourcecropPK;

/**
 *
 * @author sb
 */
public class ResourcecropJpaController implements Serializable {

    public ResourcecropJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resourcecrop resourcecrop) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (resourcecrop.getResourcecropPK() == null) {
            resourcecrop.setResourcecropPK(new ResourcecropPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(resourcecrop);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findResourcecrop(resourcecrop.getResourcecropPK()) != null) {
                throw new PreexistingEntityException("Resourcecrop " + resourcecrop + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resourcecrop resourcecrop) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            resourcecrop = em.merge(resourcecrop);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ResourcecropPK id = resourcecrop.getResourcecropPK();
                if (findResourcecrop(id) == null) {
                    throw new NonexistentEntityException("The resourcecrop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResourcecropPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resourcecrop resourcecrop;
            try {
                resourcecrop = em.getReference(Resourcecrop.class, id);
                resourcecrop.getResourcecropPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resourcecrop with id " + id + " no longer exists.", enfe);
            }
            em.remove(resourcecrop);
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

    public List<Resourcecrop> findResourcecropEntities() {
        return findResourcecropEntities(true, -1, -1);
    }

    public List<Resourcecrop> findResourcecropEntities(int maxResults, int firstResult) {
        return findResourcecropEntities(false, maxResults, firstResult);
    }

    private List<Resourcecrop> findResourcecropEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resourcecrop.class));
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

    public Resourcecrop findResourcecrop(ResourcecropPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resourcecrop.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourcecropCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resourcecrop> rt = cq.from(Resourcecrop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
