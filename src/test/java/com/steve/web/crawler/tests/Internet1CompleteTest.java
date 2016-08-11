package com.steve.web.crawler.tests;

import com.steve.web.crawler.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sless on 8/11/2016.
 */
public class Internet1CompleteTest {

    @Test
    public void TestInternet1Completely() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream internetJsonFileStream = classLoader.getResourceAsStream("internet1.json");
        SiteBrowser siteBrowser = new SiteBrowserJsonFileImpl(internetJsonFileStream);
        WebCrawlerData webCrawlerData = new WebCrawlerDataImpl();


        WebCrawlerImpl webCrawler = new WebCrawlerImpl(5, siteBrowser, webCrawlerData);
        String startingUrl = siteBrowser.getFirstPageAddress();
        webCrawler.start(startingUrl);

        webCrawlerData = webCrawler.getWebCrawlerData();

        List<String> successfulSites = webCrawlerData.getVisitedSites();
        List<String> skippedSites = webCrawlerData.getSkippedSites();
        List<String> errorSites = webCrawlerData.getErrorSites();

        //Successful
        Assert.assertEquals(5, successfulSites.size());
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p1"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p2"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p4"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p5"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p6"));

        //Skipped
        Assert.assertEquals(4, skippedSites.size());
        Assert.assertTrue(doesListContainsValue(skippedSites, "http://foo.bar.com/p2"));
        Assert.assertTrue(doesListContainsValue(skippedSites, "http://foo.bar.com/p4"));
        Assert.assertTrue(doesListContainsValue(skippedSites, "http://foo.bar.com/p1"));
        Assert.assertTrue(doesListContainsValue(skippedSites, "http://foo.bar.com/p5"));

        //Error
        Assert.assertEquals(2, errorSites.size());
        Assert.assertTrue(doesListContainsValue(errorSites, "http://foo.bar.com/p3"));
        Assert.assertTrue(doesListContainsValue(errorSites, "http://foo.bar.com/p7"));
    }

    private boolean doesListContainsValue(List<String> list, String value) {
        boolean foundMatch = false;
        for (String item : list) {
            if (item != null && item.equalsIgnoreCase(value)) {
                foundMatch = true;
                break;
            }
        }
        return foundMatch;
    }
}
