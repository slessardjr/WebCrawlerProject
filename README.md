# WebCrawlerProject

Simple Example of a multithreaded Web Crawler

# Assumptions
1) This was a proof of concept.  It's ok for the WebCrawler to have an "Internet" instance defined by JSON.

2) Threads needed to be kept in control due to the nature of infinite depth

3) There would be no need for other imput arguement to process.  The program will process internet1.json and
   internet2.json that exist on the classpath.

4) Output order might might match expected output order due to the multi-threaded nature of the application.


# Reasons for significant design choices
1) Because the requirement specified infinite depth, recursion was the best patter to use.  To use concurrency in the
   pattern I decided to use the Fork/Join framework to split up the work.  This also allowed me specify the number of
   maximum threads the application could use in the threadpool.

2) I thought about using unit tests to mock an actual internet call, but that might have made the example a little too
   complicated.  So I just like the crawler be aware of an internet instance.  The pattern I chose wasn't perfect, but
   the example satisfied the requirements without the need for any complex actions to "visit" a JSON website.

3) I processed each JSON file in parallel, and then processed the file with concurrency to satify the requirements.

4) The expected results seemed to indicate that the result lists would list a site once, even though a site could be
   skipped multiple times.  This would have been better off as Sets to not have to worry about checking for contains.
   In my initial design I had used Sets, but I wanted to demonstrate synchronizing multiple objects using one lock over
   multiple threads.