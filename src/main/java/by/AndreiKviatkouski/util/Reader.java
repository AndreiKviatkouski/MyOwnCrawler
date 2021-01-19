package by.AndreiKviatkouski.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        String line = scanner.nextLine();
        if (line.isEmpty()) {
            Writer.writeString("Was entered empty string,");
            return null;
        }
        return line;

    }

    public static ArrayList<String> readFile(String[] args) {
        String fileName = null;
        for (String arg : args) {
            System.out.println("arg:" + String.valueOf(arg));
            if ("Test.txt".equalsIgnoreCase(arg)){
                fileName=arg;
            }
            //key value
        }
        System.out.println("FILENAME:" + String.valueOf(fileName));
        ArrayList<String> inputFile = new ArrayList<>();
        BufferedReader reader = null;
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
                inputFile.add(line);
            }
            //
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputFile;
    }
}

