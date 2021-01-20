package by.AndreiKviatkouski;

import by.AndreiKviatkouski.actions.SpiderAction;
import by.AndreiKviatkouski.util.Reader;
import by.AndreiKviatkouski.validator.PropertiesValidator;
import by.AndreiKviatkouski.validator.WordValidator;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        Properties property = Reader.getAllProperties("config.properties"); // get all properties from file

        URL url = PropertiesValidator.checkUrl(property.getProperty("url"));
        Path outputFile1 = PropertiesValidator.chekPath(property.getProperty("outputFile1"));
        Path outputFile2 = PropertiesValidator.chekPath(property.getProperty("outputFile2"));
        List<String> words = PropertiesValidator.checkWordsProperty
                (property.getProperty("words"));
        int MAX_DEEP = PropertiesValidator.checkPageAndDeep
                (Integer.parseInt(property.getProperty("MAX_DEEP")));
        int MAX_PAGES_TO_SEARCH = PropertiesValidator.checkPageAndDeep
                (Integer.parseInt(property.getProperty("MAX_PAGES_TO_SEARCH")));


        SpiderAction spider = new SpiderAction();

        spider.openFile(outputFile1);

        spider.setStartPage(url);
        spider.searchRecursive(words, MAX_DEEP, MAX_PAGES_TO_SEARCH);
        spider.closeFile();

        spider.openFile(outputFile2);
        spider.printSortElementTopTen();
        spider.closeFile();
    }
}