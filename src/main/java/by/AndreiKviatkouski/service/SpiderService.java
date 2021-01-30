package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
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


    public static void downloadVideo(String url, String fileName) {


        if (url == null) {
            System.out.println("Empty URL");
            return;
        }
        long start = System.currentTimeMillis();

        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName.concat(".mp4")));
            System.out.println("File copy time = " + ((System.currentTimeMillis() - start) / 1000) + "сек");
        } catch (FileAlreadyExistsException e) {
            System.out.println(YELLOW_BOLD + "File already exist!" + RESET);
        } catch (InvalidPathException e) {
            System.out.println(YELLOW_BOLD + "Invalid path!" + RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
            
            System.out.println(media);
            System.out.println(GREEN_UNDERLINED +"____________________________________________________________________________________________________________________"+ RESET);
            String link = createDownloadLink(media);// return first link from modifiedLinkList
            finishList.add(new Video(video.getUrl(), video.getName(), link));
        }
        return finishList;
    }

    private String createDownloadLink(Elements elements) {

        String link = null;
        for (Element element : elements) {
            link = element.attr("abs:href");
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
//        view-source:h
        return videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("v", "m.v"), e.getName()))
                .collect(Collectors.toList());

    }

}