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
import org.bhaduri.machh.entities.Resource;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.JPA.exceptions.IllegalOrphanException;
import org.bhaduri.machh.JPA.exceptions.NonexistentEntityException;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.JPA.exceptions.RollbackFailureException;
import org.bhaduri.machh.entities.Resourcecategory;

/**
 *
 * @author sb
 */
public class ResourcecategoryJpaController implements Serializable {

    public ResourcecategoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resourcecategory resourcecategory) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (resourcecategory.getResourceList() == null) {
            resourcecategory.setResourceList(new ArrayList<Resource>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Resource> attachedResourceList = new ArrayList<Resource>();
            for (Resource resourceListResourceToAttach : resourcecategory.getResourceList()) {
                resourceListResourceToAttach = em.getReference(resourceListResourceToAttach.getClass(), resourceListResourceToAttach.getResourcePK());
                attachedResourceList.add(resourceListResourceToAttach);
            }
            resourcecategory.setResourceList(attachedResourceList);
            em.persist(resourcecategory);
            for (Resource resourceListResource : resourcecategory.getResourceList()) {
                Resourcecategory oldResourcecategory1OfResourceListResource = resourceListResource.getResourcecategory1();
                resourceListResource.setResourcecategory1(resourcecategory);
                resourceListResource = em.merge(resourceListResource);
                if (oldResourcecategory1OfResourceListResource != null) {
                    oldResourcecategory1OfResourceListResource.getResourceList().remove(resourceListResource);
                    oldResourcecategory1OfResourceListResource = em.merge(oldResourcecategory1OfResourceListResource);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findResourcecategory(resourcecategory.getResourcecategory()) != null) {
                throw new PreexistingEntityException("Resourcecategory " + resourcecategory + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resourcecategory resourcecategory) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resourcecategory persistentResourcecategory = em.find(Resourcecategory.class, resourcecategory.getResourcecategory());
            List<Resource> resourceListOld = persistentResourcecategory.getResourceList();
            List<Resource> resourceListNew = resourcecategory.getResourceList();
            List<String> illegalOrphanMessages = null;
            for (Resource resourceListOldResource : resourceListOld) {
                if (!resourceListNew.contains(resourceListOldResource)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Resource " + resourceListOldResource + " since its resourcecategory1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Resource> attachedResourceListNew = new ArrayList<Resource>();
            for (Resource resourceListNewResourceToAttach : resourceListNew) {
                resourceListNewResourceToAttach = em.getReference(resourceListNewResourceToAttach.getClass(), resourceListNewResourceToAttach.getResourcePK());
                attachedResourceListNew.add(resourceListNewResourceToAttach);
            }
            resourceListNew = attachedResourceListNew;
            resourcecategory.setResourceList(resourceListNew);
            resourcecategory = em.merge(resourcecategory);
            for (Resource resourceListNewResource : resourceListNew) {
                if (!resourceListOld.contains(resourceListNewResource)) {
                    Resourcecategory oldResourcecategory1OfResourceListNewResource = resourceListNewResource.getResourcecategory1();
                    resourceListNewResource.setResourcecategory1(resourcecategory);
                    resourceListNewResource = em.merge(resourceListNewResource);
                    if (oldResourcecategory1OfResourceListNewResource != null && !oldResourcecategory1OfResourceListNewResource.equals(resourcecategory)) {
                        oldResourcecategory1OfResourceListNewResource.getResourceList().remove(resourceListNewResource);
                        oldResourcecategory1OfResourceListNewResource = em.merge(oldResourcecategory1OfResourceListNewResource);
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
                String id = resourcecategory.getResourcecategory();
                if (findResourcecategory(id) == null) {
                    throw new NonexistentEntityException("The resourcecategory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resourcecategory resourcecategory;
            try {
                resourcecategory = em.getReference(Resourcecategory.class, id);
                resourcecategory.getResourcecategory();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resourcecategory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Resource> resourceListOrphanCheck = resourcecategory.getResourceList();
            for (Resource resourceListOrphanCheckResource : resourceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Resourcecategory (" + resourcecategory + ") cannot be destroyed since the Resource " + resourceListOrphanCheckResource + " in its resourceList field has a non-nullable resourcecategory1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(resourcecategory);
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

    public List<Resourcecategory> findResourcecategoryEntities() {
        return findResourcecategoryEntities(true, -1, -1);
    }

    public List<Resourcecategory> findResourcecategoryEntities(int maxResults, int firstResult) {
        return findResourcecategoryEntities(false, maxResults, firstResult);
    }

    private List<Resourcecategory> findResourcecategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resourcecategory.class));
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

    public Resourcecategory findResourcecategory(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resourcecategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourcecategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resourcecategory> rt = cq.from(Resourcecategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
