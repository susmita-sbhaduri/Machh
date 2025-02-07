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
import org.bhaduri.machh.entities.Resource;
import org.bhaduri.machh.entities.Transaction;
import org.bhaduri.machh.entities.TransactionPK;

/**
 *
 * @author sb
 */
public class TransactionJpaController implements Serializable {

    public TransactionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaction transaction) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (transaction.getTransactionPK() == null) {
            transaction.setTransactionPK(new TransactionPK());
        }
        transaction.getTransactionPK().setCrop(transaction.getResource().getResourcePK().getCrop());
        transaction.getTransactionPK().setResourcetype(transaction.getResource().getResourcePK().getResourcetype());
        transaction.getTransactionPK().setCropcategory(transaction.getResource().getResourcePK().getCropcategory());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Resource resource = transaction.getResource();
            if (resource != null) {
                resource = em.getReference(resource.getClass(), resource.getResourcePK());
                transaction.setResource(resource);
            }
            em.persist(transaction);
            if (resource != null) {
                resource.getTransactionList().add(transaction);
                resource = em.merge(resource);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTransaction(transaction.getTransactionPK()) != null) {
                throw new PreexistingEntityException("Transaction " + transaction + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaction transaction) throws NonexistentEntityException, RollbackFailureException, Exception {
        transaction.getTransactionPK().setCrop(transaction.getResource().getResourcePK().getCrop());
        transaction.getTransactionPK().setResourcetype(transaction.getResource().getResourcePK().getResourcetype());
        transaction.getTransactionPK().setCropcategory(transaction.getResource().getResourcePK().getCropcategory());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction persistentTransaction = em.find(Transaction.class, transaction.getTransactionPK());
            Resource resourceOld = persistentTransaction.getResource();
            Resource resourceNew = transaction.getResource();
            if (resourceNew != null) {
                resourceNew = em.getReference(resourceNew.getClass(), resourceNew.getResourcePK());
                transaction.setResource(resourceNew);
            }
            transaction = em.merge(transaction);
            if (resourceOld != null && !resourceOld.equals(resourceNew)) {
                resourceOld.getTransactionList().remove(transaction);
                resourceOld = em.merge(resourceOld);
            }
            if (resourceNew != null && !resourceNew.equals(resourceOld)) {
                resourceNew.getTransactionList().add(transaction);
                resourceNew = em.merge(resourceNew);
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
                TransactionPK id = transaction.getTransactionPK();
                if (findTransaction(id) == null) {
                    throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TransactionPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Transaction transaction;
            try {
                transaction = em.getReference(Transaction.class, id);
                transaction.getTransactionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaction with id " + id + " no longer exists.", enfe);
            }
            Resource resource = transaction.getResource();
            if (resource != null) {
                resource.getTransactionList().remove(transaction);
                resource = em.merge(resource);
            }
            em.remove(transaction);
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

    public List<Transaction> findTransactionEntities() {
        return findTransactionEntities(true, -1, -1);
    }

    public List<Transaction> findTransactionEntities(int maxResults, int firstResult) {
        return findTransactionEntities(false, maxResults, firstResult);
    }

    private List<Transaction> findTransactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaction.class));
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

    public Transaction findTransaction(TransactionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaction> rt = cq.from(Transaction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
