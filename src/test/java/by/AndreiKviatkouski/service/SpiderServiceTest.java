package by.AndreiKviatkouski.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpiderServiceTest {

    private static SpiderService spiderService;

    @BeforeAll
    static void setupPage() throws IOException {

        File page = new File("src/test/resources/spider_service_count_worlds.html");

        Document doc = Jsoup.parse(page, "utf-8");

        spiderService = new SpiderService();
        spiderService.htmlDocument = doc;

    }


    @Test
    @DisplayName("Count words on page ")
    void countWords() {

        int elonCount = spiderService.countWords("Elon Musk");
        int muscCount = spiderService.countWords("Musk");
        int elonCountNoExist = spiderService.countWords("Mask");

        assertEquals(1, elonCount);
        assertEquals(2, muscCount);
        assertEquals(0, elonCountNoExist);
    }

    @Test
    void getLinks() {
        assertNotNull(spiderService.getLinks());
    }
}