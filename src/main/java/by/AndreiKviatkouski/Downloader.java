package by.AndreiKviatkouski;

import by.AndreiKviatkouski.util.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import static by.AndreiKviatkouski.util.ColorScheme.RESET;
import static by.AndreiKviatkouski.util.ColorScheme.YELLOW_BOLD;

public class Downloader implements Runnable {
    String url;
    String fileName;


    OnEventHappend notifierEvent;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static void downloadVideo(String url, String fileName) {


        if (url == null) {
            Writer.writeString("Empty URL");
            return;
        }
        long start = System.currentTimeMillis();

        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName.concat(".mp4")));
            Writer.writeString("File copy time = " + ((System.currentTimeMillis() - start) / 1000) + "сек" + "\n");

        } catch (FileAlreadyExistsException e) {
            Writer.writeString(YELLOW_BOLD + "File already exist!" + RESET);
        } catch (InvalidPathException e) {
            Writer.writeString(YELLOW_BOLD + "Invalid path!" + RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNotifierEvent(OnEventHappend notifierEvent) {
        this.notifierEvent = notifierEvent;
    }

    @Override
    public void run() {
        downloadVideo(this.url,this.fileName);
    }
}
