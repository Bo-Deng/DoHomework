
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import static java.nio.file.Files.readAllLines;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author deng_875452
 */
public class BlockWebsite {
    
    public static void main(String[] args) {
        ArrayList<String> urlsToBeBlocked = new ArrayList<String> ();
        urlsToBeBlocked.add("google.com");
        urlsToBeBlocked.add("youtube.com");
        blockURLs(urlsToBeBlocked);
    }
    
    public static void blockURLs(ArrayList<String> urls) {
        for (String url : urls) {
            if (url.contains("www.")) {
                url = url.replace("www.", "");
            }

            String toWrite = "127.0.0.1 \t" + url + "\n";
            String toWriteWWW = "127.0.0.1 \t" + "www." + url + "\n";

            try {
                Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), toWrite.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), toWriteWWW.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("File inaccessible");
                System.out.println(e.toString());
            }
        }
    }
    
    public static void unBlockURLs(ArrayList<String> urls) throws IOException {
        List<String> allLines = readAllLines(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), Charset.defaultCharset());
        int origLength = allLines.size();
        for (int i = origLength; i > origLength - 2 * urls.size(); i--) {
            allLines.remove(allLines.size() - 1);
        }
        
        for (String line : allLines) {
            Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), (line + "\n").getBytes());
        }
    }
    
    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    public static void blockAllBut(ArrayList<String> urls) throws IOException {
        for (String url : urls) {
            if (url.contains("www.")) {
                url = url.replace("www.", "");
            }
            
            String line = "www." + url + "\t\t" + "www." + url + "\n";
            Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), (line + "\n").getBytes(), StandardOpenOption.APPEND);
        }
        String toWrite = "127.0.0.1\t\t*.*.*.*\n";
        Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), (toWrite + "\n").getBytes(), StandardOpenOption.APPEND);
    }
    
    public static void unBlockAll() throws IOException {
        Files.write(Paths.get("C:", "WINDOWS", "System32", "drivers", "etc", "hosts"), ("").getBytes());
    }
}
