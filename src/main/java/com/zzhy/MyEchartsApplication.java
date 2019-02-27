package com.zzhy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author majt
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class MyEchartsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyEchartsApplication.class, args);
    }
}