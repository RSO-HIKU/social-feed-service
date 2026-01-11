package com.hiku.socialFeedService.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hiku.socialFeedService.model.Post;
import com.hiku.socialFeedService.repository.FeedRepository;

import java.util.List;
@Path("/feed")
@RolesAllowed("user")
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
    public List<Post> getFeed(@PathParam("followerId") String followerId) {
        return repo.findPostsFromFollowed(followerId);
    }

@OPTIONS
@Path("{any: .*}")
public Response options() {
    return Response.ok().build();
}

@POST
@Path("/post")
public Post createPostAtPostPath(Post post) {
    try {
        return repo.createPost(post);
    } catch (Exception e) {
        e.printStackTrace();
        throw new InternalServerErrorException("Failed to create post");
    }
}

    @GET
    @Path("/postFrom/{userId}")
    public List<Post> getUserPosts(@PathParam("userId") String userId) {
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
