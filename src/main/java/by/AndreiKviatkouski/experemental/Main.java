package by.AndreiKviatkouski.experemental;

import by.AndreiKviatkouski.util.Writer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import static by.AndreiKviatkouski.util.ColorScheme.RESET;
import static by.AndreiKviatkouski.util.ColorScheme.YELLOW_BOLD;

public class Main {
    public static void main(String[] args) {

        //https://vk.com/videos-111905078?section=album_273
        //https://vk.com/videos-111905078?section=album_115
        //"https://pvv4.vkuservideo.net/c500602/3/ef7OjU-NjU2OzY/videos/2f35a30306.720.mp4"

        System.setProperty("webdriver.chrome.driver", "src\\main\\java\\by\\AndreiKviatkouski\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://m.vk.com/video-111905078_456243580");
        Document doc = Jsoup.parse(driver.getPageSource());

        Elements links = doc.select("[src*=720.mp4]");
        String link = null;
        for (Element element : links) {
            link = element.attr("abs:src");
        }

        driver.close();
        driver.quit();
        downloadVideo(link);
    }

    public static void downloadVideo(String url) {
        String fileName = "Test";
        if (url == null) {
            Writer.writeString("Empty URL");
            return;
        }
        long start = System.currentTimeMillis();
//        ProgressMonitorInputStream
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
}
