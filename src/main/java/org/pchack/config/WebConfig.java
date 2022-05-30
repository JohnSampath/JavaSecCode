package org.pchack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * Solve can't get value in filter by @Value when not using embed tomcat.
 *
 */
@Component // The annotation @Component indicates that the WebConfig class will be scanned and assembled by the SpringIoC container, and the bean name is webConfig
public class WebConfig {

    private static String[] callbacks;
    private static Boolean jsonpReferCheckEnabled = false;
    private static String[] jsonpRefererHost;
    private static String[] referWhitelist;
    private static String[] referUris;
    private static Boolean referSecEnabled = false;
    private static String businessCallback;
    private static ArrayList<String> safeDomains = new ArrayList<>();
    private static ArrayList<String> blockDomains = new ArrayList<>();
    private static ArrayList<String> ssrfSafeDomains = new ArrayList<>();
    private static ArrayList<String> ssrfBlockDomains = new ArrayList<>();
    private static ArrayList<String> ssrfBlockIps = new ArrayList<>();

    /**
     * In application.properties, the object is automatically converted to the referer verification switch of jsonp
     *
     * @param jsonpReferCheckEnabled jsonp check switch
     */
    @Value("${pchack.security.jsonp.referer.check.enabled}")
    public void setJsonpReferCheckEnabled(Boolean jsonpReferCheckEnabled) {
        WebConfig.jsonpReferCheckEnabled = jsonpReferCheckEnabled;
    }

    public static Boolean getJsonpReferCheckEnabled() {
        return jsonpReferCheckEnabled;
    }


    @Value("${pchack.security.jsonp.callback}")
    public void setJsonpCallbacks(String[] callbacks) {
        WebConfig.callbacks = callbacks;
    }

    public static String[] getJsonpCallbacks() {
        return callbacks;
    }


    @Value("${pchack.security.referer.enabled}")
    public void setReferSecEnabled(Boolean referSecEnabled) {
        WebConfig.referSecEnabled = referSecEnabled;
    }

    public static Boolean getReferSecEnabled() {
        return referSecEnabled;
    }


    @Value("${pchack.security.referer.host}")
    public void setReferWhitelist(String[] referWhitelist) {
        WebConfig.referWhitelist = referWhitelist;
    }

    public static String[] getReferWhitelist() {
        return referWhitelist;
    }


    @Value("${pchack.security.referer.uri}")
    public void setReferUris(String[] referUris) {
        WebConfig.referUris = referUris;
    }

    public static String[] getReferUris() {
        return referUris;
    }


    @Value("${pchack.business.callback}")
    public void setBusinessCallback(String businessCallback) {
        WebConfig.businessCallback = businessCallback;
    }

    public static String getBusinessCallback() {
        return businessCallback;
    }


    void setSafeDomains(ArrayList<String> safeDomains) {
        WebConfig.safeDomains = safeDomains;
    }

    public static ArrayList<String> getSafeDomains() {
        return safeDomains;
    }


    void setBlockDomains(ArrayList<String> blockDomains) {
        WebConfig.blockDomains = blockDomains;
    }

    public static ArrayList<String> getBlockDomains() {
        return blockDomains;
    }


    void setSsrfSafeDomains(ArrayList<String> ssrfSafeDomains) {
        WebConfig.ssrfSafeDomains = ssrfSafeDomains;
    }

    public static ArrayList<String> getSsrfSafeDomains() {
        return ssrfSafeDomains;
    }


    void setSsrfBlockDomains(ArrayList<String> ssrfBlockDomains) {
        WebConfig.ssrfBlockDomains = ssrfBlockDomains;
    }

    public static ArrayList<String> getSsrfBlockDomainsDomains() {
        return ssrfBlockDomains;
    }


    void setSsrfBlockIps(ArrayList<String> ssrfBlockIps) {
        WebConfig.ssrfBlockIps = ssrfBlockIps;
    }

    public static ArrayList<String> getSsrfBlockIps() {
        return ssrfBlockIps;
    }
}