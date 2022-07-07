package com.example.SecuredMvcWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SecuredMvcWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredMvcWebApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
// add webflux dependence to support WebClient	
//	@Bean
//	public WebClient webClient(ClientRegistrationRepository clientRegistrationrepository,
//			OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
//			) {
//		
//		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = 
//				new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationrepository, 
//						oAuth2AuthorizedClientRepository);
//		
//		oauth2.setDefaultOAuth2AuthorizedClient(true);
//		
//		return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
//	}

}
