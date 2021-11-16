package si.fri.rso.skupina15.beans.CDI;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.skupina15.entities.Event;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class EventBean {
    private Logger log = Logger.getLogger(EventBean.class.getName());

    @PostConstruct
    private void init(){
        log.info("Initialization " + EventBean.class.getSimpleName());
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinitialization"+ EventBean.class.getSimpleName());
    }

    @PersistenceContext(unitName = "climb-jpa")
    private EntityManager em;

    public List<Event> findAllEvents(QueryParameters query) {
        return JPAUtils.queryEntities(em, Event.class, query);
    }

    public Long eventsCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Event.class, query);
    }

    @Transactional
    public Event createEvent(Event event){
        if(event != null) {
            if(event.getId_event() == null) {
                log.info("Can't create new event. ID is not defined.");
                return null;
            }
            if(event.getTitle() == null) {
                log.info("Can't create new event. Name is not defined.");
                return null;
            }
            if(event.getDescription() == null) {
                log.info("Can't create new event. Description is not defined.");
                return null;
            }
            if(event.getHost() == null) {
                log.info("Can't create new event. Host is not defined.");
                return null;
            }
            if(event.getTag() == null) {
                log.info("Can't create new event. Tags are not defined.");
                return null;
            }
            if(event.getEnd_date() == null) {
                log.info("Can't create new event. End date is not defined.");
                return null;
            }
            if(event.getStart_date() == null) {
                log.info("Can't create new event. Start date is not defined.");
                return null;
            }
            em.persist(event);
        }
        return event;
    }

    public Event findEvent(int id_event){
        try {
            return em.find(Event.class, id_event);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public Event updateEvent(int id_event, Event event){
        Event i = findEvent(id_event);
        if(i == null){
            return null;
        }
        event.setId_event(i.getId_event());
        em.merge(event);
        return event;
    }

    @Transactional
    public int deleteEvent(int id_event){
        Event i = findEvent(id_event);
        if(i != null){
            em.remove(i);
        }
        return id_event;
    }
}
