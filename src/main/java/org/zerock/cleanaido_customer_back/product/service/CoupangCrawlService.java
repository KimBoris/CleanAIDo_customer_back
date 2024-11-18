package org.zerock.cleanaido_customer_back.product.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoupangCrawlService {

    public List<String> searchProducts(String keyword) {
        List<String> productList = new ArrayList<>();

        // WebDriver Manager로 ChromeDriver 자동 설정
        WebDriverManager.chromedriver().setup();

        // Chrome 옵션 설정 (헤드리스 모드 포함)
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 브라우저 숨기기
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            // 쿠팡 검색 URL (키워드 인코딩 포함)
            String url = "https://www.coupang.com/np/search?q=" + URLEncoder.encode(keyword, "UTF-8");
            driver.get(url);

            // 페이지 로드 대기 (필요시 조정)
            Thread.sleep(10000);

            // 검색 결과 아이템 선택
            List<WebElement> products = driver.findElements(By.cssSelector(".search-product"));

            for (WebElement product : products) {
                try {
                    String productName = product.findElement(By.cssSelector(".name")).getText();
                    String productPrice = product.findElement(By.cssSelector(".price-value")).getText();
                    String productLink = product.findElement(By.cssSelector("a")).getAttribute("href");

                    productList.add("상품명: " + productName + ", 가격: " + productPrice + ", 링크: " + productLink);
                } catch (Exception e) {
                    System.err.println("Error parsing product: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            productList.add("Failed to fetch products or no products found.");
        } finally {
            driver.quit();
        }

        return productList;
    }
}
