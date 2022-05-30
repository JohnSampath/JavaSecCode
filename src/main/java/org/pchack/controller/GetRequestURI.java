package org.pchack.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The difference between getRequestURI and getServletPath.
 * Since Spring Security's <code>antMatchers("/css/**", "/js/**")</code> does not use getRequestURI, login will not be bypassed.
 * <p>
 * Details: https://pchack.org/web/security-of-getRequestURI.html
 * <p>
 * Poc:
 * http://localhost:8080/css/%2e%2e/exclued/vuln
 * http://localhost:8080/css/..;/exclued/vuln
 * http://localhost:8080/css/..;bypasswaf/exclued/vuln
 *
 */

@RestController
@RequestMapping("uri")
public class GetRequestURI {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/exclued/vuln")
    public String exclued(HttpServletRequest request) {

        String[] excluedPath = {"/css/**", "/js/**"};
        String uri = request.getRequestURI(); // Security: request.getServletPath()
        PathMatcher matcher = new AntPathMatcher();

        logger.info("getRequestURI: " + uri);
        logger.info("getServletPath: " + request.getServletPath());

        for (String path : excluedPath) {
            if (matcher.match(path, uri)) {
                return "You have bypassed the login page.";
            }
        }
        return "This is a login page >..<";
    }
}
