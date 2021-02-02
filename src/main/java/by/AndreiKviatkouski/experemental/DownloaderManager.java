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
    Downloader downloader = new Downloader();
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Thread thread = new Thread(downloader);

    public void startTreads() {

        for (Video video : finishList) {
            downloader.setUrl(video.getDownloadLink());
            downloader.setFileName("src\\main\\java\\by\\AndreiKviatkouski\\video\\" + video.getName());
            executor.execute(thread);

        }
        executor.shutdown();
    }


}
