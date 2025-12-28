package com.hiku.socialFeedService.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.hiku.socialFeedService.model.Post;
import com.hiku.socialFeedService.repository.FeedRepository;

import java.util.List;
@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedController {

    // Instantiate manually
    private final FeedRepository repo = new FeedRepository();

    @GET
    public List<Post> list() {
        return repo.findAll();
    }


}
