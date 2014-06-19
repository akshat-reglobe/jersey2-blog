package it.pajak.scry.cards.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.pajak.scry.cards.model.Card;
import it.pajak.scry.cards.service.CardsManager;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Controller
@Path("v1/cards")
@Api(value = "cards", description = "Cards")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CardsResource {

    @Autowired
    private CardsManager cardsManager;

    @GET
    @Path("/{cardId}")
    @ApiOperation(value = "Get card by identifier", response = Card.class)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Card resource"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Card not found")
    })
    public Response getById(@PathParam("cardId") String cardId) {
        Card card = cardsManager.getById(cardId);

        if (null == card) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(card).build();
    }

    @GET
    @ApiOperation(value = "Get card by name", response = Card.class)
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "Card resource"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Card not found")
    })
    public Response getByName(@QueryParam("cardName") String cardName) {
        Card card = cardsManager.getByName(cardName);

        if (null == card) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(card).build();
    }

    @POST
    @ApiOperation(value = "Create new card")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Card created successfully"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request"),
    })
    public Response create(@Context UriInfo uriInfo, @Valid @NotNull Card card) {
        Card cardFound = cardsManager.getByName(card.name);

        if (null != cardFound) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        try {
            cardsManager.create(card);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        URI resourceURI = URI.create(uriInfo.getAbsolutePath().toString() + "/" + card.id);

        return Response.created(resourceURI).build();
    }

    @DELETE
    @Path("/{cardId}")
    @ApiOperation(value = "Delete card")
    @ApiResponses(value = {
        @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Card deleted successfully"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Card not found")
    })
    public Response delete(@NotBlank @PathParam("cardId") String cardId) {
        Card card = cardsManager.getById(cardId);

        if (null == card) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        cardsManager.delete(card);

        return Response.noContent().build();
    }
}
