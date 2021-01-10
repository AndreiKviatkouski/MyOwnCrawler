package by.AndreiKviatkouski.actions;

import by.AndreiKviatkouski.entyties.Element;
import by.AndreiKviatkouski.service.ElementService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Spider {

    private final Set<String> pagesVisited = new HashSet<>();
    private PrintWriter pw;
    private final Map<String, Integer> statistics = new HashMap<>();// statistic count words
    private final List<Element> elements = new ArrayList<>();
    ElementService elementService = new ElementService();

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

        if (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH && deepStart < MAX_DEEP && !this.pagesVisited.contains(url)) {// start conditions
            leg.crawl(url);

            List<String> links = leg.getLinks();// create list link

            for (String word : words) {
                int count = leg.countWorlds(word);
                statistics.put(word, count);
            }

            this.pagesVisited.add(url);// added link

            this.printResult(url, words, statistics); // print

            Element newElement = elementService.create(url, words, statistics);
            elements.add(newElement);//add element

            for (String link : links) {
                searchRecursive(link, words, deepStart + 1, MAX_PAGES_TO_SEARCH);//задаем глубину
            }
        }
    }

    public void printSortElement() {
        elementService.sortDown(elements);
        if (elements.size() >= 10) {
            for (int i = 0; i < 10; i++) {
                Element element = elements.get(i);
                System.out.println(element);
                StringBuilder builder = new StringBuilder(element.getUrl() + " ");
            }
        }else{
            System.err.println("Size<10!!!");
        }
    }

    public void printResult(String url, List<String> words, Map<String, Integer> statistics) {
        StringBuilder builder = new StringBuilder(url + " ");
        for (String key : words) {
            Integer value = statistics.get(key);
            builder.append(value).append(" ");
        }
        builder.append("\n");
        pw.write(builder.toString());
    }


    public void openFile(String file) {
        try {
            FileWriter fw = new FileWriter(file);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        pw.close();
    }
}
