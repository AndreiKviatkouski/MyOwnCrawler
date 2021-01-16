package by.AndreiKviatkouski.actions;

import by.AndreiKviatkouski.entyties.Element;
import by.AndreiKviatkouski.service.ElementService;
import by.AndreiKviatkouski.service.SpiderService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static by.AndreiKviatkouski.util.Writer.writeString;

public class SpiderAction {

    private final Set<String> pagesVisited = new HashSet<>();

    private final LinkedHashSet<String> unvisitedLinks = new LinkedHashSet<>();

    private PrintWriter pw;

    private final Map<String, Integer> statistics = new HashMap<>();// statistic count words

    private final List<Element> elements = new ArrayList<>();

    ElementService elementService = new ElementService();


    /**
     * Add starting Url in LinkedHashSet unvisited links
     *
     * @param startUrl - The starting point of the spider
     */
    public void setStartPage(String startUrl) {
        this.unvisitedLinks.add(startUrl);
    }


    /**
     * Our main launching point for the Spider's functionality. Internally it creates spider legs
     * that make an HTTP request and parse the response (the web page).
     *
     * @param words               - The words or string that you are searching for
     * @param MAX_DEEP            - Predefined links depth
     * @param MAX_PAGES_TO_SEARCH - Max visited page limit
     */
    public void searchRecursive(List<String> words, int MAX_DEEP, int MAX_PAGES_TO_SEARCH) {

        SpiderService leg = new SpiderService();

        int deepStart = 0;

        unvisitedLinks.addAll(leg.getLinks());
        String url = (String) this.unvisitedLinks.toArray()[0];
        unvisitedLinks.remove(url);


        if (pagesVisited.size() < MAX_PAGES_TO_SEARCH && deepStart < MAX_DEEP && !pagesVisited.contains(url)) {// start conditions
            leg.crawl(url);

            unvisitedLinks.addAll(leg.getLinks());


            for (String word : words) {
                int count = leg.countWords(word);
                statistics.put(word, count);
            }

            pagesVisited.add(url);// added link

            printResult(url, words, statistics); // print

            Element newElement = elementService.create(url, words, statistics);
            elements.add(newElement);//add element


            searchRecursive(words, deepStart + 1, MAX_PAGES_TO_SEARCH);
        }
    }

    /**
     * The method opens the file for writing
     *
     * @param file The file for writing
     */
    public void openFile(String file) {
        try {
            FileWriter fw = new FileWriter(file);
            pw = new PrintWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method closes the file for writing
     */
    public void closeFile() {
        pw.close();
    }

    /**
     * The method writes the visited pages to a file
     *
     * @param url        The URL to visit
     * @param words      The words or strings to look for
     * @param statistics The statistics to visit
     */
    public void printResult(String url, List<String> words, Map<String, Integer> statistics) {
        StringBuilder builder = new StringBuilder(url + " ");
        for (String key : words) {
            Integer value = statistics.get(key);
            builder.append(value).append(" ");
        }
        builder.append("\n");
        pw.write(builder.toString());
    }

    /**
     * The method writes the top 10 elements to a file
     */
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
            writeString("Elements size is < 10!!!");
        }
    }
}

