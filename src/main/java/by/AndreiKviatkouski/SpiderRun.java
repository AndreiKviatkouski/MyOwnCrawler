package by.AndreiKviatkouski;

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
        ArrayList<String> words = new ArrayList<String>(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Mask"));

        Spider spider = new Spider();

        //for (String word : words) {
        //    spider.search("http://en.wikipedia.org/wiki/Elon_Musk", words,0);
        //    spider.writeResults();
       //}

        spider.openFile("filename.csv", true);
        spider.searchRec("http://en.wikipedia.org/wiki/Elon_Musk", words, 8);
        spider.closeFile();


        //spider.search(baseUrl, listOfWors);

//        spider.search("http://en.wikipedia.org/wiki/Elon_Musk", "Tesla");
//        spider.writeResults();
//        spider.search("http://en.wikipedia.org/wiki/Elon_Musk", "Musk");
//        spider.writeResults();

    }
}