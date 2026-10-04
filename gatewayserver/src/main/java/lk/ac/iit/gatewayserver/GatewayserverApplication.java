package lk.ac.iit.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator lectureLinkRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route("institute-service", route -> route
						.path("/api/v1/institutes/**", "/api/v1/programs/**",
								"/api/v1/subjects/**", "/api/v1/email/**")
						.uri("lb://INSTITUTE-SERVICE"))
				.route("lecturer-service", route -> route
						.path("/api/v1/lecturers/**",
								"/api/v1/qualifications/**")
						.uri("lb://LECTURER-SERVICE"))
				.build();


	}


}
