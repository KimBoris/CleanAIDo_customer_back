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
import org.zerock.cleanaido_customer_back.product.entity.ImageFile;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoupangCrawlService {

    private final ProductRepository productRepository;

    public CoupangCrawlService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void crawlAndSaveProducts(String keyword) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);

        try {
            String searchUrl = "https://www.coupang.com/np/search?q=" + keyword;
            driver.get(searchUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-product")));

            List<WebElement> products = driver.findElements(By.cssSelector(".search-product"));
            for (WebElement productElement : products) {
                try {
                    String productCode = productElement.getAttribute("id");
                    WebElement imgElement = productElement.findElement(By.cssSelector("img"));
                    String productName = imgElement.getAttribute("alt");
                    String[] tags = productName.split(" ");
                    String priceText = productElement.findElement(By.cssSelector(".price-value")).getText();
                    int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
                    String thumbnailUrl = imgElement.getAttribute("src");

                    // 상세 페이지 이동 후 상세 이미지 크롤링
                    String productLink = productElement.findElement(By.cssSelector("a")).getAttribute("href");
                    driver.get(productLink);
                    Thread.sleep(3000); // 페이지 로드 대기

                    List<WebElement> detailImages = driver.findElements(By.cssSelector(".product-detail img"));

                    // Product 엔티티 생성
                    Product product = Product.builder()
                            .pcode(productCode)
                            .pname(productName)
                            .ptags(String.join(",", tags))
                            .price(price)
                            .sellerId("user001") // 기본 sellerId 설정
                            .releasedAt(LocalDateTime.now())
                            .quantity(100) // 임의 수량
                            .pstatus("판매중")
                            .build();

                    // 썸네일 이미지 추가
                    product.addImageFile(thumbnailUrl, false);

                    // 상세 이미지 추가
                    for (WebElement detailImage : detailImages) {
                        String detailImageUrl = detailImage.getAttribute("src");
                        product.addUsingImageFile(detailImageUrl);
                    }

                    // Product 엔티티 저장
                    productRepository.save(product);

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
}


