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
import org.bhaduri.machh.entities.Crop;
import org.bhaduri.machh.entities.Resourcecategory;
import org.bhaduri.machh.entities.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.JPA.exceptions.IllegalOrphanException;
import org.bhaduri.machh.JPA.exceptions.NonexistentEntityException;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.JPA.exceptions.RollbackFailureException;
import org.bhaduri.machh.entities.Resource;
import org.bhaduri.machh.entities.ResourcePK;

/**
 *
 * @author sb
 */
public class ResourceJpaController implements Serializable {

    public ResourceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Resource resource) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (resource.getResourcePK() == null) {
            resource.setResourcePK(new ResourcePK());
        }
        if (resource.getTransactionList() == null) {
            resource.setTransactionList(new ArrayList<Transaction>());
        }
        resource.getResourcePK().setResourcecategory(resource.getResourcecategory1().getResourcecategory());
        resource.getResourcePK().setCropcategory(resource.getCrop1().getCropPK().getCropcategory());
        resource.getResourcePK().setCrop(resource.getCrop1().getCropPK().getCrop());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crop crop1 = resource.getCrop1();
            if (crop1 != null) {
                crop1 = em.getReference(crop1.getClass(), crop1.getCropPK());
                resource.setCrop1(crop1);
            }
            Resourcecategory resourcecategory1 = resource.getResourcecategory1();
            if (resourcecategory1 != null) {
                resourcecategory1 = em.getReference(resourcecategory1.getClass(), resourcecategory1.getResourcecategory());
                resource.setResourcecategory1(resourcecategory1);
            }
            List<Transaction> attachedTransactionList = new ArrayList<Transaction>();
            for (Transaction transactionListTransactionToAttach : resource.getTransactionList()) {
                transactionListTransactionToAttach = em.getReference(transactionListTransactionToAttach.getClass(), transactionListTransactionToAttach.getTransactionPK());
                attachedTransactionList.add(transactionListTransactionToAttach);
            }
            resource.setTransactionList(attachedTransactionList);
            em.persist(resource);
            if (crop1 != null) {
                crop1.getResourceList().add(resource);
                crop1 = em.merge(crop1);
            }
            if (resourcecategory1 != null) {
                resourcecategory1.getResourceList().add(resource);
                resourcecategory1 = em.merge(resourcecategory1);
            }
            for (Transaction transactionListTransaction : resource.getTransactionList()) {
                Resource oldResourceOfTransactionListTransaction = transactionListTransaction.getResource();
                transactionListTransaction.setResource(resource);
                transactionListTransaction = em.merge(transactionListTransaction);
                if (oldResourceOfTransactionListTransaction != null) {
                    oldResourceOfTransactionListTransaction.getTransactionList().remove(transactionListTransaction);
                    oldResourceOfTransactionListTransaction = em.merge(oldResourceOfTransactionListTransaction);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findResource(resource.getResourcePK()) != null) {
                throw new PreexistingEntityException("Resource " + resource + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Resource resource) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        resource.getResourcePK().setResourcecategory(resource.getResourcecategory1().getResourcecategory());
        resource.getResourcePK().setCropcategory(resource.getCrop1().getCropPK().getCropcategory());
        resource.getResourcePK().setCrop(resource.getCrop1().getCropPK().getCrop());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resource persistentResource = em.find(Resource.class, resource.getResourcePK());
            Crop crop1Old = persistentResource.getCrop1();
            Crop crop1New = resource.getCrop1();
            Resourcecategory resourcecategory1Old = persistentResource.getResourcecategory1();
            Resourcecategory resourcecategory1New = resource.getResourcecategory1();
            List<Transaction> transactionListOld = persistentResource.getTransactionList();
            List<Transaction> transactionListNew = resource.getTransactionList();
            List<String> illegalOrphanMessages = null;
            for (Transaction transactionListOldTransaction : transactionListOld) {
                if (!transactionListNew.contains(transactionListOldTransaction)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaction " + transactionListOldTransaction + " since its resource field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (crop1New != null) {
                crop1New = em.getReference(crop1New.getClass(), crop1New.getCropPK());
                resource.setCrop1(crop1New);
            }
            if (resourcecategory1New != null) {
                resourcecategory1New = em.getReference(resourcecategory1New.getClass(), resourcecategory1New.getResourcecategory());
                resource.setResourcecategory1(resourcecategory1New);
            }
            List<Transaction> attachedTransactionListNew = new ArrayList<Transaction>();
            for (Transaction transactionListNewTransactionToAttach : transactionListNew) {
                transactionListNewTransactionToAttach = em.getReference(transactionListNewTransactionToAttach.getClass(), transactionListNewTransactionToAttach.getTransactionPK());
                attachedTransactionListNew.add(transactionListNewTransactionToAttach);
            }
            transactionListNew = attachedTransactionListNew;
            resource.setTransactionList(transactionListNew);
            resource = em.merge(resource);
            if (crop1Old != null && !crop1Old.equals(crop1New)) {
                crop1Old.getResourceList().remove(resource);
                crop1Old = em.merge(crop1Old);
            }
            if (crop1New != null && !crop1New.equals(crop1Old)) {
                crop1New.getResourceList().add(resource);
                crop1New = em.merge(crop1New);
            }
            if (resourcecategory1Old != null && !resourcecategory1Old.equals(resourcecategory1New)) {
                resourcecategory1Old.getResourceList().remove(resource);
                resourcecategory1Old = em.merge(resourcecategory1Old);
            }
            if (resourcecategory1New != null && !resourcecategory1New.equals(resourcecategory1Old)) {
                resourcecategory1New.getResourceList().add(resource);
                resourcecategory1New = em.merge(resourcecategory1New);
            }
            for (Transaction transactionListNewTransaction : transactionListNew) {
                if (!transactionListOld.contains(transactionListNewTransaction)) {
                    Resource oldResourceOfTransactionListNewTransaction = transactionListNewTransaction.getResource();
                    transactionListNewTransaction.setResource(resource);
                    transactionListNewTransaction = em.merge(transactionListNewTransaction);
                    if (oldResourceOfTransactionListNewTransaction != null && !oldResourceOfTransactionListNewTransaction.equals(resource)) {
                        oldResourceOfTransactionListNewTransaction.getTransactionList().remove(transactionListNewTransaction);
                        oldResourceOfTransactionListNewTransaction = em.merge(oldResourceOfTransactionListNewTransaction);
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
                ResourcePK id = resource.getResourcePK();
                if (findResource(id) == null) {
                    throw new NonexistentEntityException("The resource with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ResourcePK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resource resource;
            try {
                resource = em.getReference(Resource.class, id);
                resource.getResourcePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resource with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transaction> transactionListOrphanCheck = resource.getTransactionList();
            for (Transaction transactionListOrphanCheckTransaction : transactionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Resource (" + resource + ") cannot be destroyed since the Transaction " + transactionListOrphanCheckTransaction + " in its transactionList field has a non-nullable resource field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Crop crop1 = resource.getCrop1();
            if (crop1 != null) {
                crop1.getResourceList().remove(resource);
                crop1 = em.merge(crop1);
            }
            Resourcecategory resourcecategory1 = resource.getResourcecategory1();
            if (resourcecategory1 != null) {
                resourcecategory1.getResourceList().remove(resource);
                resourcecategory1 = em.merge(resourcecategory1);
            }
            em.remove(resource);
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

    public List<Resource> findResourceEntities() {
        return findResourceEntities(true, -1, -1);
    }

    public List<Resource> findResourceEntities(int maxResults, int firstResult) {
        return findResourceEntities(false, maxResults, firstResult);
    }

    private List<Resource> findResourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Resource.class));
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

    public Resource findResource(ResourcePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Resource.class, id);
        } finally {
            em.close();
        }
    }

    public int getResourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Resource> rt = cq.from(Resource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
