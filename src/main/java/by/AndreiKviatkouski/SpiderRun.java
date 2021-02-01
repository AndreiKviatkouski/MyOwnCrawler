package by.AndreiKviatkouski;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.service.Downloader;
import by.AndreiKviatkouski.service.SpiderService;
import by.AndreiKviatkouski.util.Writer;
import org.jsoup.select.Elements;

import java.util.List;

import static by.AndreiKviatkouski.util.ColorScheme.*;


public class SpiderRun {

    public static void main(String[] args) {

        SpiderService spiderService = new SpiderService();
        Downloader downloader = new Downloader();

      //  https://vk.com/videos-111905078?section=album_115
        Elements onIndexPage = spiderService.crawl("https://vk.com/videos-111905078?section=album_273",
                ".video_item_info");

        List<Video> startLinks = spiderService.createStartLinkList(onIndexPage);
        List<Video> modifiedLinks = spiderService.modifyLinkList(startLinks);


        Writer.writeString(PURPLE_BOLD + "Files for download: " + modifiedLinks.size() + " elements "  + RESET);
        modifiedLinks.stream().map(video -> video.getName() + " " + video.getUrl()).forEach(System.out::println);

        List<Video> finishList = spiderService.getFinishDownloadList(modifiedLinks);

        finishList.parallelStream()
                .forEach(video -> downloader.downloadVideo(
                        video.getDownloadLink(),
                        "src\\main\\java\\by\\AndreiKviatkouski\\video\\" + video.getName()));

//        for (Video video : finishList) {
//            Writer.writeString(RED + video + RESET);
//
//           downloader.downloadVideo(video.getDownloadLink(), "src\\main\\java\\by\\AndreiKviatkouski\\video\\" + video.getName());
//
//        }
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Writer.writeError(YELLOW_BOLD + "WELL DONE JOB!");
    }
}