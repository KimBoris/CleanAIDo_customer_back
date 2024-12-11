package org.zerock.cleanaido_customer_back.common.runner;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EnvRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // .env 파일 읽기
        Dotenv dotenv = Dotenv.load();

        // 예시: 모든 키-값 출력
        dotenv.entries().forEach(entry -> {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            log.info(entry.getKey() + "=" + entry.getValue());
        });
    }
}
