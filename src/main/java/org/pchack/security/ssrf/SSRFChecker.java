package org.pchack.security.ssrf;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.pchack.config.WebConfig;
import org.pchack.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SSRFChecker {

    private static Logger logger = LoggerFactory.getLogger(SSRFChecker.class);

    public static boolean checkURLFckSSRF(String url) {
        if (null == url) {
            return false;
        }

        ArrayList<String> ssrfSafeDomains = WebConfig.getSsrfSafeDomains();
        try {
            String host = SecurityUtil.gethost(url);

            // must be http/https
            if (!SecurityUtil.isHttp(url)) {
                return false;
            }

            if (ssrfSafeDomains.contains(host)) {
                return true;
            }
            for (String ssrfSafeDomain : ssrfSafeDomains) {
                if (host.endsWith("." + ssrfSafeDomain)) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
        return false;
    }

    /**
     * Parse the ip of the url to determine whether the ip is an intranet ip, so the case where the TTL is set to 0 is not applicable.
     * The url only allows https or http, and sets the default connection timeout.
     * The fix actively requests the redirected link.
     *
     * @param url        check url
     * @param checkTimes Set the maximum number of redirect detections, it is recommended to set it to 10 times

     * @return Returns true for safe, false for danger
     */
    public static boolean checkSSRF(String url, int checkTimes) {

        HttpURLConnection connection;
        int connectTime = 5 * 1000;  // Set the connection timeout to 5s
        int i = 1;
        String finalUrl = url;
        try {
            do {
                // Determine whether the currently requested URL is an intranet ip
                if (isInternalIpByUrl(finalUrl)) {
                    logger.error("[-] SSRF check failed. Dangerous url: " + finalUrl);
                    return false;  // The intranet ip returns directly, and the non-intranet ip continues to judge whether there is a redirect
                }

                connection = (HttpURLConnection) new URL(finalUrl).openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setUseCaches(false); // Set to false, handle the jump manually, you can get the URL of each jump
                connection.setConnectTimeout(connectTime);
                //connection.setRequestMethod("GET");
                connection.connect(); // send dns request
                int responseCode = connection.getResponseCode(); // initiate a network request
                if (responseCode >= 300 && responseCode <= 307 && responseCode != 304 && responseCode != 306) {
                    String redirectedUrl = connection.getHeaderField("Location");
                    if (null == redirectedUrl)
                        break;
                    finalUrl = redirectedUrl;
                    i += 1;  // increment the number of redirects by 1
                    logger.info("redirected url: " + finalUrl);
                    if (i == checkTimes) {
                        return false;
                    }
                } else
                    break;
            } while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
            connection.disconnect();
        } catch (Exception e) {
            return true;  // If there is an exception, it is considered safe to prevent an exception caused by a timeout and the verification is unsuccessful.
        }
        return true; // returns true by default
    }


    /**
     * Determine whether the IP of a URL is an intranet IP
     *
     * @return If it is an intranet IP, return true; if it is not an intranet IP, return false.
     */
    public static boolean isInternalIpByUrl(String url) {

        String host = url2host(url);
        if (host.equals("")) {
            return true; // Abnormal URL is treated as illegal URL such as intranet IP
        }

        String ip = host2ip(host);
        if (ip.equals("")) {
            return true; // If the domain name is abnormally converted to IP, it is considered an illegal URL
        }

        return isInternalIp(ip);
    }


    /**
     * Use the SubnetUtils library to determine whether the ip is on the intranet segment
     *
     * @param strIP ip string
     * @return Returns true if it is an intranet ip, otherwise returns false.
     */
    static boolean isInternalIp(String strIP) {
        if (StringUtils.isEmpty(strIP)) {
            logger.error("[-] SSRF check failed. IP is empty. " + strIP);
            return true;
        }

        ArrayList<String> blackSubnets = WebConfig.getSsrfBlockIps();
        for (String subnet : blackSubnets) {
            SubnetUtils utils = new SubnetUtils(subnet);
            if (utils.getInfo().isInRange(strIP)) {
                logger.error("[-] SSRF check failed. Internal IP: " + strIP);
                return true;
            }
        }

        return false;

    }

    /**
     * host to IP
     * Convert various ip to normal ip
     * 167772161 Convert various ip to normal ip  10.0.0.1
     * 127.0.0.1.xip.io convert to 127.0.0.1
     *
     * @param host domain name host
     */
    private static String host2ip(String host) {
        try {
            InetAddress IpAddress = InetAddress.getByName(host); //  send dns request
            return IpAddress.getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get host from URL, limited to http/https protocol. Only supports http:// and https://, does not support the http protocol of //.
     *
     * @param url http url
     */
    private static String url2host(String url) {
        try {
            // Use a URI, not a URL, to prevent bypassing.
            URI u = new URI(url);
            if (SecurityUtil.isHttp(url)) {
                return u.getHost();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
