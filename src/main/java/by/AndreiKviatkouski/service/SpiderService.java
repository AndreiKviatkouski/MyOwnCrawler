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
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.60 YaBrowser/20.12.0.963 Yowser/2.5 Safari/537.36";
    private final List<String> links = new LinkedList<>();
    protected Document htmlDocument;
    static Elements linksOnPage;

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
            Elements link1 = htmlDocument.select("a[href]");
            for (Element element : link1) {
                System.out.println(element.tagName() +" "+ element.attr("abs:href") +" " + element.attr("rel"));
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

    public List<String> getLinks() {
        return this.links;
    }

}