package com.steve.web.crawler;

import com.steve.web.crawler.model.Internet;
import com.steve.web.crawler.model.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

class WebCrawlerImpl implements WebCrawler {
    private final static Logger logger = LogManager.getLogger(WebCrawlerImpl.class);
    private final Object threadLock = new Object();
    private String startURL;
    private Internet internet;
    private ForkJoinPool threadPool;

    private List<String> visitedSites;
    private List<String> skippedSites;
    private List<String> errorSites;

    WebCrawlerImpl(Internet internet, String startURL) {
        logger.trace("Creating Web Crawler Object");
        this.startURL = startURL;
        this.internet = internet;

        threadPool = new ForkJoinPool(5);

        visitedSites = Collections.synchronizedList(new ArrayList<String>());
        skippedSites = Collections.synchronizedList(new ArrayList<String>());
        errorSites = Collections.synchronizedList(new ArrayList<String>());

        logger.trace("Created the Web Crawler Object");
    }

    @Override
    public void start() throws InterruptedException {
        logger.trace("Start method call started.");
        threadPool.invoke(new WebCrawlerAction(this, this.startURL));
        logger.trace("Start method call complete.");
    }

    @Override
    public boolean hasVisitedSite(String siteAddress) {
        synchronized (threadLock) {
            return visitedSites.contains(siteAddress);
        }
    }

    @Override
    public void addVisitedSite(String siteAddress) {
        synchronized (threadLock) {
            logger.trace("Adding page to visited list: " + siteAddress);
            visitedSites.add(siteAddress);
        }
    }

    @Override
    public void addSkippedSite(String siteAddress) {
        synchronized (threadLock) {
            if (!skippedSites.contains(siteAddress)) {
                logger.trace("Adding page to skipped list: " + siteAddress);
                skippedSites.add(siteAddress);
            }
        }
    }

    @Override
    public void addErrorSite(String siteAddress) {
        synchronized (threadLock) {
            if (!errorSites.contains(siteAddress)) {
                logger.trace("Adding page to error list: " + siteAddress);
                errorSites.add(siteAddress);
            }
        }
    }

    @Override
    public String getReport() {
        return getReportSection("Success", visitedSites) +
                getReportSection("Skipped", skippedSites) +
                getReportSection("Error", errorSites);
    }

    @Override
    public Page visitPage(String siteAddress) {
        logger.trace("Visiting Page: " + siteAddress);
        return internet.getPage(siteAddress);
    }

    private String getReportSection(String sectionName, List<String> results) {
        StringBuilder sb = new StringBuilder();

        sb.append(sectionName);
        sb.append(System.getProperty("line.separator"));
        sb.append("[");

        for (String link : results) {
            sb.append("\"").append(link).append("\",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }
}
