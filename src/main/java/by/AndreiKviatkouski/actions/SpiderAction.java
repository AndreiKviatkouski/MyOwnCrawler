package by.AndreiKviatkouski.actions;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.service.SpiderService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class SpiderAction {

    private final Set<String> pagesVisited = new HashSet<>();

    private final LinkedHashSet<String> unvisitedLinks = new LinkedHashSet<>();

    private PrintWriter pw;

    private final List<Video> elements = new ArrayList<>();

    public void setStartPage(URL startUrl) {
        this.unvisitedLinks.add(String.valueOf(startUrl));
    }



    public void searchRecursive(int MAX_DEEP, int MAX_PAGES_TO_SEARCH) {

        SpiderService leg = new SpiderService();

        int deepStart = 0;

        unvisitedLinks.addAll(leg.getLinks());
        String url = (String) this.unvisitedLinks.toArray()[0];
        unvisitedLinks.remove(url);


        if (pagesVisited.size() < MAX_PAGES_TO_SEARCH && deepStart < MAX_DEEP && !pagesVisited.contains(url)) {
            leg.crawl(url);

            unvisitedLinks.addAll(leg.getLinks());

            pagesVisited.add(url);

            printResult(url);

            searchRecursive( deepStart + 1, MAX_PAGES_TO_SEARCH);
        }
    }

    public void openFile(Path file) {
        try {
            FileWriter fw = new FileWriter(String.valueOf(file));
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        pw.close();
    }


    public void printResult(String url) {
        pw.write(url + " " + "\n");
    }
}

