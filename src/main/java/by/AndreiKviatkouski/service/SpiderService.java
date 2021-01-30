package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import com.github.axet.vget.VGet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static by.AndreiKviatkouski.util.Writer.writeString;
import static by.AndreiKviatkouski.util.ColorScheme.*;

public class SpiderService {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.60 YaBrowser/20.12.0.963 Yowser/2.5 Safari/537.36";

    static Elements linksOnPage;


    public static void main(String[] args) {


        SpiderService spiderService = new SpiderService();

        Elements onIndexPage = spiderService.crawl("https://vk.com/videos-111905078?section=album_115", ".video_item_title");

        List<Video> listVideoLinks = spiderService.createVideoList(onIndexPage);
//        listVideoLinks.forEach(System.out::println);
//        System.out.println(YELLOW_BOLD + "_____________________________________________________________________________________" + RESET);


        List<Video> modifiedListVideoLinks = spiderService.createModifyVideoList(listVideoLinks);
        modifiedListVideoLinks.forEach(System.out::println);
        System.out.println(YELLOW_BOLD + "_____________________________________________________________________________________" + RESET);

        List<Video> finishList = spiderService.getFinishDownloadList(modifiedListVideoLinks);
        for (Video video : finishList) {
            System.out.println(RED + video + RESET);
        }

        downloadVideo("https://pvv4.vkuservideo.net/c500602/3/ef7OjU-NjU2OzY/videos/2f35a30306.720.mp4","fileName2");


    }

    private static void downloadVideo(String url,String fileName) {

        long start = System.currentTimeMillis();

        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName.concat(".mp4")));
            System.out.println("Время копирования файла = "+((System.currentTimeMillis()-start)/1000) + "сек");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<Video> getFinishDownloadList(List<Video> modifiedListVideoLinks) {
        List<Video> finishList = new ArrayList<>();
        Elements media = null;
        for (Video video : modifiedListVideoLinks) {
             media = crawl(video.getUrl(), "script");
            finishList = createDownloadVideoList(media);
        }
        System.out.println(RED + media.get(0) +RESET);
        return finishList;
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

    private List<Video> createDownloadVideoList(Elements elements) {

        return elements.stream()
                .map((element) -> new Video(element.attr("abs:src"), element.text()))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Video> createVideoList(Elements elements) {

        return elements.stream()
                .map((element) -> new Video(element.attr("abs:href"), element.text()))
                .distinct()
                .sorted((video1, video2) -> video2.getName().substring(5, 7).compareTo(video1.getName().substring(5, 7)))
                .collect(Collectors.toList());
    }

    private List<Video> createModifyVideoList(List<Video> videoList) {

        return videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("v", "m.v"), e.getName()))
                .collect(Collectors.toList());
    }

}