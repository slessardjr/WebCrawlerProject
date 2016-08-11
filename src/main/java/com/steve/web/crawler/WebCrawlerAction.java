package com.steve.web.crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveAction;


class WebCrawlerAction extends RecursiveAction {
    private String url;
    private WebCrawler webCrawler;
    private WebCrawlerSiteBrowser siteBrowser;
    private boolean limitedSearch;
    private Integer maxDepth;
    private Integer currentDepth;

    WebCrawlerAction(String urlToCrawl, WebCrawler webCrawler, WebCrawlerSiteBrowser siteBrowser) {
        this.webCrawler = webCrawler;
        this.url = urlToCrawl;
        this.siteBrowser = siteBrowser;
        limitedSearch = false;
        maxDepth = 0;
        currentDepth = 0;
    }

    WebCrawlerAction(String urlToCrawl, WebCrawler webCrawler, WebCrawlerSiteBrowser siteBrowser, int maxDepth) {
        this.webCrawler = webCrawler;
        this.url = urlToCrawl;
        this.siteBrowser = siteBrowser;
        limitedSearch = true;
        this.maxDepth = maxDepth;
        currentDepth = 0;
    }

    private WebCrawlerAction(String urlToCrawl, WebCrawler webCrawler, WebCrawlerSiteBrowser siteBrowser, boolean limitedSearch, int maxDepth, int currentDepth) {
        this.webCrawler = webCrawler;
        this.url = urlToCrawl;
        this.siteBrowser = siteBrowser;
        this.limitedSearch = limitedSearch;
        this.maxDepth = maxDepth;
        this.currentDepth = currentDepth;
    }

    @Override
    protected void compute() {
        if (limitedSearch && currentDepth > maxDepth) {
            return;
        }

        if (!webCrawler.hasVisitedSite(url)) {
            try {
                Collection<String> siteLinks = siteBrowser.getAllSiteLinks(url);
                webCrawler.addVisitedSite(url);

                if (siteLinks != null && !siteLinks.isEmpty()) {
                    currentDepth++;

                    List<RecursiveAction> linkActions = new ArrayList<>();
                    for (String link : siteLinks) {
                        if (link.startsWith("/")) {
                            link = url.concat(link);
                        }
                        linkActions.add(new WebCrawlerAction(link, webCrawler, siteBrowser, limitedSearch, maxDepth, currentDepth));
                    }

                    invokeAll(linkActions);
                }
            } catch (Throwable t) {
                webCrawler.addErrorSite(url);
            }
        } else {
            webCrawler.addSkippedSite(url);
        }
    }
}
