package org.zerock.cleanaido_customer_back.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoupangCrawler {
    public static void main(String[] args) {
        // WebDriver 설정
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저 숨기기
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);

        try {
            // 쿠팡 검색 URL
            String searchUrl = "https://www.coupang.com/np/search?q=변기";
            driver.get(searchUrl);

            // 결과 저장
            List<Map<String, Object>> productList = new ArrayList<>();

            // 상품 리스트 크롤링
            List<WebElement> products = driver.findElements(By.cssSelector(".search-product"));
            for (WebElement product : products) {
                try {
                    // 상품 ID (li 태그의 id 속성)
                    String productCode = product.getAttribute("id");

                    // 상품명 (img 태그의 alt 속성)
                    WebElement imgElement = product.findElement(By.cssSelector("img"));
                    String productName = imgElement.getAttribute("alt");

                    // 태그 (상품명을 띄어쓰기 기준으로 분리)
                    String[] tags = productName.split(" ");

                    // 가격
                    String price = product.findElement(By.cssSelector(".price-value")).getText();

                    // 썸네일 이미지
                    String thumbnailUrl = imgElement.getAttribute("src");
                    downloadImage(thumbnailUrl, "thumbnail_" + productCode + ".jpg");

                    // 상세 페이지 링크
                    String productLink = product.findElement(By.cssSelector("a")).getAttribute("href");

                    // 상세 이미지 다운로드
                    List<String> detailImages = downloadDetailImages(driver, productLink);

                    // 임의 데이터
                    String userId = "user001";
                    String releaseDate = "2024-11-18";
                    int stockQuantity = 100;
                    String status = "판매중";

                    // 결과 저장
                    Map<String, Object> productData = new HashMap<>();
                    productData.put("product_code", productCode);
                    productData.put("product_name", productName);
                    productData.put("tags", tags);
                    productData.put("price", price);
                    productData.put("thumbnail_url", thumbnailUrl);
                    productData.put("detail_images", detailImages);
                    productData.put("user_id", userId);
                    productData.put("release_date", releaseDate);
                    productData.put("stock_quantity", stockQuantity);
                    productData.put("status", status);

                    productList.add(productData);
                } catch (Exception e) {
                    System.err.println("Error processing product: " + e.getMessage());
                }
            }

            // 결과 출력
            productList.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // 상세 페이지 이미지 다운로드
    private static List<String> downloadDetailImages(WebDriver driver, String productLink) {
        List<String> detailImageUrls = new ArrayList<>();
        try {
            driver.get(productLink);
            Thread.sleep(3000); // 페이지 로드 대기

            // 상세 이미지 선택
            List<WebElement> detailImages = driver.findElements(By.cssSelector(".product-detail img"));
            for (WebElement detailImage : detailImages) {
                String detailImageUrl = detailImage.getAttribute("src");
                detailImageUrls.add(detailImageUrl);
                downloadImage(detailImageUrl, "detail_" + System.currentTimeMillis() + ".jpg");
            }
        } catch (Exception e) {
            System.err.println("Error downloading detail images: " + e.getMessage());
        }
        return detailImageUrls;
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