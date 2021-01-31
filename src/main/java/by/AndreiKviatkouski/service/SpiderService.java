package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.util.Writer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
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



    public static void main(String[] args) {
        crawl2();

    }
    public static Elements crawl2(){
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

            writeString(GREEN_UNDERLINED +"____________________________________________________________________________________________________________________"+ RESET);
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