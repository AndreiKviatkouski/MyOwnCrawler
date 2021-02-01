package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Video;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.AndreiKviatkouski.util.ColorScheme.GREEN_UNDERLINED;
import static by.AndreiKviatkouski.util.ColorScheme.RESET;
import static by.AndreiKviatkouski.util.Writer.writeString;

public class SpiderService {

    Elements linksOnPage;

    public Elements crawl(String url, String cssQuery) {

        System.setProperty("webdriver.chrome.driver",
                "src\\main\\java\\by\\AndreiKviatkouski\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        Document htmlDocument = Jsoup.parse(driver.getPageSource());

        linksOnPage = htmlDocument.select(cssQuery);

        driver.close();
        driver.quit();

        return linksOnPage;
    }


    public List<Video> getFinishDownloadList(List<Video> modifiedLinkList) {

        List<Video> finishList = new ArrayList<>();

        Elements media;
        for (Video video : modifiedLinkList) {
            media = crawl(video.getUrl(), "[src*=720.mp4]");

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


    public List<Video> createStartLinkList(Elements elements) {

        return elements.stream()
                .map((element) -> new Video(element.select("a").attr("href"),
                        element.select("a").first().text()))
                .distinct()
                .sorted((video1, video2) -> video2.getName().substring(5, 7).compareTo(video1.getName().substring(5, 7)))
                .collect(Collectors.toList());
    }

    public List<Video> modifyLinkList(List<Video> videoList) {
        return videoList.stream()
                .map(e -> new Video(e.getUrl().replaceFirst("/", "https://m.vk.com/"), e.getName()))
                .collect(Collectors.toList());

    }

}