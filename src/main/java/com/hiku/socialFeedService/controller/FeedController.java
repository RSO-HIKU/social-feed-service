package com.hiku.socialFeedService.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.hiku.socialFeedService.model.Post;
import com.hiku.socialFeedService.repository.FeedRepository;

import java.util.List;
@Path("/feed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedController {

    // Instantiate manually
    private final FeedRepository repo = new FeedRepository();

    @GET
    public List<Post> list() {
        return repo.findAll();
    }



    @GET
    @Path("/followingPosts/{followerId}")
    public List<Post> getFeed(@PathParam("followerId") Long followerId) {
        return repo.findPostsFromFollowed(followerId);
    }



    @POST
    public Post createPost(Post post) {
        return repo.createPost(post);
    }

    @GET
    @Path("/postFrom/{userId}")
    public List<Post> getUserPosts(@PathParam("userId") Long userId) {
        return repo.findPostsByUserId(userId);
    }
    @DELETE
    @Path("/post/{postId}")
    public void deletePost(@PathParam("postId") Long postId) {
        boolean deleted = repo.deletePost(postId);
        if (!deleted) {
            throw new NotFoundException("Post not found");
        }
    }



}
