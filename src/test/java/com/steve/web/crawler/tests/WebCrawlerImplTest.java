package com.steve.web.crawler.tests;

import com.steve.web.crawler.SiteBrowser;
import com.steve.web.crawler.WebCrawler;
import com.steve.web.crawler.WebCrawlerData;
import com.steve.web.crawler.WebCrawlerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebCrawlerImplTest {

    private WebCrawler webCrawler;
    private WebCrawlerData webCrawlerData;
    private SiteBrowser siteBrowser;

    private String testSiteUrl;

    @Before
    public void setupWebCrawler() {
        testSiteUrl = "http://test.site1.com";
        webCrawlerData = mock(WebCrawlerData.class);
        siteBrowser = mock(SiteBrowser.class);

        webCrawler = new WebCrawlerImpl(1, siteBrowser, webCrawlerData);
    }

    @Test
    public void hasNotVisitedSite() throws Exception {
        when(webCrawlerData.hasVisitedSite(testSiteUrl)).thenReturn(false);

        boolean hasVisitedSite = webCrawler.hasVisitedSite(testSiteUrl);
        Assert.assertFalse(hasVisitedSite);

    }

    @Test
    public void hasVisitedSite() throws Exception {
        when(webCrawlerData.hasVisitedSite(testSiteUrl)).thenReturn(true);

        boolean hasVisitedSite = webCrawler.hasVisitedSite(testSiteUrl);
        Assert.assertTrue(hasVisitedSite);
    }

    @Test
    public void getReport() throws Exception {
        String reportString = "Returned Report String";
        when(webCrawlerData.getReport()).thenReturn(reportString);

        Object report = webCrawler.getReport();

        Assert.assertNotNull(report);
        Assert.assertEquals(String.class, report.getClass());
        Assert.assertEquals(report, reportString);
    }

}