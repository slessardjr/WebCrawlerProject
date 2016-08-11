package com.steve.web.crawler;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebCrawlerDataImpl implements WebCrawlerData {
    private final static Logger logger = org.apache.log4j.LogManager.getLogger(WebCrawlerDataImpl.class);

    private List<String> visitedSites;
    private List<String> skippedSites;
    private List<String> errorSites;

    public WebCrawlerDataImpl() {
        visitedSites = Collections.synchronizedList(new ArrayList<String>());
        skippedSites = Collections.synchronizedList(new ArrayList<String>());
        errorSites = Collections.synchronizedList(new ArrayList<String>());
    }

    @Override
    public synchronized boolean hasVisitedSite(String siteAddress) {
        return visitedSites.contains(siteAddress);
    }

    @Override
    public synchronized void addVisitedSite(String siteAddress) {
        if (siteAddress == null) return;

        logger.trace("Adding page to visited list: " + siteAddress);
        visitedSites.add(siteAddress);
    }

    @Override
    public synchronized void addSkippedSite(String siteAddress) {
        if (siteAddress == null) return;

        if (!skippedSites.contains(siteAddress)) {
            logger.trace("Adding page to skipped list: " + siteAddress);
            skippedSites.add(siteAddress);
        }
    }

    @Override
    public synchronized void addErrorSite(String siteAddress) {
        if (siteAddress == null) return;

        if (!errorSites.contains(siteAddress)) {
            logger.trace("Adding page to error list: " + siteAddress);
            errorSites.add(siteAddress);
        }
    }

    public String getReport() {
        return getReportSection("Success", visitedSites) +
                getReportSection("Skipped", skippedSites) +
                getReportSection("Error", errorSites);
    }

    private String getReportSection(String sectionName, List<String> results) {
        StringBuilder sb = new StringBuilder();

        sb.append(sectionName);
        sb.append(System.getProperty("line.separator"));
        sb.append("[");

        for (String link : results) {
            sb.append("\"").append(link).append("\",");
        }

        if (!results.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("]");
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }

    @Override
    public List<String> getVisitedSites() {
        return visitedSites;
    }

    public void setVisitedSites(List<String> visitedSites) {
        this.visitedSites = visitedSites;
    }

    @Override
    public List<String> getSkippedSites() {
        return skippedSites;
    }

    public void setSkippedSites(List<String> skippedSites) {
        this.skippedSites = skippedSites;
    }

    @Override
    public List<String> getErrorSites() {
        return errorSites;
    }

    public void setErrorSites(List<String> errorSites) {
        this.errorSites = errorSites;
    }
}
