package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.util.Writer;
import by.AndreiKviatkouski.validator.PropertiesValidator;
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
import static by.AndreiKviatkouski.validator.PropertiesValidator.*;

public class SpiderService {
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    static Elements linksOnPage;


    public static void main(String[] args) {


        SpiderService spiderService = new SpiderService();
//        https://vk.com/videos-111905078?section=album_273
        Elements onIndexPage = spiderService.crawl("https://vk.com/videos-111905078?section=album_273", ".video_item_title");

        List<Video> listVideoLinks = spiderService.createLinkList(onIndexPage);

        List<Video> modifiedListVideoLinks = spiderService.modifyLinkList(listVideoLinks);


        System.out.println(PURPLE_BOLD + "Files for download: " + modifiedListVideoLinks.size() + RESET);
        modifiedListVideoLinks.stream().map(video -> video.getName() + " " + video.getUrl()).forEach(System.out::println);

        List<Video> finishList = spiderService.getFinishDownloadList(modifiedListVideoLinks);
        for (Video video : finishList) {
            System.out.println(RED + video + RESET + "\n");

            downloadVideo(video.getDownloadLink(), "src\\main\\java\\by\\AndreiKviatkouski\\video\\" + video.getName());

//            downloadVideo("https://pvv4.vkuservideo.net/c500602/3/ef7OjU-NjU2OzY/videos/2f35a30306.720.mp4","src\\main\\java\\by\\AndreiKviatkouski\\video\\");

        }

        Writer.writeError(YELLOW_BOLD +"WELL DONE JOB!");
    }


    private static void downloadVideo(String url, String fileName) {


        if (url == null) {
            System.out.println("Empty URL");
            return;
        }
        long start = System.currentTimeMillis();

        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName.concat(".mp4")));
            System.out.println("Время копирования файла = " + ((System.currentTimeMillis() - start) / 1000) + "сек");
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

    private List<Video> getFinishDownloadList(List<Video> modifiedLinkList) {

        List<Video> finishList = new ArrayList<>();

        Elements media;
        for (Video video : modifiedLinkList) {
            media = crawl(video.getUrl(), "[src*=.mp4]");
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

    private List<Video> createLinkList(Elements elements) {

        return elements.stream()
                .map((element) -> new Video(element.attr("abs:href"), element.text()))
                .distinct()
                .sorted((video1, video2) -> video2.getName().substring(5, 7).compareTo(video1.getName().substring(5, 7)))
                .collect(Collectors.toList());
    }

    private List<Video> modifyLinkList(List<Video> videoList) {

        return videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("v", "m.v"), e.getName()))
                .collect(Collectors.toList());
    }

}