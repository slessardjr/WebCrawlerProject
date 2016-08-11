package com.steve.web.crawler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.steve.web.crawler.model.Page;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SiteBrowserJsonFileImpl implements SiteBrowser {
    private DocumentContext jsonDocument;

    SiteBrowserJsonFileImpl(String filePath) throws FileNotFoundException {
        try {
            File jsonFile = new File(filePath);
            jsonDocument = JsonPath.parse(jsonFile);
        } catch (Exception e) {
            throw new FileNotFoundException(filePath);
        }
    }

    public SiteBrowserJsonFileImpl(InputStream jsonFile) throws Exception {
        jsonDocument = JsonPath.parse(jsonFile);
    }

    private static Set<String> getAllSiteLinks(String siteAddress, DocumentContext documentContext) throws Exception {
        Set<String> pageLinks = new HashSet<>();

        String xPathQuery = "$.pages[?(@.address == '" + siteAddress + "')]";
        List<Map<String, Object>> pages = documentContext.read(xPathQuery, List.class);

        if (pages != null && !pages.isEmpty()) {
            Map<String, Object> pageMap = pages.get(0);
            List<String> links = (List<String>) pageMap.get("links");
            pageLinks.addAll(links);
        } else {
            throw new JsonPageNotFoundException("Cannot Find JSON Page: " + siteAddress);
        }

        return pageLinks;
    }

    private static String getFirstPageAddress(DocumentContext documentContext) {
        String xPathQuery = "$.pages[0]";
        Page page = documentContext.read(xPathQuery, Page.class);

        return (page != null) ? page.getAddress() : null;
    }

    @Override
    public String getFirstPageAddress() {
        return getFirstPageAddress(this.jsonDocument);
    }

    @Override
    public Set<String> getAllSiteLinks(String siteAddress) throws Exception {
        return getAllSiteLinks(siteAddress, this.jsonDocument);
    }
}

