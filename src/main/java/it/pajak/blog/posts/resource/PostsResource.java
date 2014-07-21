package it.pajak.blog.posts.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.pajak.blog.posts.model.Comment;
import it.pajak.blog.posts.model.Post;
import it.pajak.blog.posts.repository.PostsRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Controller
@Path("v1/posts")
@Api(value = "posts", description = "Blog posts")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PostsResource {

    @Autowired
    private PostsRepository postsRepository;

    @GET
    @ApiOperation(value = "Get blog posts", response = Post.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog post resource"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Blog post not found")
    })
    public Response list(@QueryParam("offset") @Min(0) @DefaultValue("0") int offset,
            @QueryParam("limit") @Min(1) @Max(10) @DefaultValue("10") int limit) {
        List<Post> posts = postsRepository.findAll(new PageRequest(offset, limit)).getContent();

        return Response.status(Response.Status.OK).entity(posts).build();
    }

    @GET
    @Path("/{postId}")
    @ApiOperation(value = "Get blog post by identifier", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog post resource"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Blog post not found")
    })
    public Response getOneById(@PathParam("postId") String postId) {
        Post post = postsRepository.findOne(postId);

        if (null == post) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(post).build();
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Get blog post by slug", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog post resource"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Blog post not found")
    })
    public Response getOneBySlug(@QueryParam("slug") String slug) {
        Post post = postsRepository.findOneBySlug(slug);

        if (null == post) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(post).build();
    }

    @POST
    @ApiOperation(value = "Create post")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Blog post created successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request")
    })
    public Response create(@Context UriInfo uriInfo, @Valid Post post) {
        postsRepository.save(post);

        URI resourceURI = URI.create(uriInfo.getAbsolutePath().toString() + "/" + post.id);

        return Response.created(resourceURI).build();
    }

    @PUT
    @Path("/{postId}")
    @ApiOperation(value = "Update post")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Blog post updated successfully"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Blog post not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request")
    })
    public Response update(@PathParam("postId") String postId, @Valid Post post) {
        try {
            postsRepository.updatePost(postId, post);
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/{postId}/comments")
    @ApiOperation(value = "Create comment for particular blog post", response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Blog post created successfully"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Blog post already exists")
    })
    public Response createComment(@Context UriInfo uriInfo, @NotBlank @PathParam("postId") String postId, @Valid Comment comment) {
        Post post = postsRepository.findOne(postId);

        if (null == post) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        comment.createDate = new DateTime();
        postsRepository.addComment(postId, comment);

        URI resourceURI = URI.create(uriInfo.getAbsolutePath().toString() + "/" + postId);

        return Response.created(resourceURI).build();
    }
}
