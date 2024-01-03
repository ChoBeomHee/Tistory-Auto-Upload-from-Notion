package com.server.tistoryproject.Service;

import com.server.tistoryproject.Model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class CrawlingService {
    public String getContent(String url) throws IOException {
        System.out.println("주소 ==================== " + url);
        Document document = Jsoup.connect(url).get();

        Elements contentElement;
        contentElement = document.select("div[class=page-body]");
        contentElement.select("div.another_category").remove();
        contentElement.select("div.container_postbtn").remove();
        contentElement.select("p").attr("data-ke-size", "size16");
    //        contentElement.select("ul").unwrap();
    //        contentElement.select("ol").unwrap();
    //        contentElement.select("figure").unwrap();
    //        contentElement.select("pre").unwrap();
    //        contentElement.select("article").unwrap();

        //contentElement.select(":not(li)").remove();


        System.out.println();
        System.out.println();
        System.out.println("=============" + contentElement);

        return contentElement.toString();
    }
    public Post crawlingSelenium(String url) throws InterruptedException, IOException {
        Post post = new Post();

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        Runtime.getRuntime().exec("C:/Program Files/Google/Chrome/Application/chrome.exe --remote-debugging-port=9222 --user-data-dir=\"C:/Selenium/ChromeData\""); // 크롬 디버깅 모드
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222"); // 해당 포트로 selenium 열기
        WebDriver driver = new ChromeDriver(options);
        Dimension dimension = new Dimension(800, 600);
        driver.manage().window().setSize(dimension);
        driver.get(url);
        Thread.sleep(10000);
        post.setTitle(driver.getTitle());

        driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[1]/header/div[1]/div/div[4]/div[2]/div[5]")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div[2]/div/div/div/div/div/div[1]/div[7]/div[2]")).click();
//        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/svg")).click();
//        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[3]/div/div[2]/div[2]/div/div/div/div/div/div/div/div[2]/div")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[7]/div[2]")).click();
//        ArrayList<WebElement> contents;
//        contents = (ArrayList<WebElement>) driver.findElements(By.className("notion-page-content"));
//
//        StringBuilder html_code = new StringBuilder();
//
//        for (WebElement content : contents) {
//            html_code.append(content.getAttribute("outerHTML"));
//        }
//
//        List<WebElement> imageElements = contents.get(0).findElements(By.tagName("img"));
//        WebElement imgs = imageElements.get(0);
//
//        imgs.click();
//        Actions actions = new Actions(driver);
//        actions.contextClick(imgs).perform();
//        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div[2]/div/div/div/div/div[2]/div[1]/div[3]/div[2]")).click();
//
//        switch_img_url(driver);
//
//        Thread.sleep(10000);
//
//        String img_url = driver.getCurrentUrl();
//        System.out.println(img_url);
//
//        String HTML = html_code.toString().replace(imgs.getAttribute("outerHTML"), img_url);
//
//        driver.close();
//
//        switch_main_url(driver);
//
//        System.out.println(imgs.getAttribute("outerHTML"));
//        System.out.println(img_url);
//
//        post.setContent(HTML);
//        driver.close();
//        driver.quit();

        Thread.sleep(16000); // download 기다림

        String filePath = "C:\\Users\\tiger\\Downloads";
        String SavePath = "C:\\Users\\tiger\\OneDrive\\바탕 화면\\TistoryProjectServer\\TistoryProject\\src\\main\\resources\\static\\webapp\\Img_file";

        File zipFile = new File(filePath);
        File[] zipFiles = zipFile.listFiles((dir, name) -> name.endsWith(".zip"));

        String ZipFileAbs = "";

        if (zipFiles != null && zipFiles.length > 0) {
            Arrays.sort(zipFiles, Comparator.comparingLong(File::lastModified).reversed());

            // 가장 최근에 수정된 파일 출력
            File mostRecentZipFile = zipFiles[0];
            ZipFileAbs = mostRecentZipFile.getAbsolutePath();
        }
        String names = Unzip(ZipFileAbs, SavePath);
        String[] FILENAME = names.split("/");
        post.setDownload_filename(FILENAME[0]);
        driver.close();
        driver.quit();

        return post;
    }

    public String Unzip(String ZipFilePath, String DestFilePath) throws IOException{
        File Destination_dir = new File(DestFilePath);
        String name ="";
        if(!Destination_dir.exists()){
            Destination_dir.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(ZipFilePath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        while(zipEntry != null){
            String File_Path = DestFilePath + File.separator + zipEntry.getName();
            name = zipEntry.getName();
            if(!zipEntry.isDirectory()){
                extractFile(zipInputStream, File_Path);
            }else{
                File directory = new File(File_Path);
                directory.mkdirs();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();

        return name;
    }
    public void extractFile(ZipInputStream zipInputStream, String File_Path) throws IOException {
        File file = new File(File_Path);

        // Ensure that the directory exists
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }


        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(File_Path));
        byte[] Bytes = new byte[4096];
        int Read_Byte = 0;
        while((Read_Byte = zipInputStream.read(Bytes)) != -1){
            bufferedOutputStream.write(Bytes, 0, Read_Byte);
        }

        bufferedOutputStream.close();
    }

    public void test(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.naver.com/");

        System.out.println(driver.getPageSource());

        driver.quit();
    }

    public String getTitle(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements element = document.select("meta[name=title]");

        return element.attr("content");
    }
}
