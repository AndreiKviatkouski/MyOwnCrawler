package by.AndreiKviatkouski;

import by.AndreiKviatkouski.actions.SpiderAction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrei Kviatkouski
 * @version 1.1
 * @since 1.0
 */

public class SpiderRun {

    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     *
     * @param args - not used
     */

    public static void main(String[] args) {

        List<String> words = new ArrayList<>(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Musk"));

        SpiderAction spider = new SpiderAction();
// oytput="lala .cxs"

        //java filename.jar output="output.sorted.csv" url="http://en.wikipedia.org/wiki/Elon_Musk" worlds="world1 , world2"

        spider.openFile("src\\main\\java\\by\\AndreiKviatkouski\\data\\NewData.csv");

        spider.setStartPage("http://en.wikipedia.org/wiki/Elon_Musk");
        spider.searchRecursive(words, 8, 15);
        spider.closeFile();

        spider.openFile("src\\main\\java\\by\\AndreiKviatkouski\\data\\DataSort.csv");
        spider.printSortElementTopTen();
        spider.closeFile();
    }
}