package by.AndreiKviatkouski.validator;

import by.AndreiKviatkouski.util.Writer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesValidator {

    public static List<String> checkWordsProperty(String wordsInput) {

        if (wordsInput == null) {
            Writer.writeError("Parameter words is empty. Please add parameter in file and restart app!");
            System.exit(1);
        }

        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("([^,]|\n)+");// find words separated by commas
        Matcher matcher = pattern.matcher(wordsInput);

        while (matcher.find()) {
            words.add(matcher.group().trim());// add a word without a space from the beginning and end
        }

        return words;
    }

    public static int checkPageAndDeep(int value) {
        if (value == 0 || value > 10000) {
            Writer.writeError("Parameter is empty or does not match the condition: value != 0 && value <= 10000. " +
                    "Please add parameter in file and restart app!");
            System.exit(1);
        }
        return value;
    }

    public static Path chekPath(String value) {
        Path path = null;
        try {
            path = Paths.get(value);
        } catch (InvalidPathException ipe) {
            ipe.printStackTrace();
        }
        return path;
    }

    public static URL checkUrl(String value) {
        URL url = null;
        try {
             url = new URL(value);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (MalformedURLException e) {
            e.printStackTrace();// the URL is not in a valid form
        } catch (IOException e) {
           Writer.writeError("Invalid URL!");// the connection couldn't be established
        }
        return url;
    }
}
