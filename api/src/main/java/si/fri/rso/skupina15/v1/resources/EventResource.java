package si.fri.rso.skupina15.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.rest.beans.QueryParameters;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.rso.skupina15.beans.CDI.EventBean;
import si.fri.rso.skupina15.beans.config.RestProperties;
import si.fri.rso.skupina15.entities.Event;
import si.fri.rso.skupina15.entities.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

@Log
@ApplicationScoped
@Path("events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {
    @Inject
    private RestProperties restProperties;

    private Logger log = Logger.getLogger(EventResource.class.getName());

    @Inject
    private EventBean eventBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Returns a list of events.", summary = "List of events", tags = "events", responses = {
            @ApiResponse(responseCode = "200",
                    description = "List of events",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = Event.class))),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
            )})
    @GET
    public Response getEvents() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long count = eventBean.eventsCount(query);
        List<Event> events = eventBean.findAllEvents(query);

        List<Application> applications = new ArrayList<>();
        ServiceLoader.load(Application.class).forEach(applications::add);

        //log.info(String.valueOf(applications.size()));

        // Testing configurations
        log.info(System.getenv().get("CONFIG"));
        log.info(restProperties.getPrint());

        return Response.ok(events).header("X-Total-Count", count).build();
    }

    @Operation(description = "Returns selected event.", summary = "Selected event", tags = "event", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Selected event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))
            )})
    @GET
    @Path("{id}")
    public Response returnItems(@Parameter(description = "The id that needs to be fetched", required = true)
                                    @PathParam("id") Integer id){
        Event i = eventBean.findEvent(id);
        if (i != null){
            return Response.ok(i).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Add event.", summary = "New event", tags = "event", responses = {
            @ApiResponse(responseCode = "200",
                    description = "New event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))
            )})
    @POST
    public Response addItem(@RequestBody(description = "Created event object", required = true,
            content = @Content(schema = @Schema(implementation = Event.class))) Event i){
        Event event = eventBean.createEvent(i);
        if(event == null){
            log.info("Invalid API input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @Operation(description = "Change selected event.", summary = "Change event", tags = "event", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Changed event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))
            )})
    @PUT
    @Path("{id}")
    public Response UpdateItem(@Parameter(description = "The id that needs to be updated", required = true)
                                   @PathParam("id") Integer id,@RequestBody(description = "Created event object",
            required = true, content = @Content(schema = @Schema(implementation = Event.class)))  Event i){
        Event event = eventBean.updateEvent(id, i);
        if(event == null){
            log.info("Event for update does not exist");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @Operation(description = "Delete choosen event.", summary = "Deleted event", tags = "event", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Deleted event",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))
            )})
    @DELETE
    @Path("{id}")
    public Response deleteItem(@Parameter(description = "The id that needs to be deleted", required = true)
                                   @PathParam("id") Integer id){
        Integer event = eventBean.deleteEvent(id);
        if(event == null){
            log.info("Event does not exist");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(event).build();
    }
}
