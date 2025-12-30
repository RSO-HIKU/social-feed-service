package com.hiku.socialFeedService.repository;

import com.hiku.socialFeedService.model.Follow;
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
 public void addFollow(Long followerId, Long followingId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            // Check if already exists (due to unique constraint)
            Long count = em.createQuery(
                "SELECT COUNT(f) FROM Follow f WHERE f.followerId = :followerId AND f.followingId = :followingId", Long.class)
                .setParameter("followerId", followerId)
                .setParameter("followingId", followingId)
                .getSingleResult();
            if (count == 0) {
                Follow follow = new Follow();
                follow.setFollowerId(followerId);
                follow.setFollowingId(followingId);
                em.persist(follow);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeFollow(Long followerId, Long followingId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery(
                "DELETE FROM Follow f WHERE f.followerId = :followerId AND f.followingId = :followingId")
                .setParameter("followerId", followerId)
                .setParameter("followingId", followingId)
                .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
