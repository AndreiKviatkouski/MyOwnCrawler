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



//    private static String fileName(String[] args) {
//        String fileName;
//        for (String arg : args) {
//            if ("C:\\Users\\andre\\Desktop\\MyOwnCrawler\\Test.txt".equalsIgnoreCase(arg)) {
//                fileName = arg;
//                return fileName;
//            }
//
//        }
//        return null;
//    }

//    public static ArrayList<String> readFile(String[] args) {
//
//        String fileName = fileName(args);
//        if (fileName == null) {
//            Writer.writeError("File not exist!Attach the file with the properties and restart the application!!!");
//            System.exit(1);
//        }
//
//        ArrayList<String> inputParams = new ArrayList<>();
//
//        BufferedReader reader = null;
//        try {
//            File file = new File(fileName);
//            FileReader fr = new FileReader(file);
//            reader = new BufferedReader(fr);
//            String line = reader.readLine();
//            while (line != null) {
//                System.out.println(line);
//                line = reader.readLine();
//                inputParams.add(line);
//            }
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return inputParams;
//    }




}

