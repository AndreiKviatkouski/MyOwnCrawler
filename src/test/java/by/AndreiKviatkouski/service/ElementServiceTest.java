package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementServiceTest {


    @Test
    void create() {
        String url, List<String> words, Map<String, Integer> statistics

        ElementService elementService = new ElementService();

        Element elementOriginal = new Element(url, param1, value1, param2, value2, param3, value3, param4, value4, param5, value5);
        Element element = elementService.create();

        assertEquals(elementOriginal.getUrl(), element.getUrl());
        assertEquals(elementOriginal.getParam1(), element.getParam1());
    }

    @Test
    void sortDown() {
    }
}