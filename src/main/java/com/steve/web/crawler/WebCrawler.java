package com.steve.web.crawler;

public interface WebCrawler {
    void addErrorSite(String siteAddress);

    void addSkippedSite(String siteAddress);

    void addVisitedSite(String siteAddress);

    String getReport();

    boolean hasVisitedSite(String siteAddress);

    void start(String startUrl) throws InterruptedException;

    void start(String startURL, int maxDepth) throws InterruptedException;
}
