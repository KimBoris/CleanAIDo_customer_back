package org.zerock.cleanaido_customer_back.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloadUtil {

    //크롤링 이미지 다운
    public static void downloadImage(String imageUrl, String filePath) {
        try (InputStream in = new URL(imageUrl).openStream();
             FileOutputStream out = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Image downloaded: " + filePath);
        } catch (Exception e) {
            System.err.println("Error downloading image: " + e.getMessage());
        }
    }
}
