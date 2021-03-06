package com.steve.web.crawler;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Options programOptions = ProgramOptions.getProgramOptions();
        if (args == null) {
            ProgramOptions.help(programOptions);
        }

        CommandLineParser cliParser = new DefaultParser();
        Integer maxDepth = null;
        try {
            CommandLine commandLine = cliParser.parse(programOptions, args);

            if (ProgramOptions.tooManyArgs(commandLine)) {
                String logStatement = MessageFormat.format("Please only provide one option of \"{0}\", \"{1}\", or \"{2}\"",
                        ProgramOptions.TEST_MODE_ARG_NAME, ProgramOptions.WEB_MODE_ARG_NAME, ProgramOptions.DEMO_MODE_ARG_NAME);
                logger.info(logStatement);
                ProgramOptions.help(programOptions);
            }

            if (commandLine.hasOption(ProgramOptions.MAX_DEPTH_ARG_NAME)) {
                String depthArg = commandLine.getOptionValue(ProgramOptions.MAX_DEPTH_ARG_NAME);
                maxDepth = Integer.parseInt(depthArg);

                if (maxDepth < 1) {
                    logger.error("Please Enter a Value that is larger than 0 for \"" + ProgramOptions.MAX_DEPTH_ARG_NAME + "\" argument.");
                    ProgramOptions.help(programOptions);
                }
            }

            if (commandLine.hasOption(ProgramOptions.TEST_MODE_ARG_NAME)) {
                String jsonFilePath = commandLine.getOptionValue(ProgramOptions.TEST_MODE_ARG_NAME);
                if (jsonFilePath != null && !jsonFilePath.isEmpty()) {
                    String result = runTest(jsonFilePath, maxDepth);
                    logger.info(result);
                } else {
                    logger.error("File Path URI is empty or missing");
                    ProgramOptions.help(programOptions);
                }
            }

            if (commandLine.hasOption(ProgramOptions.WEB_MODE_ARG_NAME)) {
                System.out.println("Are you sure you want to query the ACTUAL web? Type Y or y to Continue: ");
                System.console().flush();
                String consoleInput = System.console().readLine().trim();
                System.console().flush();

                if (consoleInput != null && consoleInput.equalsIgnoreCase("y")) {

                    String startingUrl = commandLine.getOptionValue(ProgramOptions.WEB_MODE_ARG_NAME);
                    if (startingUrl != null && !startingUrl.isEmpty()) {
                        String result = runCrawl(maxDepth, startingUrl);
                        logger.info(result);
                    } else {
                        logger.error("Starting URL is empty or missing");
                        ProgramOptions.help(programOptions);
                    }
                } else {
                    logger.error("Unexpected response: \"" + consoleInput + "\".  Please Try Again.");
                    ProgramOptions.help(programOptions);
                }
            }

            if (commandLine.hasOption(ProgramOptions.DEMO_MODE_ARG_NAME)) {
                runDemo();
            }


        } catch (Throwable t) {
            logger.error("ERROR in Main Thread.", t);
            System.out.println(t.getMessage());
        }
    }

    private static String runCrawl(Integer maxDepth, String startingUrl) throws Exception {
        WebCrawlerData webCrawlerData = new WebCrawlerDataImpl();
        SiteBrowser siteBrowser = new SiteBrowserImpl();

        WebCrawler webCrawler = new WebCrawlerImpl(5, siteBrowser, webCrawlerData);

        return runCrawl(maxDepth, startingUrl, webCrawler);
    }

    private static String runCrawl(Integer maxDepth, String startingUrl, WebCrawler webCrawler) throws Exception {
        startWebCrawler(startingUrl, maxDepth, webCrawler);
        return webCrawler.getReport();
    }

    private static String runTest(String jsonFilePath, Integer maxDepth) throws Exception {
        SiteBrowserJsonFileImpl jsonSiteBrowser = new SiteBrowserJsonFileImpl(jsonFilePath);
        WebCrawlerData webCrawlerData = new WebCrawlerDataImpl();
        WebCrawler webCrawler = new WebCrawlerImpl(5, jsonSiteBrowser, webCrawlerData);

        return runTest(jsonFilePath, maxDepth, webCrawler, jsonSiteBrowser);
    }

    private static String runTest(String jsonFilePath, Integer maxDepth, WebCrawler webCrawler, SiteBrowser siteBrowser) throws Exception {
        String startingUrl = siteBrowser.getFirstPageAddress();
        startWebCrawler(startingUrl, maxDepth, webCrawler);
        return jsonFilePath + System.getProperty("line.separator") + webCrawler.getReport();
    }

    private static void startWebCrawler(String startingUrl, Integer maxDepth, WebCrawler webCrawler) throws InterruptedException {
        if (maxDepth != null && startingUrl != null && !startingUrl.isEmpty()) {
            webCrawler.start(startingUrl, maxDepth);
        } else {
            webCrawler.start(startingUrl);
        }
    }

    private static void runDemo() {
        List<String> filesToProcess = new ArrayList<>();
        filesToProcess.add("internet1.json");
        filesToProcess.add("internet2.json");

        for (String file : filesToProcess) {
            Runnable internetRunnable = () -> {
                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    InputStream internetJsonFileStream = classLoader.getResourceAsStream(file);
                    SiteBrowserJsonFileImpl jsonSiteBrowser = new SiteBrowserJsonFileImpl(internetJsonFileStream);

                    WebCrawlerData webCrawlerData = new WebCrawlerDataImpl();

                    WebCrawler webCrawler = new WebCrawlerImpl(5, jsonSiteBrowser, webCrawlerData);
                    webCrawler.start(jsonSiteBrowser.getFirstPageAddress());

                    String internetResult = file + System.getProperty("line.separator") +
                            webCrawler.getReport();

                    logger.info(internetResult);
                } catch (Exception e) {
                    logger.error("Error in thread for " + file, e);
                }
            };

            Thread internetThread = new Thread(internetRunnable);
            internetThread.start();
        }
    }
}
