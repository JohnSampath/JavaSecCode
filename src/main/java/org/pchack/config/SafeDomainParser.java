package org.pchack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class SafeDomainParser {

    private static Logger logger = LoggerFactory.getLogger(SafeDomainParser.class);

    public SafeDomainParser() {

        String rootTag = "domains";
        String safeDomainTag = "safedomains";
        String blockDomainTag = "blockdomains";
        String finalTag = "domain";
        String safeDomainClassPath = "url" + File.separator + "url_safe_domain.xml";
        ArrayList<String> safeDomains = new ArrayList<>();
        ArrayList<String> blockDomains = new ArrayList<>();

        try {
            // Read the files in the resources directory
            ClassPathResource resource = new ClassPathResource(safeDomainClassPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(resource.getInputStream());  // parse xml

            NodeList rootNode = doc.getElementsByTagName(rootTag);  // Parse the root node domains
            Node domainsNode = rootNode.item(0);
            NodeList child = domainsNode.getChildNodes();

            for (int i = 0; i < child.getLength(); i++) {
                Node node = child.item(i);
                // Parse the safeDomains node
                if (node.getNodeName().equals(safeDomainTag)) {
                    NodeList tagChild = node.getChildNodes();
                    for (int j = 0; j < tagChild.getLength(); j++) {
                        Node finalTagNode = tagChild.item(j);
                        // Parse the domain node in the safeDomains node
                        if (finalTagNode.getNodeName().equals(finalTag)) {
                            safeDomains.add(finalTagNode.getTextContent());
                        }
                    }
                } else if (node.getNodeName().equals(blockDomainTag)) {
                    NodeList finalTagNode = node.getChildNodes();
                    for (int j = 0; j < finalTagNode.getLength(); j++) {
                        Node tagNode = finalTagNode.item(j);
                        // Parse the domain node in the blockDomains node
                        if (tagNode.getNodeName().equals(finalTag)) {
                            blockDomains.add(tagNode.getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        WebConfig wc = new WebConfig();
        wc.setSafeDomains(safeDomains);
        logger.info(safeDomains.toString());
        wc.setBlockDomains(blockDomains);

        // Parse SSRF configuration
        String ssrfRootTag = "ssrfsafeconfig";
        String ssrfSafeDomainTag = "safedomains";
        String ssrfBlockDomainTag = "blockdomains";
        String ssrfBlockIpsTag = "blockips";
        String ssrfFinalTag = "domain";
        String ssrfIpFinalTag = "ip";
        String ssrfSafeDomainClassPath = "url" + File.separator + "ssrf_safe_domain.xml";

        ArrayList<String> ssrfSafeDomains = new ArrayList<>();
        ArrayList<String> ssrfBlockDomains = new ArrayList<>();
        ArrayList<String> ssrfBlockIps = new ArrayList<>();

        try {
            // Read the files in the resources directory
            ClassPathResource resource = new ClassPathResource(ssrfSafeDomainClassPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Fix the bug that the file cannot be read when it is packaged into a jar package to run
            Document doc = db.parse(resource.getInputStream());  // parse xml

            NodeList rootNode = doc.getElementsByTagName(ssrfRootTag);  // Parse the root node
            Node domainsNode = rootNode.item(0);
            NodeList child = domainsNode.getChildNodes();

            for (int i = 0; i < child.getLength(); i++) {
                Node node = child.item(i);
                // Parse the safeDomains node
                if (node.getNodeName().equals(ssrfSafeDomainTag)) {
                    NodeList tagChild = node.getChildNodes();
                    for (int j = 0; j < tagChild.getLength(); j++) {
                        Node tagFinalNode = tagChild.item(j);
                        if (tagFinalNode.getNodeName().equals(ssrfFinalTag)) {
                            ssrfSafeDomains.add(tagFinalNode.getTextContent());
                        }
                    }
                } else if (node.getNodeName().equals(ssrfBlockDomainTag)) {
                    NodeList tagChild = node.getChildNodes();
                    for (int j = 0; j < tagChild.getLength(); j++) {
                        Node tagFinalNode = tagChild.item(j);
                        if (tagFinalNode.getNodeName().equals(ssrfFinalTag)) {
                            ssrfBlockDomains.add(tagFinalNode.getTextContent());
                        }
                    }
                } else if (node.getNodeName().equals(ssrfBlockIpsTag)) {
                    NodeList tagChild = node.getChildNodes();
                    for (int j = 0; j < tagChild.getLength(); j++) {
                        Node tagFinalNode = tagChild.item(j);
                        // Parse the ip node in the blockIps node
                        if (tagFinalNode.getNodeName().equals(ssrfIpFinalTag)) {
                            ssrfBlockIps.add(tagFinalNode.getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        logger.info(ssrfBlockIps.toString());
        wc.setSsrfBlockDomains(ssrfBlockDomains);
        wc.setSsrfBlockIps(ssrfBlockIps);
        wc.setSsrfSafeDomains(ssrfSafeDomains);
    }
}




