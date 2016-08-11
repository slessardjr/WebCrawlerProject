package com.steve.web.crawler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sless on 8/11/2016.
 */
public class WebCrawlerImplTest {

    private WebCrawler webCrawler;

    @Before
    public void setupWebCrawler() {
        webCrawler = new WebCrawlerImpl(1, null);
    }

    @Test
    public void start() throws Exception {

    }

    @Test
    public void start1() throws Exception {

    }

    @Test
    public void hasNotVisitedSite() throws Exception {
        String site = "http://test.site1.com";
        boolean hasVisitedSite = webCrawler.hasVisitedSite(site);
        Assert.assertFalse(hasVisitedSite);
    }

    @Test
    public void hasVisitedSite() throws Exception {
        String site = "http://test.site1.com";
        webCrawler.addVisitedSite(site);

        boolean hasVisitedSite = webCrawler.hasVisitedSite(site);
        Assert.assertTrue(hasVisitedSite);
    }

    @Test
    public void addVisitedSite() throws Exception {
        String site = "http://test.site1.com";
        boolean hasVisitedSite;

        hasVisitedSite = webCrawler.hasVisitedSite(site);
        Assert.assertFalse(hasVisitedSite);

        webCrawler.addVisitedSite(site);

        hasVisitedSite = webCrawler.hasVisitedSite(site);
        Assert.assertTrue(hasVisitedSite);
    }


    @Test
    public void addSkippedSite() throws Exception {

    }

    @Test
    public void addErrorSite() throws Exception {

    }

    @Test
    public void getReport() throws Exception {

    }

}