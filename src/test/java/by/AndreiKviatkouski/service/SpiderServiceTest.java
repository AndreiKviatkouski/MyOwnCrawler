package by.AndreiKviatkouski.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpiderServiceTest {

    SpiderService spiderService;

    @BeforeAll
    void setupPage(){
        String path =

        this.spiderService = new SpiderService();
        //this.spiderService.htmlDocument = Connection.
    }

    @AfterAll
    void removeData(){
         System.out.println("Hello World form SpiderServiceTest.removeData");
    }


    @Test
    void crawl() {
    }

    @Test
    void countWorlds() {
        int elonCount = this.spiderService.countWorlds("Elon_Musk");
        int muscCount = this.spiderService.countWorlds("Musk");

        int elonCountNoExist = this.spiderService.countWorlds("Elon");

        assertEquals(1, elonCount);
        assertEquals(1, muscCount);
        assertEquals(0, elonCountNoExist);
    }

    @Test
    void getLinks() {
    }
}