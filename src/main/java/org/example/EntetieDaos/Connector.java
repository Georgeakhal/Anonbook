package org.example.EntetieDaos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;

public class Connector {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Anonbook");
    private final EntityManager em = emf.createEntityManager();
    private final CriteriaBuilder cb = em.getCriteriaBuilder();

    private static Connector con = null;

    private Connector() {
    }

    public static Connector getInstance() {
        if (con == null) {
            con = new Connector();
        }
        return con;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public EntityManager getEm() {
        return em;
    }

    public CriteriaBuilder getCb() {
        return cb;
    }
}
