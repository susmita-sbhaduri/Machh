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
import org.bhaduri.machh.entities.Crop;
import org.bhaduri.machh.entities.Site;
import org.bhaduri.machh.entities.SitePK;

/**
 *
 * @author sb
 */
public class SiteJpaController implements Serializable {

    public SiteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Site site) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (site.getSitePK() == null) {
            site.setSitePK(new SitePK());
        }
        site.getSitePK().setCrop(site.getCrop1().getCropPK().getCrop());
        site.getSitePK().setCropcategory(site.getCrop1().getCropPK().getCropcategory());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crop crop1 = site.getCrop1();
            if (crop1 != null) {
                crop1 = em.getReference(crop1.getClass(), crop1.getCropPK());
                site.setCrop1(crop1);
            }
            em.persist(site);
            if (crop1 != null) {
                crop1.getSiteList().add(site);
                crop1 = em.merge(crop1);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSite(site.getSitePK()) != null) {
                throw new PreexistingEntityException("Site " + site + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Site site) throws NonexistentEntityException, RollbackFailureException, Exception {
        site.getSitePK().setCrop(site.getCrop1().getCropPK().getCrop());
        site.getSitePK().setCropcategory(site.getCrop1().getCropPK().getCropcategory());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Site persistentSite = em.find(Site.class, site.getSitePK());
            Crop crop1Old = persistentSite.getCrop1();
            Crop crop1New = site.getCrop1();
            if (crop1New != null) {
                crop1New = em.getReference(crop1New.getClass(), crop1New.getCropPK());
                site.setCrop1(crop1New);
            }
            site = em.merge(site);
            if (crop1Old != null && !crop1Old.equals(crop1New)) {
                crop1Old.getSiteList().remove(site);
                crop1Old = em.merge(crop1Old);
            }
            if (crop1New != null && !crop1New.equals(crop1Old)) {
                crop1New.getSiteList().add(site);
                crop1New = em.merge(crop1New);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SitePK id = site.getSitePK();
                if (findSite(id) == null) {
                    throw new NonexistentEntityException("The site with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SitePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Site site;
            try {
                site = em.getReference(Site.class, id);
                site.getSitePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The site with id " + id + " no longer exists.", enfe);
            }
            Crop crop1 = site.getCrop1();
            if (crop1 != null) {
                crop1.getSiteList().remove(site);
                crop1 = em.merge(crop1);
            }
            em.remove(site);
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

    public List<Site> findSiteEntities() {
        return findSiteEntities(true, -1, -1);
    }

    public List<Site> findSiteEntities(int maxResults, int firstResult) {
        return findSiteEntities(false, maxResults, firstResult);
    }

    private List<Site> findSiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Site.class));
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

    public Site findSite(SitePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Site.class, id);
        } finally {
            em.close();
        }
    }

    public int getSiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Site> rt = cq.from(Site.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
