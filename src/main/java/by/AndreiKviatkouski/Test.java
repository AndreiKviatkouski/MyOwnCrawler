package by.AndreiKviatkouski;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {

        String[] links = {"https://m.vk.com/video-111905078_456246013"};

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        webClient.getOptions().setJavaScriptEnabled(false);
//        webClient.getOptions().setCssEnabled(false);

        for (String s : links) {

            HtmlPage page = webClient.getPage(s);
            Document csDoc = Jsoup.parse(page.asXml());
            Elements link = csDoc.select("[src*=.mp4]");

            System.out.println(link);
        }
            webClient.close();
        }
}
