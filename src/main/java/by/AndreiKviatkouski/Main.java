package by.AndreiKviatkouski;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://m.vk.com/video-111905078_456246013");
        Document doc = Jsoup.parse(driver.getPageSource());
// Jsoup code here to parse/scrape data
        Elements link = doc.select("[src*=720.mp4]");

        System.out.println(link);
        driver.close();
        driver.quit();
    }
}
