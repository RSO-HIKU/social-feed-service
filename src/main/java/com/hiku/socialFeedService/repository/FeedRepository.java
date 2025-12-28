package com.hiku.socialFeedService.repository;

import com.hiku.socialFeedService.model.Post;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ApplicationScoped
public class FeedRepository {

    private final EntityManagerFactory emf;

    public FeedRepository() {
        // Create EntityManagerFactory for RESOURCE_LOCAL unit
        this.emf = Persistence.createEntityManagerFactory("hikuPU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Post> findAll() {
        EntityManager em = getEntityManager();
        List<Post> result = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
        em.close();
        return result;
    }


}
