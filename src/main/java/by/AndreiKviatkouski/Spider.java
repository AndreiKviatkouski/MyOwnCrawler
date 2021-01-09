package by.AndreiKviatkouski;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Spider {
    private static final int MAX_PAGES_TO_SEARCH = 10000;


    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    private String currentUrl;


//    private static final int MAX_DEPTH = 9;
//    private HashSet<String> links2;

//    public void WebCrawlerWithDepth() {
//        links2 = new HashSet<String>();
//    }
//
//    public void getPageLinks(String URL, int depth) {
//        if ((!links2.contains(URL) && (depth < MAX_DEPTH))) {
//            System.out.println(">> Depth: " + depth + " [" + URL + "]");
//            try {
//                links2.add(URL);
//
//                Document document = Jsoup.connect(URL).get();
//                Elements linksOnPage = document.select("a[href]");
//
//                depth++;
//                for (Element page : linksOnPage) {
//                    getPageLinks(page.attr("abs:href"), depth);
//                }
//            } catch (IOException e) {
//                System.err.println("For '" + URL + "': " + e.getMessage());
//            }
//        }

    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param url        - The starting point of the spider
     * @param searchWord - The word or string that you are searching for
     */

//    public void search(String url, ArrayList<String>words, int maxDepp) {
//        int currentDepp = 0;
//        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {//1
//            SpiderLeg leg = new SpiderLeg();
//            if (this.pagesToVisit.isEmpty()) {
//                currentUrl = url;
//                this.pagesVisited.add(url);
//            } else {
//                currentUrl = this.nextUrl();
//
//            }
//            leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
//            // SpiderLeg
//            boolean success = leg.searchForWord(searchWord);
//            if (success) {
//                System.out.printf("**Success** Word %s found at %s%n", searchWord, currentUrl);
//                break;
//            }
//
//            if(this.currentDeep < maxDepp) {
//                this.pagesToVisit.addAll(leg.getLinks());
//                this.currentDeep = this.currentDeep + 1;
//            }
//        }
//        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
//    }
    public void searchRec(String url, List<String> words, int deep) {
        SpiderLeg leg = new SpiderLeg();
        if (deep < 8 &&
                this.pagesVisited.size() < MAX_PAGES_TO_SEARCH &&
                !this.pagesVisited.contains(url)) {// max deep
//            if(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH){
//            if (!this.pagesVisited.contains(url)) {
            leg.crawl(url);
            List<String> links = leg.getLinks();// list link
            Map<String, Integer> stat = new HashMap<>();// statistic count words

            for (String world : words) {
                int count = leg.countWorlds(world);
                stat.put(world, count);
            }

            this.pagesVisited.add(url);// added link

            this.printResult(url, words, stat); // print

            for (String link : links) {
                searchRec(link, words, deep + 1);
            }
        }
    }


    FileWriter objectName;
    PrintWriter pw;

    public void printResult(String url, List<String> words, Map<String, Integer> stat) {
        try {
            //FileWriter objectName = new FileWriter("NewData2.csv",true);
            //PrintWriter pw = new PrintWriter(objectName);
            String builder = url + ",";
            for (String key : words) {
                Integer value = stat.get(key);
                builder += value + ",";
            }
            builder += "\n";


            pw.write(builder);
            //pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile(String filename, boolean append) {
        try {
            objectName = new FileWriter(filename, append);
            pw = new PrintWriter(objectName);// add Row name url-1-2-3-4
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        pw.close();
    }

    /**
     * Returns the next URL to visit (in the order that they were found). We also do a check to make
     * sure this method doesn't return a URL that has already been visited.
     *
     * @return
     */
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }


    void writeResults() {
//        String CSV_FILE = "C:\\test.csv";

        try (
                FileWriter CSV_FILE = new FileWriter("NewData.csv", true);
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(String.valueOf(CSV_FILE)));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Linc", "Count"));
        ) {
            csvPrinter.printRecord(currentUrl, SpiderLeg.linksOnPage.size());
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
