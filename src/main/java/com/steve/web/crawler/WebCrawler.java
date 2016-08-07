package com.steve.web.crawler;

import com.steve.web.crawler.model.Page;

interface WebCrawler {
    void addErrorSite(String siteAddress);

    void addSkippedSite(String siteAddress);

    void addVisitedSite(String siteAddress);

    Page visitPage(String siteAddress);

    String getReport();

    boolean hasVisitedSite(String siteAddress);

    void start() throws InterruptedException;
}
