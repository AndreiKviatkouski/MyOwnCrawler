package by.AndreiKviatkouski;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.service.SpiderService;
import by.AndreiKviatkouski.util.Writer;
import org.jsoup.select.Elements;

import java.util.List;

import static by.AndreiKviatkouski.util.ColorScheme.*;


public class SpiderRun {

    public static void main(String[] args) {

//        https://vk.com/videos-111905078?section=album_273
//        https://vk.com/videos-111905078?section=album_115
//        "https://pvv4.vkuservideo.net/c500602/3/ef7OjU-NjU2OzY/videos/2f35a30306.720.mp4"


        SpiderService spiderService = new SpiderService();

        Elements onIndexPage = spiderService.crawl("https://vk.com/videos-111905078?section=album_273", ".video_item_title");

        List<Video> listVideoLinks = spiderService.createLinkList(onIndexPage);

        List<Video> modifiedListVideoLinks = spiderService.modifyLinkList(listVideoLinks);


        System.out.println(PURPLE_BOLD + "Files for download: " + modifiedListVideoLinks.size() + RESET);
        modifiedListVideoLinks.stream().map(video -> video.getName() + " " + video.getUrl()).forEach(System.out::println);

        List<Video> finishList = spiderService.getFinishDownloadList(modifiedListVideoLinks);
        for (Video video : finishList) {
            System.out.println(RED + video + RESET + "\n");

            SpiderService.downloadVideo(video.getDownloadLink(), "src\\main\\java\\by\\AndreiKviatkouski\\video\\" + video.getName());

        }

        Writer.writeError(YELLOW_BOLD +"WELL DONE JOB!");
    }
}