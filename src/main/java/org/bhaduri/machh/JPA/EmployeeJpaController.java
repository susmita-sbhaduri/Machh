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
import org.bhaduri.machh.entities.Operation;
import java.util.ArrayList;
import java.util.List;
import org.bhaduri.machh.JPA.exceptions.IllegalOrphanException;
import org.bhaduri.machh.JPA.exceptions.NonexistentEntityException;
import org.bhaduri.machh.JPA.exceptions.PreexistingEntityException;
import org.bhaduri.machh.JPA.exceptions.RollbackFailureException;
import org.bhaduri.machh.entities.Employee;

/**
 *
 * @author sb
 */
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (employee.getOperationList() == null) {
            employee.setOperationList(new ArrayList<Operation>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Operation> attachedOperationList = new ArrayList<Operation>();
            for (Operation operationListOperationToAttach : employee.getOperationList()) {
                operationListOperationToAttach = em.getReference(operationListOperationToAttach.getClass(), operationListOperationToAttach.getOperationPK());
                attachedOperationList.add(operationListOperationToAttach);
            }
            employee.setOperationList(attachedOperationList);
            em.persist(employee);
            for (Operation operationListOperation : employee.getOperationList()) {
                Employee oldEmployeeOfOperationListOperation = operationListOperation.getEmployee();
                operationListOperation.setEmployee(employee);
                operationListOperation = em.merge(operationListOperation);
                if (oldEmployeeOfOperationListOperation != null) {
                    oldEmployeeOfOperationListOperation.getOperationList().remove(operationListOperation);
                    oldEmployeeOfOperationListOperation = em.merge(oldEmployeeOfOperationListOperation);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmployee(employee.getId()) != null) {
                throw new PreexistingEntityException("Employee " + employee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employee persistentEmployee = em.find(Employee.class, employee.getId());
            List<Operation> operationListOld = persistentEmployee.getOperationList();
            List<Operation> operationListNew = employee.getOperationList();
            List<String> illegalOrphanMessages = null;
            for (Operation operationListOldOperation : operationListOld) {
                if (!operationListNew.contains(operationListOldOperation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Operation " + operationListOldOperation + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Operation> attachedOperationListNew = new ArrayList<Operation>();
            for (Operation operationListNewOperationToAttach : operationListNew) {
                operationListNewOperationToAttach = em.getReference(operationListNewOperationToAttach.getClass(), operationListNewOperationToAttach.getOperationPK());
                attachedOperationListNew.add(operationListNewOperationToAttach);
            }
            operationListNew = attachedOperationListNew;
            employee.setOperationList(operationListNew);
            employee = em.merge(employee);
            for (Operation operationListNewOperation : operationListNew) {
                if (!operationListOld.contains(operationListNewOperation)) {
                    Employee oldEmployeeOfOperationListNewOperation = operationListNewOperation.getEmployee();
                    operationListNewOperation.setEmployee(employee);
                    operationListNewOperation = em.merge(operationListNewOperation);
                    if (oldEmployeeOfOperationListNewOperation != null && !oldEmployeeOfOperationListNewOperation.equals(employee)) {
                        oldEmployeeOfOperationListNewOperation.getOperationList().remove(operationListNewOperation);
                        oldEmployeeOfOperationListNewOperation = em.merge(oldEmployeeOfOperationListNewOperation);
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
                Integer id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Operation> operationListOrphanCheck = employee.getOperationList();
            for (Operation operationListOrphanCheckOperation : operationListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Operation " + operationListOrphanCheckOperation + " in its operationList field has a non-nullable employee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
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

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
