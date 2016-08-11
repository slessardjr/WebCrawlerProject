package com.steve.web.crawler;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

class ProgramOptions {
    private final static String cliCommandName = "SL-WebCrawler";

    static String TEST_MODE_ARG_NAME = "test";
    static String WEB_MODE_ARG_NAME = "crawl";
    static String DEMO_MODE_ARG_NAME = "demo";
    static String MAX_DEPTH_ARG_NAME = "maxdepth";

    static Options getProgramOptions() {
        Options options = new Options();

        options.addOption(
                Option.builder()
                        .argName("d")
                        .longOpt(DEMO_MODE_ARG_NAME)
                        .hasArg(false)
                        .desc("Runs the SiteBrowserJsonFileImpl Crawler in Demo mode. Processes Two internal files, internet1.json and internet2.json")
                        .build()
        );

        options.addOption(
                Option.builder()
                        .argName("t")
                        .longOpt(TEST_MODE_ARG_NAME)
                        .hasArg()
                        .type(String.class)
                        .desc("Runs the SiteBrowserJsonFileImpl Crawler in Test mode. Provide Path to JSON Internet File")
                        .build()
        );

        options.addOption(
                Option.builder()
                        .argName("c")
                        .longOpt(WEB_MODE_ARG_NAME)
                        .hasArg()
                        .type(String.class)
                        .desc("Runs the SiteBrowserJsonFileImpl Crawler on a Website. Provide starting website URL")
                        .build()
        );

        options.addOption(
                Option.builder()
                        .argName("m")
                        .longOpt(MAX_DEPTH_ARG_NAME)
                        .hasArg()
                        .type(Integer.class)
                        .desc("The maximum depth to crawl to.  Default is Infinite")
                        .build()
        );

        options.addOption(
                Option.builder()
                        .argName("h")
                        .longOpt("help")
                        .desc("Show the usage/help. AKA what you're looking at now")
                        .build()
        );

        return options;
    }

    static boolean tooManyArgs(CommandLine cli) {
        int count = 0;

        //Add the Args that should only have 1 in the group
        if (cli.hasOption(ProgramOptions.WEB_MODE_ARG_NAME))
            count++;
        if (cli.hasOption(ProgramOptions.TEST_MODE_ARG_NAME))
            count++;
        if (cli.hasOption(ProgramOptions.DEMO_MODE_ARG_NAME))
            count++;

        return count > 1;
    }

    static void help(Options options) {
        System.out.println();
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(cliCommandName, options);
        System.exit(0);
    }
}
