package by.AndreiKviatkouski;

import by.AndreiKviatkouski.actions.SpiderAction;
import by.AndreiKviatkouski.util.Reader;
import by.AndreiKviatkouski.validator.PropertiesValidator;

import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;


public class SpiderRun {

    public static void main(String[] args) {


        Properties property = Reader.getAllProperties("config.properties"); // get all properties from file

        URL url = PropertiesValidator.checkUrl(property.getProperty("url"));
        Path outputFile1 = PropertiesValidator.chekPath(property.getProperty("outputFile1"));
        
        int MAX_DEEP = PropertiesValidator.checkPageAndDeep
                (Integer.parseInt(property.getProperty("MAX_DEEP")));
        int MAX_PAGES_TO_SEARCH = PropertiesValidator.checkPageAndDeep
                (Integer.parseInt(property.getProperty("MAX_PAGES_TO_SEARCH")));


//        SpiderAction spider = new SpiderAction();
//
//        spider.openFile(outputFile1);
//
//        spider.setStartPage(url);
//        spider.searchRecursive(MAX_DEEP, MAX_PAGES_TO_SEARCH);
//        spider.closeFile();

    }
}