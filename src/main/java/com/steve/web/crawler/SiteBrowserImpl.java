package com.steve.web.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class SiteBrowserImpl implements SiteBrowser {
    @Override
    public Set<String> getAllSiteLinks(String siteAddress) throws Exception {
        Document siteDocument = Jsoup.connect(siteAddress).get();
        Elements siteDocumentLinks = siteDocument.select("a[href]");

        return getLinks(siteDocumentLinks);
    }

    @Override
    public String getFirstPageAddress() {
        return null;
    }

    private Set<String> getLinks(Elements elements) {
        Set<String> siteLinks = new HashSet<>();

        for (Element link : elements) {
            String linkText = link.attr("href");
            if (linkText != null && !linkText.isEmpty()) {
                siteLinks.add(linkText);
            }
        }

        return siteLinks;
    }
}
