package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Element;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ElementServiceTest {


    @Test
    void create() {
        String url="http://en.wikipedia.org/wiki/Elon_Musk";

        List<String> words = new ArrayList<> (Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk"));

        Map<String, Integer> statistics = new HashMap<>();;
        for (int i = 0; i < words.size(); i++) {
            statistics.put(words.get(i),10*i);
        }

        ElementService elementService = new ElementService();

        Element elementOriginal = new Element(url,
                "Tesla", 0,
                "Musk", 10,
                "Gigafactory", 20,
                "Elon Musk", 30,
                "Total", 60);

        Element element = elementService.create(url, words, statistics);

        assertEquals(elementOriginal.getUrl(), element.getUrl());
        assertEquals(elementOriginal.getParam1(), element.getParam1());
        assertEquals(elementOriginal.getValue1(), element.getValue1());

        assertEquals(elementOriginal.getParam2(), element.getParam2());
        assertEquals(elementOriginal.getValue2(), element.getValue2());

        assertEquals(elementOriginal.getParam3(), element.getParam3());
        assertEquals(elementOriginal.getValue3(), element.getValue3());

        assertEquals(elementOriginal.getParam4(), element.getParam4());
        assertEquals(elementOriginal.getValue4(), element.getValue4());

        assertEquals(elementOriginal.getParam5(), element.getParam5());
        assertEquals(elementOriginal.getValue5(), element.getValue5());
    }

    @Test
    void sortDown() {
    }
}