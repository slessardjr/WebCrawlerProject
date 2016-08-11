package com.steve.web.crawler;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.steve.web.crawler.model.Page;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebCrawlerSiteBrowserJsonFileImpl implements WebCrawlerSiteBrowser {
    private DocumentContext jsonDocument;

    WebCrawlerSiteBrowserJsonFileImpl(String filePath) throws FileNotFoundException {
        try {
            File jsonFile = new File(filePath);
            jsonDocument = JsonPath.parse(jsonFile);
        } catch (Exception e) {
            throw new FileNotFoundException(filePath);
        }
    }

    WebCrawlerSiteBrowserJsonFileImpl(File jsonFile) throws FileNotFoundException {
        try {
            jsonDocument = JsonPath.parse(jsonFile);
        } catch (Exception e) {
            throw new FileNotFoundException(jsonFile.getName());
        }
    }

    @Override
    public Set<String> getAllSiteLinks(String siteAddress) throws Exception {
        Set<String> pageLinks = new HashSet<>();

        String xPathQuery = "$.pages[?(@.address == '" + siteAddress + "')]";
        List<Map<String, Object>> pages = jsonDocument.read(xPathQuery, List.class);

        if (pages != null && !pages.isEmpty()) {
            Map<String, Object> pageMap = pages.get(0);
            List<String> links = (List<String>) pageMap.get("links");
            pageLinks.addAll(links);
        } else {
            throw new Exception("JSON Page not found.");
        }

        return pageLinks;
    }

    public String getFirstPageAddress() {
        String xPathQuery = "$.pages[0]";
        Page page = jsonDocument.read(xPathQuery, Page.class);

        return (page != null) ? page.getAddress() : null;
    }
}
