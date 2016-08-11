package com.steve.web.crawler;

import java.util.Set;

public interface WebCrawlerSiteBrowser {
    Set<String> getAllSiteLinks(String siteAddress) throws Exception;
}
