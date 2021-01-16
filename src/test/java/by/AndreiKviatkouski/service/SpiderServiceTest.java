package by.AndreiKviatkouski.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpiderServiceTest {

    SpiderService spiderService;

    @BeforeAll
    static void setupPage() throws IOException {

        File page = new File("src/test/resources/spider_service_count_worlds.html");

        Document doc = Jsoup.parse(page, "utf-8");

        String str = doc.body().text();
        System.out.println(str);

        SpiderService spiderService = new SpiderService();
        spiderService.htmlDocument = doc;
    }

//    @AfterAll
//    void removeData(){
//         System.out.println("Hello World form SpiderServiceTest.removeData");
//    }


//    @Test
//    void crawl() {
//    }

    @Test
    void countWords() {
        int elonCount = spiderService.countWords("Elon Musk");
        int muscCount = spiderService.countWords("Musk");

        int elonCountNoExist = spiderService.countWords("Elon");

        assertEquals(1, elonCount);
        assertEquals(1, muscCount);
        assertEquals(0, elonCountNoExist);
    }

//    @Test
//    void getLinks() {
//    }
}