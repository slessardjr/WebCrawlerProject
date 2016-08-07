package com.steve.web.crawler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page {
    private String address;
    private List<String> links;

    public Page() {
        links = Collections.synchronizedList(new ArrayList<String>());
    }

    public Page(String address) {
        this.address = address;
        links = new ArrayList<>();
    }

    public Page(String address, List<String> links) {
        this.address = address;
        this.links = links;
    }

    //Generate Getter and Setters below
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
