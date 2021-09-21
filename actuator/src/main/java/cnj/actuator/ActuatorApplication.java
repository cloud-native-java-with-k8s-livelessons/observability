package cnj.actuator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.Map;

@SpringBootApplication
public class ActuatorApplication {

	@Bean
	HealthIndicator bootifulHealthIndicator() {
		return () -> Health.status("I <3 Production!").build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ActuatorApplication.class, args);
	}
}


@Controller
@ResponseBody
class MetricsController {

	private final MeterRegistry registry;
	private final Counter counter;

	MetricsController(MeterRegistry registry) {
		this.registry = registry;
		this.counter = this.registry.counter("demo-counter");
	}

	@PostMapping("/increment")
	void increment() {
		this.counter.increment();
	}
}

@Controller
@ResponseBody
class HealthRestController {

	private final ApplicationContext applicationContext;

	HealthRestController(
		ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@EventListener
	public void onEvent(AvailabilityChangeEvent<LivenessState> event) {

		var message = switch (event.getState()) {
			case BROKEN -> "ruh roh!";
			case CORRECT -> "everything's A-OK!";
		};
		System.out.println("the " + AvailabilityChangeEvent.class.getName() +
			" has changed. It is now " + message);

	}

	@PostMapping("/green")
	Mono<Map<String, LivenessState>> green() {
		var state = LivenessState.CORRECT;
		AvailabilityChangeEvent.publish(this.applicationContext, state);
		return Mono.just(Map.of("status", state));
	}

	@PostMapping("/red")
	Mono<Map<String, LivenessState>> red() {
		var state = LivenessState.BROKEN;
		AvailabilityChangeEvent.publish(this.applicationContext, state);
		return Mono.just(Map.of("status", state));
	}
}