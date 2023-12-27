package com.server.tistoryproject.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class UploadService {
    public String HTMLFileLoad(){
        String directoryPath = "C:\\Users\\tiger\\Downloads\\Save";

        File HTMLfile = findHTMLFile(directoryPath);

        String HTML_CODE = readHTMLFileContent(HTMLfile);

        Document document = Jsoup.parse(HTML_CODE);

        Elements img_elements = document.select("img");
        Elements a_elements = document.select("a");
        String PATH = "C:\\Users\\tiger\\OneDrive\\바탕 화면\\TistoryProjectServer\\TistoryProject\\Img_file\\SEAL Library 코드 분석 및 정리 da4d636a2a3a48e485629a018b369a2e\\";

        for(int i = 0; i < img_elements.size(); i++){
            String[] img_name = img_elements.get(i).attr("src").split("/");

            String newSrc = PATH + img_name[img_name.length - 1].replace("%20", " ");

            img_elements.get(i).attr("src", newSrc);
            a_elements.get(i).attr("href", newSrc);
            System.out.println(newSrc);
        }

        System.out.println(document);

        return document.toString();
    }

    public File findHTMLFile(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            return searchHTMLFile(directory);
        }

        return null;
    }

    public File searchHTMLFile(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".html")) {
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
