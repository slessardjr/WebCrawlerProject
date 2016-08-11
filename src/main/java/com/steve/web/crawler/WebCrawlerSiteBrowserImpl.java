package com.steve.web.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class WebCrawlerSiteBrowserImpl implements WebCrawlerSiteBrowser {
    @Override
    public Set<String> getAllSiteLinks(String siteAddress) throws Exception {
        Set<String> siteLinks = new HashSet<>();

        Document siteDocument = Jsoup.connect(siteAddress).get();
        Elements siteDocumentLinks = siteDocument.select("a[href]");

        for (Element link : siteDocumentLinks) {
            String linkText = link.attr("href");
            if (linkText != null && !linkText.isEmpty()) {
                siteLinks.add(linkText);
            }
        }

        return siteLinks;
    }
}
