package org.pchack.security;

import org.pchack.config.WebConfig;
import org.pchack.security.ssrf.SSRFChecker;
import org.pchack.security.ssrf.SocketHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class SecurityUtil {

    private static final Pattern FILTER_PATTERN = Pattern.compile("^[a-zA-Z0-9_/\\.-]+$");
    private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);


    /**
     * Determine if the URL starts with HTTP.
     *
     * @param url url
     * @return true or false
     */
    public static boolean isHttp(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }


    /**
     * Get http url host.
     *
     * @param url url
     * @return host
     */
    public static String gethost(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost().toLowerCase();
        } catch (URISyntaxException e) {
            return "";
        }
    }


    /**
     * Both first-level domain names and multi-level domain names are supported. The relevant configuration is in the url/url_safe_domain.xml file in the resources directory.
     * The blacklist is judged first, and if the blacklist is satisfied, return null.
     *
     * @param url the url need to check
     * @return Safe url returns original url; Illegal url returns null;
     */
    public static String checkURL(String url) {

        if (null == url){
            return null;
        }

        ArrayList<String> safeDomains = WebConfig.getSafeDomains();
        ArrayList<String> blockDomains = WebConfig.getBlockDomains();

        try {
            String host = gethost(url);

            // Must be http/https
            if (!isHttp(url)) {
                return null;
            }

            // Returns null if blacklist is met
            if (blockDomains.contains(host)){
                return null;
            }
            for(String blockDomain: blockDomains) {
                if(host.endsWith("." + blockDomain)) {
                    return null;
                }
            }

            // Support multi-level domain names
            if (safeDomains.contains(host)){
                return url;
            }

            // Support first-level domain names
            for(String safedomain: safeDomains) {
                if(host.endsWith("." + safedomain)) {
                    return url;
                }
            }
            return null;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }


    /**
     * Handle SSRF vulnerabilities by custom whitelisting domain names. This scheme is strongly recommended if the URL range converges.
     * This is the easiest and most effective way to fix. Because SSRF is caused by initiating a URL request,
     *  most of the scenarios are image scenarios, and the domain names of general images are CDN or OSS, etc., so SSRF vulnerability repair can be completed by limiting the domain name whitelist.

     *
     * @param url url to be verified
     * @return Safe url returns true. Dangerous url returns false.
     */
    public static boolean checkSSRFByWhitehosts(String url) {
        return SSRFChecker.checkURLFckSSRF(url);
    }


    /**
     * Parse the IP of the URL to determine whether the IP is an intranet IP. If there is a redirect jump, the IP of the redirect jump will be parsed circularly. This scheme is not recommended.
     *
     * Existing problems:
     *   1、Will actively initiate the request, there may be performance problems
     *   2、Set the redirection jump to the first 302 without jumping, and the second 302 jumping to the intranet IP to bypass the defense scheme
     *   3、TTL set to 0 will be bypassed
     *
     * @param url check url
     * @return Returns true for safe, false for danger

     */
    @Deprecated
    public static boolean checkSSRF(String url) {
        int checkTimes = 10;
        return SSRFChecker.checkSSRF(url, checkTimes);
    }


    /**
     * This solution is recommended when a whitelist cannot be used. Provided that redirection is disabled and TTL is not 0 by default.
     *
     * There is a problem:
     *  1、TTL of 0 will be bypassed
     *  2、Use redirection to bypass
     *
     * @param url The url that needs to check.
     * @return Safe url returns true. Dangerous url returns false.
     */
    public static boolean checkSSRFWithoutRedirect(String url) {
        if(url == null) {
            return false;
        }
        return !SSRFChecker.isInternalIpByUrl(url);
    }

    /**
     * Check ssrf by hook socket. Start socket hook.
     *
     */
    public static void startSSRFHook() throws IOException {
        SocketHook.startHook();
    }

    /**
     * Close socket hook.
     *
     * @author liergou @ 2020-04-04 02:15
     **/
    public static void stopSSRFHook(){
        SocketHook.stopHook();
    }



    /**
     * Filter file path to prevent path traversal vulns.
     *
     * @param filepath file path
     * @return illegal file path return null
     */
    public static String pathFilter(String filepath) {
        String temp = filepath;

        // use while to sovle multi urlencode
        while (temp.indexOf('%') != -1) {
            try {
                temp = URLDecoder.decode(temp, "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.info("Unsupported encoding exception: " + filepath);
                return null;
            } catch (Exception e) {
                logger.info(e.toString());
                return null;
            }
        }

        if (temp.contains("..") || temp.charAt(0) == '/') {
            return null;
        }

        return filepath;
    }


    public static String cmdFilter(String input) {
        if (!FILTER_PATTERN.matcher(input).matches()) {
            return null;
        }

        return input;
    }


    /**
     * Filter the cases where # in order by cannot be used in mybatis.
     * Strictly restrict user input to contain only <code>a-zA-Z0-9_-.</code> characters.
     *
     * @param sql sql
     * @return safe sql, otherwise null
     */
    public static String sqlFilter(String sql) {
        if (!FILTER_PATTERN.matcher(sql).matches()) {
            return null;
        }
        return sql;
    }

    /**
     * Replace characters other than <code>0-9a-zA-Z/-.</code> with empty
     *
     * @param str string
     * @return the filtered string
     */
    public static String replaceSpecialStr(String str) {
        StringBuilder sb = new StringBuilder();
        str = str.toLowerCase();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // if it is 0-9
            if (ch >= 48 && ch <= 57 ){
                sb.append(ch);
            }
            // if a-z
            else if(ch >= 97 && ch <= 122) {
                sb.append(ch);
            }
            else if(ch == '/' || ch == '.' || ch == '-'){
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
    }

}