package com.server.tistoryproject.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Service
public class HTMLService {
    public String HTMLFileLoad(String fileName){
        String directoryPath = "C:\\Users\\tiger\\OneDrive\\바탕 화면\\TistoryProjectServer\\TistoryProject\\src\\main\\resources\\static\\webapp\\Img_file";
        System.out.println(fileName);
        File HTMLfile = findHTMLFile(directoryPath, fileName);
        String HTML_FILE_NAME = findHTMLFile_NAME(directoryPath).replace(".html", "");
        System.out.println(HTML_FILE_NAME);
        String HTML_CODE = readHTMLFileContent(HTMLfile);

        Document document = Jsoup.parse(HTML_CODE);

        Elements img_elements = document.select("img");
        Elements a_elements = document.select("a");

        for(int i = 0; i < img_elements.size(); i++){
            String[] img_name = img_elements.get(i).attr("src").split("/");

            String newSrc = "http://localhost:8080/image/?FolderName="
                    + HTML_FILE_NAME + "&"
                    + "FileName=" + img_name[img_name.length - 1];
            System.out.println(img_name[img_name.length - 1]);
            img_elements.get(i).attr("src", newSrc);
            a_elements.get(i).attr("href", newSrc);
            System.out.println(newSrc);
        }

        System.out.println(document);

        return document.toString();
    }

    public File findHTMLFile(String directoryPath, String filename) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            return searchHTMLFile(directory, filename);
        }

        return null;
    }

    public String findHTMLFile_NAME(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            return searchHTMLFile_NAME(directory);
        }

        return null;
    }

    public String searchHTMLFile_NAME(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".html")) {
                    // 확장자가 ".html"인 파일을 찾으면 반환
                    return file.getName();
                }
            }
        }

        return null;
    }

    public File searchHTMLFile(File directory, String filename) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(filename + ".html")) {
                    // 확장자가 ".html"인 파일을 찾으면 반환
                    return file;
                }
            }
        }

        return null;
    }

    public String readHTMLFileContent(File htmlFile) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(htmlFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }


}
