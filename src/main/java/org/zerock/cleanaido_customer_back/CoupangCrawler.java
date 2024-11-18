package org.zerock.cleanaido_customer_back;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.zerock.cleanaido_customer_back.product.service.CoupangCrawlService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CoupangCrawler {

    public static List<String> searchProducts(String keyword) {
        List<String> productList = new ArrayList<>();
        try {
            String url = "https://www.coupang.com/np/search?q=" + URLEncoder.encode(keyword, "UTF-8");
            System.out.println("Fetching URL: " + url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .referrer("https://www.google.com")
                    .timeout(30000)
                    .header("Accept-Language", "ko-KR,ko;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Cache-Control", "no-cache")
                    .get();

            Elements productElements = doc.select(".search-product");

            for (Element product : productElements) {
                String productName = product.select(".name").text();
                String productPrice = product.select(".price-value").text();
                String productLink = "https://www.coupang.com" + product.select("a").attr("href");

                if (productName.isEmpty() || productPrice.isEmpty() || productLink.isEmpty()) {
                    continue;
                }

                productList.add("상품명: " + productName + ", 가격: " + productPrice + ", 링크: " + productLink);
            }

            if (productList.isEmpty()) {
                productList.add("Failed to fetch products or no products found.");
            }
        } catch (IOException e) {
            System.err.println("Error during fetching products: " + e.getMessage());
        }
        return productList;
    }

    public static void main(String[] args) {
        CoupangCrawlService service = new CoupangCrawlService();
        List<String> products = service.searchProducts("청소용품");
        products.forEach(System.out::println);
    }

}
