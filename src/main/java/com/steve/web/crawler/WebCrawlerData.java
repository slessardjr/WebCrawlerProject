package com.steve.web.crawler;

import java.util.List;

public interface WebCrawlerData {
    boolean hasVisitedSite(String siteAddress);

    void addVisitedSite(String siteAddress);

    void addSkippedSite(String siteAddress);

    void addErrorSite(String siteAddress);

    List<String> getVisitedSites();

    List<String> getSkippedSites();

    List<String> getErrorSites();

    String getReport();
}
