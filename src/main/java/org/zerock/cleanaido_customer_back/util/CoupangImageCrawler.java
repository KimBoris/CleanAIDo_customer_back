package org.zerock.cleanaido_customer_back.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

//쿠팡 이미지 크롤링
public class CoupangImageCrawler {

    public static void main(String[] args) {
        // WebDriver 설정
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저 숨기기
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);

        try {
            // 쿠팡 검색 결과 페이지 URL
            String url = "https://www.coupang.com/np/search?q=변기";
            driver.get(url);

            // 썸네일 이미지 다운로드
            List<WebElement> products = driver.findElements(By.cssSelector(".search-product"));
            for (WebElement product : products) {
                try {
                    // 썸네일 이미지 추출
                    WebElement thumbnail = product.findElement(By.cssSelector("img"));
                    String thumbnailUrl = thumbnail.getAttribute("src");
                    System.out.println("Downloading thumbnail: " + thumbnailUrl);

                    // 썸네일 이미지 다운로드
                    downloadImage(thumbnailUrl, "thumbnail_" + System.currentTimeMillis() + ".jpg");

                    // 상세 페이지 링크 추출
                    WebElement linkElement = product.findElement(By.cssSelector("a"));
                    String productLink = linkElement.getAttribute("href");

                    // 상세 이미지 다운로드
                    downloadProductDetails(driver, productLink);
                } catch (Exception e) {
                    System.err.println("Error processing product: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // 상세 페이지 이미지 다운로드
    private static void downloadProductDetails(WebDriver driver, String productLink) {
        try {
            driver.get(productLink);
            Thread.sleep(3000); // 페이지 로드 대기

            // 상세 이미지 추출
            List<WebElement> detailImages = driver.findElements(By.cssSelector(".product-detail img"));
            for (WebElement detailImage : detailImages) {
                String detailImageUrl = detailImage.getAttribute("src");
                System.out.println("Downloading detail image: " + detailImageUrl);

                // 상세 이미지 다운로드
                downloadImage(detailImageUrl, "detail_" + System.currentTimeMillis() + ".jpg");
            }
        } catch (Exception e) {
            System.err.println("Error downloading product details: " + e.getMessage());
        }
    }

    // 이미지 다운로드 유틸리티
    private static void downloadImage(String imageUrl, String fileName) {
        try (InputStream in = new URL(imageUrl).openStream();
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Image downloaded: " + fileName);
        } catch (Exception e) {
            System.err.println("Error downloading image: " + e.getMessage());
        }
    }
}
