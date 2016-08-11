package com.steve.web.crawler.tests;

import com.steve.web.crawler.WebCrawlerDataImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebCrawlerDataImplTest {
    private WebCrawlerDataImpl webCrawlerData;
    private String testSiteUrl;

    @Before
    public void initWebCrawlerData() {
        webCrawlerData = new WebCrawlerDataImpl();
        testSiteUrl = "http://test.site1.com";
    }

    @Test
    public void hasVisitedSite() throws Exception {
        List<String> visitedSites = new ArrayList<>();
        visitedSites.add(testSiteUrl);
        webCrawlerData.setVisitedSites(visitedSites);

        boolean hasVisitedSite = webCrawlerData.hasVisitedSite(testSiteUrl);
        Assert.assertTrue(hasVisitedSite);
    }

    @Test
    public void hasNotVisitedSite() throws Exception {
        boolean hasVisitedSite = webCrawlerData.hasVisitedSite(testSiteUrl);
        Assert.assertFalse(hasVisitedSite);
    }

    @Test
    public void hasNotVisitedNullSite() throws Exception {
        boolean hasVisitedSite = webCrawlerData.hasVisitedSite(null);
        Assert.assertFalse(hasVisitedSite);
    }

    @Test
    public void addVisitedSite() throws Exception {
        List<String> visitedSites;

        visitedSites = webCrawlerData.getVisitedSites();
        Assert.assertNotNull(visitedSites);
        Assert.assertEquals(0, visitedSites.size());

        webCrawlerData.addVisitedSite(testSiteUrl);

        visitedSites = webCrawlerData.getVisitedSites();
        Assert.assertNotNull(visitedSites);
        Assert.assertEquals(1, visitedSites.size());
        Assert.assertEquals(testSiteUrl, visitedSites.get(0));
    }

    @Test
    public void addNullVisitedSite() {
        //try to add null, it shouldn't add
        webCrawlerData.addVisitedSite(null);

        List<String> visitedSites = webCrawlerData.getVisitedSites();
        Assert.assertEquals(0, visitedSites.size());
    }

    @Test
    public void isAddVisitedSiteSynchronized() throws NoSuchMethodException {
        Method addErrorSiteMethod = WebCrawlerDataImpl.class.getMethod("addVisitedSite", String.class);
        Assert.assertTrue(Modifier.isSynchronized(addErrorSiteMethod.getModifiers()));
    }

    @Test
    public void addSkippedSite() throws Exception {
        List<String> skippedSites;

        skippedSites = webCrawlerData.getSkippedSites();
        Assert.assertNotNull(skippedSites);
        Assert.assertEquals(0, skippedSites.size());

        webCrawlerData.addSkippedSite(testSiteUrl);

        skippedSites = webCrawlerData.getSkippedSites();
        Assert.assertNotNull(skippedSites);
        Assert.assertEquals(1, skippedSites.size());
        Assert.assertEquals(testSiteUrl, skippedSites.get(0));
    }

    @Test
    public void addNullSkippedSite() {
        //try to add null, it shouldn't add
        webCrawlerData.addSkippedSite(null);

        List<String> skippedSites = webCrawlerData.getSkippedSites();
        Assert.assertEquals(0, skippedSites.size());
    }

    @Test
    public void isAddSkippedSiteSynchronized() throws NoSuchMethodException {
        Method addErrorSiteMethod = WebCrawlerDataImpl.class.getMethod("addSkippedSite", String.class);
        Assert.assertTrue(Modifier.isSynchronized(addErrorSiteMethod.getModifiers()));
    }

    @Test
    public void addErrorSite() throws Exception {
        List<String> errorSites;

        errorSites = webCrawlerData.getErrorSites();
        Assert.assertNotNull(errorSites);
        Assert.assertEquals(0, errorSites.size());

        webCrawlerData.addErrorSite(testSiteUrl);

        errorSites = webCrawlerData.getErrorSites();
        Assert.assertNotNull(errorSites);
        Assert.assertEquals(1, errorSites.size());
        Assert.assertEquals(testSiteUrl, errorSites.get(0));
    }

    @Test
    public void addNullErrorSite() {
        //try to add null, it shouldn't add
        webCrawlerData.addErrorSite(null);

        List<String> errorSites = webCrawlerData.getErrorSites();
        Assert.assertEquals(0, errorSites.size());
    }

    @Test
    public void isAddErrorSiteSynchronized() throws NoSuchMethodException {
        Method addErrorSiteMethod = WebCrawlerDataImpl.class.getMethod("addErrorSite", String.class);
        Assert.assertTrue(Modifier.isSynchronized(addErrorSiteMethod.getModifiers()));
    }

    @Test
    public void getReport() throws Exception {
        //Don't care about the format.  Just want to make sure we get a string return
        Object report = webCrawlerData.getReport();
        Assert.assertNotNull(report);
        Assert.assertEquals(String.class, report.getClass());
    }

    @Test
    public void getVisitedSites() throws Exception {
        Object returnObject = webCrawlerData.getVisitedSites();
        Assert.assertNotNull(returnObject);
        Assert.assertEquals(Collections.synchronizedList(new ArrayList<>()).getClass(), returnObject.getClass());
    }

    @Test
    public void setVisitedSites() throws Exception {
        List<String> listToSave = Collections.synchronizedList(new ArrayList<>());
        listToSave.add(testSiteUrl);

        webCrawlerData.setVisitedSites(listToSave);

        List<String> returnList = webCrawlerData.getVisitedSites();

        Assert.assertEquals(listToSave, returnList);
    }

    @Test
    public void getSkippedSites() throws Exception {
        Object returnObject = webCrawlerData.getSkippedSites();
        Assert.assertNotNull(returnObject);
        Assert.assertEquals(Collections.synchronizedList(new ArrayList<>()).getClass(), returnObject.getClass());
    }

    @Test
    public void setSkippedSites() throws Exception {
        List<String> listToSave = Collections.synchronizedList(new ArrayList<>());
        listToSave.add(testSiteUrl);

        webCrawlerData.setSkippedSites(listToSave);

        List<String> returnList = webCrawlerData.getSkippedSites();

        Assert.assertEquals(listToSave, returnList);
    }

    @Test
    public void getErrorSites() throws Exception {
        Object returnObject = webCrawlerData.getErrorSites();
        Assert.assertNotNull(returnObject);
        Assert.assertEquals(Collections.synchronizedList(new ArrayList<>()).getClass(), returnObject.getClass());
    }

    @Test
    public void setErrorSites() throws Exception {
        List<String> listToSave = Collections.synchronizedList(new ArrayList<>());
        listToSave.add(testSiteUrl);

        webCrawlerData.setErrorSites(listToSave);

        List<String> returnList = webCrawlerData.getErrorSites();

        Assert.assertEquals(listToSave, returnList);
    }

}