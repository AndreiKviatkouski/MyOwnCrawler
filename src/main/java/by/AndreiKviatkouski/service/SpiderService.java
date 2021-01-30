package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.AndreiKviatkouski.util.Writer.writeString;

public class SpiderService {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.60 YaBrowser/20.12.0.963 Yowser/2.5 Safari/537.36";
    protected Document htmlDocument;
    static Elements linksOnPage;
    private List<Video> listLinksVideo = new ArrayList<>();

    public boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).ignoreHttpErrors(true);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if (connection.response().statusCode() == 200) {
                writeString("\n**Visiting** Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                writeString("**Failure** Retrieved something other than HTML");
                return false;
            }
            linksOnPage = htmlDocument.select(".video_item_title");

            return true;

        } catch (IOException ioe) {
            return false;
        }
    }


    public static void main(String[] args) {


        SpiderService spiderService = new SpiderService();
        spiderService.crawl("https://vk.com/videos-111905078?section=album_115", ".video_item_title");
        List<Video> videoList = spiderService.createVideoList();
        List<Video> modifiedVideoList = spiderService.modifyVideoList(videoList);
        for (Video video : modifiedVideoList) {
           Elements media = spiderService.crawl(video.getUrl(), "[src]");
           media.forEach(System.out::println);
            spiderService.createDownloadVideoList(media);
        }

    }


    public Elements crawl(String url, String cssQuery) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT).followRedirects(true).ignoreHttpErrors(true);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if (connection.response().statusCode() == 200) {
                writeString("\n**Visiting** Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                writeString("**Failure** Retrieved something other than HTML");
            }
            linksOnPage = htmlDocument.select(cssQuery);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return linksOnPage;
    }

    private List<Video> createDownloadVideoList(Elements elements) {
        List<Video> downloadList = elements.stream()
                .filter(element -> element.normalName().equals("source"))
                .map((element) -> new Video(element.attr("abs:src")))
                .distinct()
                .collect(Collectors.toList());

        downloadList.forEach(System.out::println);
        return downloadList;
    }

    private List<Video> createVideoList() {
        listLinksVideo = linksOnPage.stream()
                .map((element) -> new Video(element.attr("abs:href"), element.text()))
                .distinct()
                .sorted((video1, video2) -> video2.getName().substring(5, 7).compareTo(video1.getName().substring(5, 7)))
                .collect(Collectors.toList());

        listLinksVideo.forEach(System.out::println);
        return listLinksVideo;
    }

    private List<Video> modifyVideoList(List<Video> videoList) {

        List<Video> modifiedListVideo = videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("v", "m.v"), e.getName()))
                .collect(Collectors.toList());

        modifiedListVideo.forEach(System.out::println);

        return modifiedListVideo;
    }


    public List<String> getLinks() {
        List<String> arr = new ArrayList<>();
        for (Video value : listLinksVideo) {
            arr.add(value.getUrl());
        }
        return arr;
    }

}