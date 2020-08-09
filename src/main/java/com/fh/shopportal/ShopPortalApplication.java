package com.fh.shopportal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shopportal.*.mapper")
public class ShopPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPortalApplication.class, args);
    }

}
