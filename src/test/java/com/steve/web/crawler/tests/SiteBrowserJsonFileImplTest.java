package com.steve.web.crawler.tests;

import com.steve.web.crawler.JsonPageNotFoundException;
import com.steve.web.crawler.SiteBrowserJsonFileImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStream;
import java.util.Set;

public class SiteBrowserJsonFileImplTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAllSiteLinksInternet1() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream internetJsonFileStream = classLoader.getResourceAsStream("internet1.json");

        SiteBrowserJsonFileImpl siteBrowser = new SiteBrowserJsonFileImpl(internetJsonFileStream);

        Set<String> p4Links = siteBrowser.getAllSiteLinks("http://foo.bar.com/p4");

        Assert.assertNotNull(p4Links);
        Assert.assertEquals(3, p4Links.size());
    }

    @Test
    public void getAllSiteLinksInternet1InvalidPage() throws Exception {
        thrown.expect(JsonPageNotFoundException.class);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream internetJsonFileStream = classLoader.getResourceAsStream("internet1.json");
        SiteBrowserJsonFileImpl siteBrowser = new SiteBrowserJsonFileImpl(internetJsonFileStream);

        siteBrowser.getAllSiteLinks("http://this.site.does.not.exist.com");
    }


    @Test
    public void getFirstPageAddressInternet1() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream internetJsonFileStream = classLoader.getResourceAsStream("internet1.json");

        SiteBrowserJsonFileImpl siteBrowser = new SiteBrowserJsonFileImpl(internetJsonFileStream);
        String firstPage = siteBrowser.getFirstPageAddress();

        Assert.assertEquals("http://foo.bar.com/p1", firstPage);
    }

}