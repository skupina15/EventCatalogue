package si.fri.rso.skupina15.v1;


//import com.kumuluz.ee.discovery.annotations.RegisterService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//@RegisterService
@OpenAPIDefinition(info = @Info(title = "API for events", version = "v1", contact = @Contact(email = "rso@fri.uni-lj.si"), license = @
        License(name = "dev"), description = "API for events."), servers = @Server(url ="http://20.120.67.65:8080/events/v1"))
@ApplicationPath("v1")
public class EventCatalogueApplication extends Application {
}
