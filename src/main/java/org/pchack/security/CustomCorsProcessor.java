package org.pchack.security;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;

public class CustomCorsProcessor extends DefaultCorsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CustomCorsProcessor.class);


    /**
     * Cross-domain request, this method will detect whether the request source is allowed
     *
     * @param config        CORS configuration
     * @param requestOrigin request origin
     * @return Returns the request origin if the request origin is allowed; otherwise returns null
     */
    @Override
    protected String checkOrigin(CorsConfiguration config, String requestOrigin) {

        // Support checkOrigin original domain name configuration
        String result = super.checkOrigin(config, requestOrigin);
        if (result != null) {
            return result;
        }

        if (StringUtils.isBlank(requestOrigin)) {
            return null;
        }

        return customCheckOrigin(requestOrigin);
    }


    /**
     * Custom check requestOrigin
     */
    private String customCheckOrigin(String requestOrigin) {

        if ( SecurityUtil.checkURL(requestOrigin) != null) {
            logger.info("[+] Origin: "  + requestOrigin );
            return requestOrigin;
        }
        logger.error("[-] Origin: " + requestOrigin );
        return null;
    }


}