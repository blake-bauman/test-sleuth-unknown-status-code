package com.example.demo;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;


@SpringBootApplication
public class DemoApplication {

	private static int port = 0;

	public static void main(final String[] args) {
		final WireMockConfiguration options = wireMockConfig().dynamicPort();
		final WireMockServer wireMockServer = new WireMockServer(options);
		wireMockServer.start();

		port = wireMockServer.port();
		configureFor(port);

		givenThat(get(urlEqualTo("/hello")).willReturn(status(499).withBody("Foo Bar")));

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> helloWorld(final WebClient.Builder builder) {
		return route(GET("/hello"), request -> {
		    final Mono<String> result = builder.build()
                                               .get()
                                               .uri("http://localhost:{port}/hello", port)
                                               .exchange()
                                               .flatMap(clientResponse -> Mono.just("Hello World: " + clientResponse.rawStatusCode()));
			return ServerResponse.ok().contentType(TEXT_PLAIN).body(result, String.class);
		});
	}
}
