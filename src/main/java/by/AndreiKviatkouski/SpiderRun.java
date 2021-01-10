package by.AndreiKviatkouski;

import by.AndreiKviatkouski.actions.SpiderAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiderRun {

    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     *
     * @param args - not used
     */
    public static void main(String[] args) {

        List<String> words = new ArrayList<>(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk"));

        SpiderAction spider = new SpiderAction();

        spider.openFile("NewData.csv");
        spider.searchRecursive("http://en.wikipedia.org/wiki/Elon_Musk", words, 8, 10000);
        spider.closeFile();

        spider.openFile("DataSort.csv");
        spider.printSortElementTopTen();
        spider.closeFile();
    }
}