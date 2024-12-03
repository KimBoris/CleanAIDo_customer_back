//package org.zerock.cleanaido_customer_back.product.service;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.stereotype.Service;
//import org.zerock.cleanaido_customer_back.product.entity.Product;
//import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class DaisoCrawlService {
//
//    private final ProductRepository productRepository;
//
//    public DaisoCrawlService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    public void crawlAndSaveProducts(String keyword) {
//        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-gpu");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//
//        WebDriver driver = new ChromeDriver(options);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//
//        try {
//            String baseUrl = "https://www.daisomall.co.kr";
//            String searchUrl = baseUrl + "/ds/dst/SCR_DST_0015?searchTerm=" + keyword;
//            retryGetPage(driver, searchUrl, wait);
//
//            // 상품 정보 리스트를 먼저 수집
//            List<WebElement> products = driver.findElements(By.cssSelector("div.goods-unit"));
//            List<String> productLinks = new ArrayList<>();
//            System.out.println("크롤링한 상품 개수: " + products.size());
//
//            for (WebElement productElement : products) {
//                try {
//                    String productLink = productElement.findElement(By.cssSelector("div.goods-thumb a")).getAttribute("href");
//                    productLinks.add(buildValidUrl(baseUrl, productLink));
//                } catch (Exception e) {
//                    System.err.println("상품 링크를 추출할 수 없습니다: " + e.getMessage());
//                }
//            }
//
//            // 수집된 링크를 순회하며 상품 처리
//            for (String productLink : productLinks) {
//                try {
//                    retryGetPage(driver, productLink, wait);
//
//                    // 상품 이름 추출
//                    WebElement productNameElement = driver.findElement(By.cssSelector("div.tit"));
//                    String productName = productNameElement.getText().trim();
//
//                    // 상품 코드 추출
//                    String productCode = extractProductCodeFromUrl(productLink);
//
//                    // 가격 추출
//                    String priceText = driver.findElement(By.cssSelector("span.value")).getText();
//                    int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
//
//                    // 썸네일 이미지 추출
//                    WebElement thumbnailElement = driver.findElement(By.cssSelector("div.goods-thumb img"));
//                    String thumbnailUrl = thumbnailElement.getAttribute("src");
//                    if (thumbnailUrl.startsWith("//")) {
//                        thumbnailUrl = "https:" + thumbnailUrl; // 절대 URL로 변환
//                    }
//
//                    // 상세 이미지 추출
//                    List<WebElement> detailImages = driver.findElements(By.cssSelector("div.cms.more img"));
//                    List<String> detailImageUrls = new ArrayList<>();
//                    for (WebElement detailImage : detailImages) {
//                        String detailImageUrl = detailImage.getAttribute("src");
//                        if (detailImageUrl.startsWith("//")) {
//                            detailImageUrl = "https:" + detailImageUrl; // 절대 URL로 변환
//                        }
//                        detailImageUrls.add(detailImageUrl);
//                    }
//
//                    // 태그 추출
//                    List<WebElement> tagElements = driver.findElements(By.cssSelector("div.tag-container span.tag")); // 태그 컨테이너에 따라 변경
//                    List<String> tags = new ArrayList<>();
//                    for (WebElement tagElement : tagElements) {
//                        tags.add(tagElement.getText().trim());
//                    }
//
//                    // Product 생성 및 저장
//                    Product product = Product.builder()
//                            .pcode(productCode)
//                            .pname(productName)
//                            .price(price)
//                            .pstatus("판매중")
//                            .ptags(String.join(",", tags)) // 태그를 쉼표로 연결
//                            .releasedAt(LocalDateTime.now())
//                            .quantity(100)
//                            .sellerId("user001")
//                            .build();
//
//                    // 썸네일 이미지 추가 (type=false)
//                    product.addImageFile(thumbnailUrl, false);
//
//                    // 상세 이미지 추가 (type=true)
//                    int order = 1;
//                    for (String imageUrl : detailImageUrls) {
//                        product.addImageFile(imageUrl, true);
//                        order++;
//                    }
//
//                    // 데이터 저장
//                    if (!productRepository.existsByPcode(productCode)) {
//                        productRepository.save(product);
//                        System.out.println("저장 완료: " + productName);
//                    } else {
//                        System.out.println("이미 저장된 상품입니다: " + productName);
//                    }
//
//                } catch (Exception e) {
//                    System.err.println("상품 처리 중 오류 발생: " + e.getMessage());
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            driver.quit();
//        }
//    }
//
//    private void retryGetPage(WebDriver driver, String url, WebDriverWait wait) throws Exception {
//        int attempts = 0;
//        Exception lastException = null;
//        while (attempts < 3) {
//            try {
//                System.out.println("페이지 로드 시도: " + url + " (시도 횟수: " + (attempts + 1) + ")");
//                driver.get(url);
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.goods-unit, div.cms.more")));
//                System.out.println("페이지 로드 성공: " + url);
//                return;
//            } catch (Exception e) {
//                attempts++;
//                lastException = e;
//                System.err.println("페이지 로드 실패 (시도 횟수: " + attempts + "): " + e.getMessage());
//                Thread.sleep(2000);
//            }
//        }
//        throw new Exception("페이지 로드 실패: " + url, lastException);
//    }
//
//    private String buildValidUrl(String baseUrl, String productLink) {
//        if (productLink.startsWith("http")) {
//            return productLink;
//        }
//        return baseUrl + productLink;
//    }
//
//    private String extractProductCodeFromUrl(String url) {
//        if (url.contains("pdNo=")) {
//            return url.substring(url.indexOf("pdNo=") + 5);
//        }
//        return null;
//    }
//}
