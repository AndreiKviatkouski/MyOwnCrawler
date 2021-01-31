package by.AndreiKviatkouski;

import by.AndreiKviatkouski.entyties.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class DownloaderManager implements OnEventHappend{
    List<Downloader> list = new ArrayList<>();
    List<Video> videoList = new ArrayList<>();

    void createDownloads(){
        Downloader d=new Downloader();
        d.setFileName("my filename");
//        d.setNotifierEvent(this);
    }

    @Override
    public void happen(Object data) {

    }
}
