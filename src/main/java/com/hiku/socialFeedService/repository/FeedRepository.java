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
        this.emf = Persistence.createEntityManagerFactory("hikuPU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Post> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void addFollow(String followerId, String followingId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
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
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void removeFollow(String followerId, String followingId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery(
                "DELETE FROM Follow f WHERE f.followerId = :followerId AND f.followingId = :followingId")
                .setParameter("followerId", followerId)
                .setParameter("followingId", followingId)
                .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Post> findPostsFromFollowed(String followerId) {
        EntityManager em = getEntityManager();
        try {
            List<String> followingIds = em.createQuery(
                "SELECT f.followingId FROM Follow f WHERE f.followerId = :followerId", String.class)
                .setParameter("followerId", followerId)
                .getResultList();

            if (followingIds.isEmpty()) {
                return List.of();
            }

            return em.createQuery(
                "SELECT p FROM Post p WHERE p.userId IN :followingIds ORDER BY p.createdAt DESC", Post.class)
                .setParameter("followingIds", followingIds)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Post> findPostsByUserId(String userId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                "SELECT p FROM Post p WHERE p.userId = :userId ORDER BY p.createdAt DESC", Post.class)
                .setParameter("userId", userId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public Post createPost(Post post) {
        System.out.println("Creating post: " + post.getTitle() + " by userId: " + post.getUserId()+" with image URL: " + post.getPostimageurl());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
            System.out.println("Created post with ID: " + post.getId());
            return post;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean deletePost(Long postId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Post post = em.find(Post.class, postId);
            if (post != null) {
                em.remove(post);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
