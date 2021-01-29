package by.AndreiKviatkouski.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Reader {

    public static Properties getAllProperties(String filename) {
        Properties properties = new Properties();

        try (InputStream input = Reader.class.getClassLoader().getResourceAsStream(filename)) {

            if (input == null) {
                Writer.writeError("Sorry, unable to find " + filename + " Attach the file with the properties and restart the application!!!");
                System.exit(1);
            }
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}

