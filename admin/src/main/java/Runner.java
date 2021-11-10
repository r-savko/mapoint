import net.mapoint.service.LocationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/common-spring-context.xml");
        LocationService locationService = ctx.getBean(LocationService.class);
        locationService.getAll();

    }
}