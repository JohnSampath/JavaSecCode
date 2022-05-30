package org.pchack.security.ssrf;


/**
 * SSRFException
 *
 * @author pchack @2020-04-04
 */
public class SSRFException extends RuntimeException {

    SSRFException(String s) {
        super(s);
    }

}
