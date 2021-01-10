package by.AndreiKviatkouski;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Spider {

    private final Set<String> pagesVisited = new HashSet<>();
    private  PrintWriter pw;


    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param url   - The starting point of the spider
     * @param words - The words or string that you are searching for
     */


    public void searchRecursive(String url, List<String> words, int MAX_DEEP, int MAX_PAGES_TO_SEARCH) {
        SpiderLeg leg = new SpiderLeg();
        int deepStart = 0;
        if (deepStart < MAX_DEEP && this.pagesVisited.size() < MAX_PAGES_TO_SEARCH && !this.pagesVisited.contains(url)) {// start conditions
            leg.crawl(url);
            List<String> links = leg.getLinks();// create list link
            Map<String, Integer> statistics = new HashMap<>();// statistic count words

            for (String word : words) {
                int count = leg.countWorlds(word);
                statistics.put(word, count);
            }

            this.pagesVisited.add(url);// added link

            this.printResult(url, words, statistics); // print

            for (String link : links) {
                searchRecursive(link, words, deepStart + 1,MAX_PAGES_TO_SEARCH);//задаем глубину
            }
        }
    }


    public void printResult(String url, List<String> words, Map<String, Integer> stat) {

        StringBuilder builder = new StringBuilder(url + " ");
        for (String key : words) {
            Integer value = stat.get(key);
            builder.append(value).append(" ");
        }
        builder.append("\n");
        pw.write(builder.toString());
    }


    public void openFile(String file, boolean append) {
        try {
            FileWriter fw = new FileWriter(file, append);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        pw.close();
    }
}
