package com.steve.web.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steve.web.crawler.model.Internet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        List<String> filesToProcess = new ArrayList<>();
        filesToProcess.add("internet1.json");
        filesToProcess.add("internet2.json");

        for (String file : filesToProcess) {
            try {
                Internet internet = loadJSONInternet(file);
                Runnable internetRunnable = () -> {
                    WebCrawler webCrawler = new WebCrawlerImpl(internet, internet.getPage(0).getAddress());
                    try {
                        webCrawler.start();
                        String internetResult = file + System.getProperty("line.separator") +
                                webCrawler.getReport();

                        logger.info(internetResult);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                };

                Thread internetThread = new Thread(internetRunnable);
                internetThread.start();

            } catch (Throwable t) {
                logger.error("Error in Internet Process for " + file, t);
                t.printStackTrace();
            }
        }
    }

    private static Internet loadJSONInternet(String filePath) throws FileNotFoundException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        File internetFile;
        try {
            internetFile = new File(classLoader.getResource(filePath).getFile());
        } catch (Exception e) {
            throw new FileNotFoundException(filePath);
        }

        Internet internet = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            internet = mapper.readValue(internetFile, Internet.class);
        } catch (IOException e) {
            logger.error("Error mapping file", e);
        }

        return internet;
    }
}
