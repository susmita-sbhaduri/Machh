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
import org.bhaduri.machh.entities.Site;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.JPA.exceptions.IllegalOrphanException;
import org.bhaduri.machh.JPA.exceptions.NonexistentEntityException;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.JPA.exceptions.RollbackFailureException;
import org.bhaduri.machh.entities.Crop;
import org.bhaduri.machh.entities.CropPK;
import org.bhaduri.machh.entities.Resource;

/**
 *
 * @author sb
 */
public class CropJpaController implements Serializable {

    public CropJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Crop crop) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (crop.getCropPK() == null) {
            crop.setCropPK(new CropPK());
        }
        if (crop.getSiteList() == null) {
            crop.setSiteList(new ArrayList<Site>());
        }
        if (crop.getResourceList() == null) {
            crop.setResourceList(new ArrayList<Resource>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Site> attachedSiteList = new ArrayList<Site>();
            for (Site siteListSiteToAttach : crop.getSiteList()) {
                siteListSiteToAttach = em.getReference(siteListSiteToAttach.getClass(), siteListSiteToAttach.getSitePK());
                attachedSiteList.add(siteListSiteToAttach);
            }
            crop.setSiteList(attachedSiteList);
            List<Resource> attachedResourceList = new ArrayList<Resource>();
            for (Resource resourceListResourceToAttach : crop.getResourceList()) {
                resourceListResourceToAttach = em.getReference(resourceListResourceToAttach.getClass(), resourceListResourceToAttach.getResourcePK());
                attachedResourceList.add(resourceListResourceToAttach);
            }
            crop.setResourceList(attachedResourceList);
            em.persist(crop);
            for (Site siteListSite : crop.getSiteList()) {
                Crop oldCrop1OfSiteListSite = siteListSite.getCrop1();
                siteListSite.setCrop1(crop);
                siteListSite = em.merge(siteListSite);
                if (oldCrop1OfSiteListSite != null) {
                    oldCrop1OfSiteListSite.getSiteList().remove(siteListSite);
                    oldCrop1OfSiteListSite = em.merge(oldCrop1OfSiteListSite);
                }
            }
            for (Resource resourceListResource : crop.getResourceList()) {
                Crop oldCrop1OfResourceListResource = resourceListResource.getCrop1();
                resourceListResource.setCrop1(crop);
                resourceListResource = em.merge(resourceListResource);
                if (oldCrop1OfResourceListResource != null) {
                    oldCrop1OfResourceListResource.getResourceList().remove(resourceListResource);
                    oldCrop1OfResourceListResource = em.merge(oldCrop1OfResourceListResource);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCrop(crop.getCropPK()) != null) {
                throw new PreexistingEntityException("Crop " + crop + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Crop crop) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crop persistentCrop = em.find(Crop.class, crop.getCropPK());
            List<Site> siteListOld = persistentCrop.getSiteList();
            List<Site> siteListNew = crop.getSiteList();
            List<Resource> resourceListOld = persistentCrop.getResourceList();
            List<Resource> resourceListNew = crop.getResourceList();
            List<String> illegalOrphanMessages = null;
            for (Site siteListOldSite : siteListOld) {
                if (!siteListNew.contains(siteListOldSite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Site " + siteListOldSite + " since its crop1 field is not nullable.");
                }
            }
            for (Resource resourceListOldResource : resourceListOld) {
                if (!resourceListNew.contains(resourceListOldResource)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Resource " + resourceListOldResource + " since its crop1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Site> attachedSiteListNew = new ArrayList<Site>();
            for (Site siteListNewSiteToAttach : siteListNew) {
                siteListNewSiteToAttach = em.getReference(siteListNewSiteToAttach.getClass(), siteListNewSiteToAttach.getSitePK());
                attachedSiteListNew.add(siteListNewSiteToAttach);
            }
            siteListNew = attachedSiteListNew;
            crop.setSiteList(siteListNew);
            List<Resource> attachedResourceListNew = new ArrayList<Resource>();
            for (Resource resourceListNewResourceToAttach : resourceListNew) {
                resourceListNewResourceToAttach = em.getReference(resourceListNewResourceToAttach.getClass(), resourceListNewResourceToAttach.getResourcePK());
                attachedResourceListNew.add(resourceListNewResourceToAttach);
            }
            resourceListNew = attachedResourceListNew;
            crop.setResourceList(resourceListNew);
            crop = em.merge(crop);
            for (Site siteListNewSite : siteListNew) {
                if (!siteListOld.contains(siteListNewSite)) {
                    Crop oldCrop1OfSiteListNewSite = siteListNewSite.getCrop1();
                    siteListNewSite.setCrop1(crop);
                    siteListNewSite = em.merge(siteListNewSite);
                    if (oldCrop1OfSiteListNewSite != null && !oldCrop1OfSiteListNewSite.equals(crop)) {
                        oldCrop1OfSiteListNewSite.getSiteList().remove(siteListNewSite);
                        oldCrop1OfSiteListNewSite = em.merge(oldCrop1OfSiteListNewSite);
                    }
                }
            }
            for (Resource resourceListNewResource : resourceListNew) {
                if (!resourceListOld.contains(resourceListNewResource)) {
                    Crop oldCrop1OfResourceListNewResource = resourceListNewResource.getCrop1();
                    resourceListNewResource.setCrop1(crop);
                    resourceListNewResource = em.merge(resourceListNewResource);
                    if (oldCrop1OfResourceListNewResource != null && !oldCrop1OfResourceListNewResource.equals(crop)) {
                        oldCrop1OfResourceListNewResource.getResourceList().remove(resourceListNewResource);
                        oldCrop1OfResourceListNewResource = em.merge(oldCrop1OfResourceListNewResource);
                    }
                }
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
                CropPK id = crop.getCropPK();
                if (findCrop(id) == null) {
                    throw new NonexistentEntityException("The crop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CropPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crop crop;
            try {
                crop = em.getReference(Crop.class, id);
                crop.getCropPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crop with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Site> siteListOrphanCheck = crop.getSiteList();
            for (Site siteListOrphanCheckSite : siteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crop (" + crop + ") cannot be destroyed since the Site " + siteListOrphanCheckSite + " in its siteList field has a non-nullable crop1 field.");
            }
            List<Resource> resourceListOrphanCheck = crop.getResourceList();
            for (Resource resourceListOrphanCheckResource : resourceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Crop (" + crop + ") cannot be destroyed since the Resource " + resourceListOrphanCheckResource + " in its resourceList field has a non-nullable crop1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(crop);
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

    public List<Crop> findCropEntities() {
        return findCropEntities(true, -1, -1);
    }

    public List<Crop> findCropEntities(int maxResults, int firstResult) {
        return findCropEntities(false, maxResults, firstResult);
    }

    private List<Crop> findCropEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Crop.class));
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

    public Crop findCrop(CropPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Crop.class, id);
        } finally {
            em.close();
        }
    }

    public int getCropCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Crop> rt = cq.from(Crop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
