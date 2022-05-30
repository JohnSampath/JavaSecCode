package org.pchack.controller;

import org.pchack.security.SecurityUtil;
import org.pchack.util.LoginUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * https://github.com/pchack93/java-sec-code/wiki/CORS
 */

@RestController
@RequestMapping("/cors")
public class Cors {

    private static String info = "{\"name\": \"pchack\", \"phone\": \"18200001111\"}";

    @GetMapping("/vuln/origin")
    public String vuls1(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", origin); // set origin from header
        response.setHeader("Access-Control-Allow-Credentials", "true");  // allow cookie
        return info;
    }

    @GetMapping("/vuln/setHeader")
    public String vuls2(HttpServletResponse response) {
        // When the backend sets Access-Control-Allow-Origin to *, the front-end will be abnormal if withCredentials is set to true when cross-domain
        response.setHeader("Access-Control-Allow-Origin", "*");
        return info;
    }


    @GetMapping("*")
    @RequestMapping("/vuln/crossOrigin")
    public String vuls3() {
        return info;
    }


    /**
     * Rewrite Cors' checkOrigin verification method
     * Support custom checkOrigin, let it additionally support first-level domain name
     * Code: org/pchack/security/CustomCorsProcessor
     */
    @CrossOrigin(origins = {"pchack.org", "http://test.pchack.me"})
    @GetMapping("/sec/crossOrigin")
    public String secCrossOrigin() {
        return info;
    }


    /**
     * WebMvcConfigurer set Cors
     * Support custom checkOrigin
     * Code: org/pchack/config/CorsConfig.java
     */
    @GetMapping("/sec/webMvcConfigurer")
    public CsrfToken getCsrfToken_01(CsrfToken token) {
        return token;
    }


    /**
     * spring security set cors
     * Custom checkOrigin is not supported because spring security takes precedence over setCorsProcessor execution
     * Code： org/pchack/security/WebSecurityConfig.java
     */
    @GetMapping("/sec/httpCors")
    public CsrfToken getCsrfToken_02(CsrfToken token) {
        return token;
    }


    /**
     * Custom filter settings cors
     * Support custom checkOrigin
     * Code: org/pchack/filter/OriginFilter.java
     */
    @GetMapping("/sec/originFilter")
    public CsrfToken getCsrfToken_03(CsrfToken token) {
        return token;
    }


    /**
     * CorsFilter sets cors.
     * Custom checkOrigin is not supported because corsFilter takes precedence over setCorsProcessor execution
     * Code：org/pchack/filter/BaseCorsFilter.java
     */
    @RequestMapping("/sec/corsFilter")
    public CsrfToken getCsrfToken_04(CsrfToken token) {
        return token;
    }


    @GetMapping("/sec/checkOrigin")
    public String seccode(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");

        // If origin is not empty and origin is not in the whitelist, it is considered unsafe.
        // If origin is empty, it means that it is a request from the same domain or a request directly initiated by the browser.
        if (origin != null && SecurityUtil.checkURL(origin) == null) {
            return "Origin is not safe.";
        }
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return LoginUtils.getUserInfo2JsonStr(request);
    }


}