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
import org.bhaduri.machh.entities.Sitecrop;
import org.bhaduri.machh.entities.SitecropPK;

/**
 *
 * @author sb
 */
public class SitecropJpaController implements Serializable {

    public SitecropJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sitecrop sitecrop) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (sitecrop.getSitecropPK() == null) {
            sitecrop.setSitecropPK(new SitecropPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(sitecrop);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSitecrop(sitecrop.getSitecropPK()) != null) {
                throw new PreexistingEntityException("Sitecrop " + sitecrop + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sitecrop sitecrop) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            sitecrop = em.merge(sitecrop);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SitecropPK id = sitecrop.getSitecropPK();
                if (findSitecrop(id) == null) {
                    throw new NonexistentEntityException("The sitecrop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SitecropPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sitecrop sitecrop;
            try {
                sitecrop = em.getReference(Sitecrop.class, id);
                sitecrop.getSitecropPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sitecrop with id " + id + " no longer exists.", enfe);
            }
            em.remove(sitecrop);
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

    public List<Sitecrop> findSitecropEntities() {
        return findSitecropEntities(true, -1, -1);
    }

    public List<Sitecrop> findSitecropEntities(int maxResults, int firstResult) {
        return findSitecropEntities(false, maxResults, firstResult);
    }

    private List<Sitecrop> findSitecropEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sitecrop.class));
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

    public Sitecrop findSitecrop(SitecropPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sitecrop.class, id);
        } finally {
            em.close();
        }
    }

    public int getSitecropCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sitecrop> rt = cq.from(Sitecrop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
