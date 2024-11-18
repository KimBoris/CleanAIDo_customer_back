package org.zerock.cleanaido_customer_back.product.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CoupangCrawlService {

    public List<Map<String, Object>> crawlProducts(String keyword) {
        List<Map<String, Object>> productList = new ArrayList<>();

        // WebDriver 설정
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        try {
            String searchUrl = "https://www.coupang.com/np/search?q=" + keyword;
            driver.get(searchUrl);

            // 페이지 로드 대기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-product")));

            // 상품 리스트 크롤링
            List<WebElement> products = driver.findElements(By.cssSelector(".search-product"));
            for (WebElement product : products) {
                try {
                    // 상품 ID
                    String productCode = product.getAttribute("id");

                    // 상품명 (img 태그의 alt 속성)
                    WebElement imgElement = product.findElement(By.cssSelector("img"));
                    String productName = imgElement.getAttribute("alt");

                    // 태그 (상품명을 띄어쓰기 기준으로 분리)
                    String[] tags = productName.split(" ");

                    // 상품 가격
                    String price = product.findElement(By.cssSelector(".price-value")).getText();

                    // 썸네일 이미지 URL
                    String thumbnailUrl = imgElement.getAttribute("src");

                    // 상품 상세 링크
                    String productLink = product.findElement(By.cssSelector("a")).getAttribute("href");

                    // 임의 데이터
                    String userId = "user001";
                    String releaseDate = "2024-11-18";
                    int stockQuantity = 100;
                    String status = "판매중";

                    // 크롤링 데이터 저장
                    Map<String, Object> productData = new HashMap<>();
                    productData.put("product_code", productCode);
                    productData.put("product_name", productName);
                    productData.put("tags", tags);
                    productData.put("price", price);
                    productData.put("thumbnail_url", thumbnailUrl);
                    productData.put("product_link", productLink);
                    productData.put("user_id", userId);
                    productData.put("release_date", releaseDate);
                    productData.put("stock_quantity", stockQuantity);
                    productData.put("status", status);

                    productList.add(productData);
                } catch (Exception e) {
                    System.err.println("Error processing product: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return productList;
    }
}
