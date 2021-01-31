package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.util.Writer;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.AndreiKviatkouski.util.ColorScheme.*;
import static by.AndreiKviatkouski.util.Writer.writeString;

public class SpiderService {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    static Elements linksOnPage;


    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
//        // turn off htmlunit warnings
//        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
//        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
//
//        WebClient webClient = new WebClient();
//        HtmlPage page = webClient.getPage("http://stackoverflow.com");
//        System.out.println(page.getTitleText());

//        String[] links = {"http://www.oddsportal.com/tennis/china/atp-beijing/murray-andy-dimitrov-grigor-fTdGYm3q/#cs;2;6",
//                "http://www.oddsportal.com/tennis/china/atp-beijing/murray-andy-dimitrov-grigor-fTdGYm3q/#cs;2;9"};
//
//        String bm = null;
//        String[] odds = new String[2];
//
//        //Second way
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//
//        webClient.getOptions().setJavaScriptEnabled(false);
//        webClient.getOptions().setCssEnabled(false);
//        System.out.println("Client opened");
//        for (int i = 0; i < links.length; i++) {
//
//            HtmlPage page = webClient.getPage(links[i]);
//            System.out.println("Page loaded");
//            Document csDoc = Jsoup.parse(page.asXml());
//            System.out.println("Page parsed");
//
//            Element table = csDoc.select("table.table-main.detail-odds.sortable").first();
//            Elements cols = table.select("td:eq(0)");
//
//            if (cols.first().text().trim().contains("bet365.it")) {
//                bm = cols.first().text().trim();
//                odds[i] = table.select("tbody > tr.lo").select("td.right.odds").first().text().trim();
//            } else {
//                Elements footTable = csDoc.select("table.table-main.detail-odds.sortable");
//                Elements footRow = footTable.select("tfoot > tr.aver");
//                odds[i] = footRow.select("td.right").text().trim();
//
//                bm = "AVG";
//            }
//            webClient.close();
//        }
//
//        System.out.println(bm + "\t" + odds[0] + "\t" + odds[1]);

    }


    public static Elements crawl2() {
        String url = "view-source:https://m.vk.com/video-111905078_456246013";
        String cssQuery = "[src*=.mp4]";
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).ignoreHttpErrors(true);
            Document htmlDocument = connection.get();

            if (connection.response().statusCode() == 200) {
                writeString(YELLOW_BOLD + "\n**Visiting** Received web page at " + RESET + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                writeString(RED_BOLD + "**Failure** Retrieved something other than HTML" + RESET);
            }
            linksOnPage = htmlDocument.select(cssQuery);
            System.out.println(linksOnPage);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return linksOnPage;
    }


    //    Я использую Java. Я хочу получить исходный код веб-страницы, но на странице работает JavaScript, и я хочу получить код,
//    сгенерированный JavaScript (код, который мы видим в firebug в firefox) Кто-нибудь знает, что я должен делать?
    public Elements crawl(String url, String cssQuery) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).ignoreHttpErrors(true);
            Document htmlDocument = connection.get();

            if (connection.response().statusCode() == 200) {
                writeString(YELLOW_BOLD + "\n**Visiting** Received web page at " + RESET + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                writeString(RED_BOLD + "**Failure** Retrieved something other than HTML" + RESET);
            }
            linksOnPage = htmlDocument.select(cssQuery);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return linksOnPage;
    }


    public List<Video> getFinishDownloadList(List<Video> modifiedLinkList) {

        List<Video> finishList = new ArrayList<>();

        Elements media;
        for (Video video : modifiedLinkList) {
            media = crawl(video.getUrl(), "[src*=.mp4]");

            writeString(GREEN_UNDERLINED + "____________________________________________________________________________________________________________________" + RESET);
            String link = createDownloadLink(media);// return first link from modifiedLinkList
            finishList.add(new Video(video.getUrl(), video.getName(), link));
        }

        return finishList;
    }

    private String createDownloadLink(Elements elements) {

        String link = null;
        for (Element element : elements) {
            link = element.attr("abs:src");
        }

        return link;
    }

    public List<Video> createLinkList(Elements elements) {

        return elements.stream()
                .map((element) -> new Video(element.attr("abs:href"), element.text()))
                .distinct()
                .sorted((video1, video2) -> video2.getName().substring(5, 7).compareTo(video1.getName().substring(5, 7)))
                .collect(Collectors.toList());
    }

    public List<Video> modifyLinkList(List<Video> videoList) {
        return videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("v", "m.v"), e.getName()))
                .collect(Collectors.toList());

    }

}