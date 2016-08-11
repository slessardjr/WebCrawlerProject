package com.steve.web.crawler;

import java.util.Set;

public interface SiteBrowser {
    Set<String> getAllSiteLinks(String siteAddress) throws Exception;
}
