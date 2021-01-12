package by.AndreiKviatkouski.actions;

import by.AndreiKviatkouski.entyties.Element;
import by.AndreiKviatkouski.service.ElementService;
import by.AndreiKviatkouski.service.SpiderService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SpiderAction {

    private final Set<String> pagesVisited = new HashSet<>();
    LinkedHashSet<String> unvisitedLinks = new LinkedHashSet<>();

    private PrintWriter pw;
    private final Map<String, Integer> statistics = new HashMap<>();// statistic count words
    private final List<Element> elements = new ArrayList<>();
    ElementService elementService = new ElementService();


    public void setStartPage(String startUrl){
        this.unvisitedLinks.add(startUrl);
    }


    public void searchRecursive(List<String> words, int MAX_DEEP, int MAX_PAGES_TO_SEARCH) {

        SpiderService leg = new SpiderService();

        int deepStart = 0;

        this.unvisitedLinks.addAll(leg.getLinks());
        String url = (String) this.unvisitedLinks.toArray()[0];
        this.unvisitedLinks.remove(url);


        if (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH && deepStart < MAX_DEEP && !this.pagesVisited.contains(url)) {// start conditions
            leg.crawl(url);

            //List<String> links = leg.getLinks();// create list link
            unvisitedLinks.addAll(leg.getLinks());


            for (String word : words) {
                int count = leg.countWorlds(word);
                statistics.put(word, count);
            }

            this.pagesVisited.add(url);// added link

            this.printResult(url, words, statistics); // print

            Element newElement = elementService.create(url, words, statistics);
            elements.add(newElement);//add element

            //for (String link : links) {
            //    searchRecursive(link, words, deepStart + 1, MAX_PAGES_TO_SEARCH);//set deep
            //}
            searchRecursive( words, deepStart + 1, MAX_PAGES_TO_SEARCH);
        }
    }
    //  3,4, 1,2,3,4   1,2

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

    public void printResult(String url, List<String> words, Map<String, Integer> statistics) {
        StringBuilder builder = new StringBuilder(url + " ");
        for (String key : words) {
            Integer value = statistics.get(key);
            builder.append(value).append(" ");
        }
        builder.append("\n");
        pw.write(builder.toString());
    }



    public void printSortElementTopTen() {
        elementService.sortDown(elements);
        if (elements.size() >= 10) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                Element element = elements.get(i);
                builder.append(element.getUrl()).append(" ").
                        append(element.getValue1()).append(" ").
                        append(element.getValue2()).append(" ").
                        append(element.getValue3()).append(" ").
                        append(element.getValue4()).append(" ").
                        append(element.getValue5()).append(" ");
                builder.append("\n");

                builder.append("Numbers are").append(" ");
                builder.append("\n");

                builder.append("     ").
                        append(element.getParam1()).
                        append("-").
                        append(element.getValue1()).
                        append(" ").
                        append("hits");
                builder.append("\n");

                builder.append("     ").
                        append(element.getParam2()).
                        append("-").
                        append(element.getValue2()).
                        append(" ").
                        append("hits");
                builder.append("\n");

                builder.append("     ").
                        append(element.getParam3()).
                        append("-").append(element.getValue3()).
                        append(" ").
                        append("hits");
                builder.append("\n");

                builder.append("     ").
                        append(element.getParam4()).
                        append("-").
                        append(element.getValue4()).
                        append(" ").
                        append("hits");
                builder.append("\n");

                builder.append("     ").
                        append(element.getParam5()).
                        append("-").
                        append(element.getValue5()).
                        append(" ").
                        append("hits");
                builder.append("\n");
                builder.append("\n");
                builder.append("\n");

            }

            builder.append("\n");
            pw.write(builder.toString());
        } else {
            System.err.println("Elements size is < 10!!!");
        }
    }
}
