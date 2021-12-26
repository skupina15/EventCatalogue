package si.fri.rso.skupina15.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.rso.skupina15.beans.CDI.EventBean;
import si.fri.rso.skupina15.beans.config.RestProperties;
import si.fri.rso.skupina15.entities.Event;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

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

    @GET
    public Response getEvents() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        Long count = eventBean.eventsCount(query);
        List<Event> events = eventBean.findAllEvents(query);

        // Testing configurations
        log.info(System.getenv().get("CONFIG"));
        log.info(restProperties.getPrint());

        return Response.ok(events).header("X-Total-Count", count).build();
    }

    @GET
    @Path("{id}")
    public Response returnItems(@PathParam("id") Integer id){
        Event i = eventBean.findEvent(id);
        if (i != null){
            return Response.ok(i).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addItem(Event i){
        Event event = eventBean.createEvent(i);
        if(event == null){
            log.info("Invalid API input.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @PUT
    @Path("{id}")
    public Response UpdateItem(@PathParam("id") Integer id, Event i){
        Event event = eventBean.updateEvent(id, i);
        if(event == null){
            log.info("Event for update does not exist");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(event).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteItem(@PathParam("id") Integer id){
        Integer event = eventBean.deleteEvent(id);
        if(event == null){
            log.info("Event does not exist");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(event).build();
    }
}
