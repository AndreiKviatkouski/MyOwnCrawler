package by.AndreiKviatkouski;

import by.AndreiKviatkouski.util.Writer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
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
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
//        https://m.vk.com/video-111905078_456243555
//        https://m.vk.com/video-111905078_456243547
//        https://m.vk.com/video-111905078_456243580

        driver.get("https://m.vk.com/video-111905078_456243580");
        Document doc = Jsoup.parse(driver.getPageSource());
// Jsoup code here to parse/scrape data
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
