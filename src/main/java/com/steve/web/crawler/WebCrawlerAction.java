package com.steve.web.crawler;

import com.steve.web.crawler.model.Page;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;


class WebCrawlerAction extends RecursiveAction {

    private String url;
    private WebCrawler webCrawler;

    WebCrawlerAction(WebCrawler webCrawler, String url) {
        this.webCrawler = webCrawler;
        this.url = url;
    }

    @Override
    protected void compute() {
        Page page = webCrawler.visitPage(url);

        if (page != null) {
            if (!webCrawler.hasVisitedSite(page.getAddress())) {
                webCrawler.addVisitedSite(page.getAddress());

                List<RecursiveAction> linkActions = page.getLinks().stream().map(link ->
                        new WebCrawlerAction(webCrawler, link)).collect(Collectors.toList());

                invokeAll(linkActions);
            } else {
                webCrawler.addSkippedSite(page.getAddress());
            }
        } else {
            webCrawler.addErrorSite(url);
        }
    }
}
