package com.example.configuration;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.ValidatedTokenResponse;
import com.example.error.AuthorizationException;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
	private final WebClient.Builder webClientBuilder;

	public AuthFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}

	public static class Config {
		// empty class as I don't need any particular configuration
	}

	@Override
	public GatewayFilter apply(Config config) {

		return (exchange, chain) -> {
			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new AuthorizationException("Missing authorization information");
			}

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

			String[] parts = authHeader.split(" ");

			if (parts.length != 2 || !"Bearer".equals(parts[0])) {
				throw new AuthorizationException("Incorrect authorization structure");
			}

			return webClientBuilder.build().post()
					.uri("http://user-management-service/user/validateToken?token=" + parts[1]).retrieve()
					.bodyToMono(ValidatedTokenResponse.class).map(res -> {
						exchange.getRequest().mutate().header("X-auth-user-name", String.valueOf(res.getUsername()));
						return exchange;
					}).flatMap(chain::filter);
		};

	}
}
