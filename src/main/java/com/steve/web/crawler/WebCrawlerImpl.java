package com.steve.web.crawler;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class WebCrawlerImpl implements WebCrawler {
    private final static Logger logger = LogManager.getLogger(WebCrawlerImpl.class);

    private SiteBrowser siteBrowser;
    private ForkJoinPool threadPool;
    private WebCrawlerData webCrawlerData;

    public WebCrawlerImpl(Integer maxNumberOfThreads, SiteBrowser siteBrowser, WebCrawlerData webCrawlerData) {
        logger.trace("Creating SiteBrowserJsonFileImpl Crawler Object");
        threadPool = new ForkJoinPool(maxNumberOfThreads);

        this.siteBrowser = siteBrowser;
        this.webCrawlerData = webCrawlerData;

        logger.trace("Created the SiteBrowserJsonFileImpl Crawler Object");
    }

    @Override
    public void start(String startURL) throws InterruptedException {
        logger.trace("Start method call started.");
        threadPool.invoke(new CrawlerAction(startURL, this, siteBrowser));
        stopThreads();
        logger.trace("Start method call complete.");
    }

    @Override
    public void start(String startURL, int maxDepth) throws InterruptedException {
        logger.trace("Start method call started.");
        threadPool.invoke(new CrawlerAction(startURL, this, siteBrowser, maxDepth));
        stopThreads();
        logger.trace("Start method call complete.");
    }

    private void stopThreads() throws InterruptedException {
        threadPool.shutdown();
        threadPool.awaitTermination(5, TimeUnit.MINUTES);
    }

    @Override
    public boolean hasVisitedSite(String siteAddress) {
        return webCrawlerData.hasVisitedSite(siteAddress);
    }

    @Override
    public void addVisitedSite(String siteAddress) {
        webCrawlerData.addVisitedSite(siteAddress);
    }

    @Override
    public String getReport() {
        return webCrawlerData.getReport();
    }

    @Override
    public void addSkippedSite(String siteAddress) {
        webCrawlerData.addSkippedSite(siteAddress);
    }

    @Override
    public void addErrorSite(String siteAddress) {
        webCrawlerData.addErrorSite(siteAddress);
    }

    public WebCrawlerData getWebCrawlerData() {
        return webCrawlerData;
    }
}
