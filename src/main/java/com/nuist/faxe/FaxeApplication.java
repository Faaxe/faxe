package com.nuist.faxe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
/*@ComponentScan(basePackages = "com.nuist.faxe")
@EnableAutoConfiguration*/
//扫描 mapper
@MapperScan("com.nuist.faxe.web.mapper")
public class FaxeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaxeApplication.class, args);
	}
}
