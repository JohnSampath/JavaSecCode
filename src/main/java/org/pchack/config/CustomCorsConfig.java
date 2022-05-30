package org.pchack.config;

import org.pchack.security.CustomCorsProcessor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class CustomCorsConfig implements WebMvcRegistrations {

	/**
	 * Set cors origin whitelist. Distinguish between http and https, and will not
	 * intercept requests of the same domain by default.
	 */
	@Bean
	@Lazy
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// In order to support first-level domain names, checkOrigin has been rewritten
				// String[] allowOrigins = {"pchack.org", "http://test.pchack.me"};
				registry.addMapping("/cors/sec/webMvcConfigurer") // /**means all routes path
						// .allowedOrigins(allowOrigins)
						.allowedMethods("GET", "POST").allowCredentials(true);
			}
		};
	}

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new CustomRequestMappingHandlerMapping();
	}

	/**
	 * Custom Cors processor, rewritten checkOrigin Custom verification origin,
	 * support first-level domain name verification && multi-level domain name
	 */
	private static class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
		private CustomRequestMappingHandlerMapping() {
			setCorsProcessor(new CustomCorsProcessor());
		}
	}
}
