package by.AndreiKviatkouski.service;

import by.AndreiKviatkouski.entyties.Element;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ElementService {


    public Element create(String url, List<String> words, Map<String, Integer> statistics) {
        String param1 = words.get(0);
        Integer value1 = statistics.get(param1);

        String param2 = words.get(1);
        Integer value2 = statistics.get(param2);

        String param3 = words.get(2);
        Integer value3 = statistics.get(param3);

        String param4 = words.get(3);
        Integer value4 = statistics.get(param4);

        String param5 = "Total";
        Integer value5 = value1 + value2 + value3 + value4;

        return new Element(url, param1, value1, param2, value2, param3, value3, param4, value4, param5, value5);
    }


    public void sortDown(List<Element> elements) {
        Collections.sort(elements, Collections.reverseOrder(Element.compare));
    }
}


