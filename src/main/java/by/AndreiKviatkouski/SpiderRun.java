package by.AndreiKviatkouski;

import by.AndreiKviatkouski.actions.SpiderAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiderRun {

    public static void main(String[] args) {

        List<String> words = new ArrayList<>(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk"));

        SpiderAction spider = new SpiderAction();

        spider.openFile("NewData.csv");
        spider.setStartPage("http://en.wikipedia.org/wiki/Elon_Musk");
        spider.searchRecursive( words, 8, 100);
        spider.closeFile();

        spider.openFile("DataSort.csv");
        spider.printSortElementTopTen();
        spider.closeFile();
    }
}