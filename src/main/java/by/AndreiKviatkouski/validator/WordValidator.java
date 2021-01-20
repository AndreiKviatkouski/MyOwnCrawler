package by.AndreiKviatkouski.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordValidator {

    public static Matcher check(String word, String str) {
        Pattern pattern = Pattern.compile(word);
        return pattern.matcher(str);
    }
}