package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.validator.WordValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import static by.AndreiKviatkouski.util.Writer.writeString;

public class SpiderService {
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.60 YaBrowser/20.12.0.963 Yowser/2.5 Safari/537.36";
    private final List<String> links = new LinkedList<>();
    protected Document htmlDocument;
    static Elements linksOnPage;


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     *
     * @param url - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).ignoreHttpErrors(true);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code indicating that everything is great.
            {
                writeString("\n**Visiting** Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                writeString("**Failure** Retrieved something other than HTML");
                return false;
            }
            linksOnPage = htmlDocument.select("a[href]");
            writeString("Found (" + linksOnPage.size() + ") links");

            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
            return true;
        } catch (IOException ioe) {
            // We were not successful in our HTTP request
            return false;
        }
    }


    /**
     * Performs a search on the body of on the HTML document that is retrieved. This method should
     * only be called after a successful crawl.
     *
     * @param word - The word or string to look for
     * @return whether or not the word was found
     */


    public int countWords(String word) {
        int count = 0;
//           Defensive coding. This method should only be used after a successful crawl.
        if (this.htmlDocument == null) {
            writeString("ERROR! Call crawl() before performing analysis on the document");
            return 0;
        }
        writeString("Searching for the word " + word + "...");

        String str = this.htmlDocument.body().text();
        if (str == null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return 0;
        }
        Matcher matcher = WordValidator.check(word, str);
        while (matcher.find()) {
            count++;
        }
        writeString(word + "   count: " + count);
        return count;

    }


    public List<String> getLinks() {
        return this.links;
    }

}