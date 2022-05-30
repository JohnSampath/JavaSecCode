package org.pchack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * In order not to parse the xml of the safedomain every time it is called, the parsing action is placed in the Bean.
 */
@Configuration
public class SafeDomainConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SafeDomainConfig.class);

    @Bean // @Bean represents the assembly of the object returned by the safeDomainParserf method into the SpringIOC container
    public SafeDomainParser safeDomainParser() {
        try {
            LOGGER.info("SafeDomainParser bean inject successfully!!!");
            return new SafeDomainParser();
        } catch (Exception e) {
            LOGGER.error("SafeDomainParser is null " + e.getMessage(), e);
        }
        return null;
    }

}

