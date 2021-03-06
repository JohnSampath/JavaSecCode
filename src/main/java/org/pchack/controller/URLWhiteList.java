package org.pchack.controller;


import org.pchack.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The vulnerability code and security code of Java url whitelist.
 * The security code is checking url whitelist.
 *
 */

@RestController
@RequestMapping("/url")
public class URLWhiteList {


    private String domainwhitelist[] = {"pchack.org", "pchack.com"};
    private static final Logger logger = LoggerFactory.getLogger(URLWhiteList.class);

    /**
     * bypass poc: bypass pchack.org
     * http://localhost:8080/url/vuln/endswith?url=http://aaapchack.org
     */
    @GetMapping("/vuln/endsWith")
    public String endsWith(@RequestParam("url") String url) {

        String host = SecurityUtil.gethost(url);

        for (String domain : domainwhitelist) {
            if (host.endsWith(domain)) {
                return "Good url.";
            }
        }
        return "Bad url.";
    }


    /**
     * It's the same with <code>indexOf</code>.
     * <p>
     * http://localhost:8080/url/vuln/contains?url=http://pchack.org.bypass.com
     * http://localhost:8080/url/vuln/contains?url=http://bypasspchack.org
     */
    @GetMapping("/vuln/contains")
    public String contains(@RequestParam("url") String url) {

        String host = SecurityUtil.gethost(url);

        for (String domain : domainwhitelist) {
            if (host.contains(domain)) {
                return "Good url.";
            }
        }
        return "Bad url.";
    }


    /**
     * bypass poc: bypass pchack.org. It's the same with endsWith.
     * http://localhost:8080/url/vuln/regex?url=http://aaapchack.org
     */
    @GetMapping("/vuln/regex")
    public String regex(@RequestParam("url") String url) {

        String host = SecurityUtil.gethost(url);
        Pattern p = Pattern.compile("pchack\\.org$");
        Matcher m = p.matcher(host);

        if (m.find()) {
            return "Good url.";
        } else {
            return "Bad url.";
        }
    }


    /**
     * The bypass of using <code>java.net.URL</code> to getHost.
     * <p>
     * Bypass poc1: curl -v 'http://localhost:8080/url/vuln/url_bypass?url=http://evel.com%5c@www.pchack.org/a.html'
     * Bypass poc2: curl -v 'http://localhost:8080/url/vuln/url_bypass?url=http://evil.com%5cwww.pchack.org/a.html'
     * <p>
     * More details: https://github.com/JohnSampath/JavaSecCode/wiki/URL-whtielist-Bypass
     */
    @GetMapping("/vuln/url_bypass")
    public String url_bypass(String url) throws MalformedURLException {

        logger.info("url:  " + url);

        if (!SecurityUtil.isHttp(url)) {
            return "Url is not http or https";
        }

        URL u = new URL(url);
        String host = u.getHost();
        logger.info("host:  " + host);

        // endsWith .
        for (String domain : domainwhitelist) {
            if (host.endsWith("." + domain)) {
                return "Good url.";
            }
        }

        return "Bad url.";
    }


    /**
     * First-level & Multi-level host whitelist.
     * http://localhost:8080/url/sec?url=http://aa.pchack.org
     */
    @GetMapping("/sec")
    public String sec(@RequestParam("url") String url) {

        String whiteDomainlists[] = {"pchack.org", "pchack.com", "test.pchack.me"};

        if (!SecurityUtil.isHttp(url)) {
            return "SecurityUtil is not http or https";
        }

        String host = SecurityUtil.gethost(url);

        for (String whiteHost: whiteDomainlists){
            if (whiteHost.startsWith(".") && host.endsWith(whiteHost)) {
                return url;
            } else if (!whiteHost.startsWith(".") && host.equals(whiteHost)) {
                return url;
            }
        }

        return "Bad url.";
    }


    /**
     * http://localhost:8080/url/sec/array_indexOf?url=http://ccc.bbb.pchack.org
     */
    @GetMapping("/sec/array_indexOf")
    public String sec_array_indexOf(@RequestParam("url") String url) {

        // Define muti-level host whitelist.
        ArrayList<String> whiteDomainlists = new ArrayList<>();
        whiteDomainlists.add("bbb.pchack.org");
        whiteDomainlists.add("ccc.bbb.pchack.org");

        if (!SecurityUtil.isHttp(url)) {
            return "SecurityUtil is not http or https";
        }

        String host = SecurityUtil.gethost(url);

        if (whiteDomainlists.indexOf(host) != -1) {
            return "Good url.";
        }
        return "Bad url.";
    }

}
