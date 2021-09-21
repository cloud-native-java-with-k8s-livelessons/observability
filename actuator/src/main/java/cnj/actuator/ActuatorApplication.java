package cnj.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@ResponseBody
@SpringBootApplication
public class ActuatorApplication {

    private final ApplicationContext applicationContext;

    ActuatorApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostMapping
    Mono<Map<String, ReadinessState>> post() {
        AvailabilityChangeEvent.publish(this.applicationContext, ReadinessState.REFUSING_TRAFFIC);
        return Mono.just(Map.of("status", ReadinessState.REFUSING_TRAFFIC));
    }

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

}
