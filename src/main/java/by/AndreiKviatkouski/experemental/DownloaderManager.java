package by.AndreiKviatkouski.experemental;

import by.AndreiKviatkouski.entyties.Video;
import by.AndreiKviatkouski.service.Downloader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloaderManager {

    private List<Video> finishList;


    ExecutorService executor = Executors.newFixedThreadPool(4);

    public void startTreads() {

        for (Video video : finishList) {
            Downloader downloader = new Downloader();
            Thread thread = new Thread(downloader);
            downloader.setUrl(video.getDownloadLink());
            downloader.setFileName("src\\main\\java\\by\\AndreiKviatkouski\\video2\\" + video.getName());
            executor.execute(thread);

        }
        executor.shutdown();
    }
}
