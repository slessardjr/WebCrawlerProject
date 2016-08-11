package com.steve.web.crawler.tests;

import com.steve.web.crawler.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class Internet2CompleteTest {

    @Test
    public void TestInternet1FileCompletely() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream internetJsonFileStream = classLoader.getResourceAsStream("internet2.json");
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
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p3"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p4"));
        Assert.assertTrue(doesListContainsValue(successfulSites, "http://foo.bar.com/p5"));

        //Skipped
        Assert.assertEquals(1, skippedSites.size());
        Assert.assertTrue(doesListContainsValue(skippedSites, "http://foo.bar.com/p1"));

        //Error
        Assert.assertEquals(0, errorSites.size());
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
