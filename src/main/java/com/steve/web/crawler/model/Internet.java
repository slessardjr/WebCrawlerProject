package com.steve.web.crawler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Internet {
    private List<Page> pages;

    public Internet() {
        pages = Collections.synchronizedList(new ArrayList<Page>());
    }


    public Page getPage(int index) throws IndexOutOfBoundsException {
        return pages.get(index);
    }

    public Page getPage(String address) {
        Page returnPage = null;

        for (Page page : pages) {
            if (page.getAddress().equalsIgnoreCase(address)) {
                returnPage = page;
                break;
            }
        }

        return returnPage;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
