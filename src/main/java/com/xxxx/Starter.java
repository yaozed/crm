package com.xxxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther:姚泽栋
 * @Date: 2023/1/4 - 01 - 04 - 10:19
 * @Description: IntelliJ IDEA
 * @version: 1.0
 */
@SpringBootApplication(scanBasePackages="com.xxxx.crm.Controller")
@ComponentScan("com.xxxx.crm.*")
@MapperScan("com.xxxx.crm.dao")
public class Starter extends SpringBootServletInitializer {
   public static void main(String[] args) {
      SpringApplication.run(Starter.class);
   }

   /**
    * Configure the application. Normally all you would need to do is to add sources
    * (e.g. config classes) because other settings have sensible defaults. You might
    * choose (for instance) to add default command line arguments, or set an active
    * Spring profile.
    *
    * @param builder a builder for the application context
    * @return the application builder
    * @see SpringApplicationBuilder
    */
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(Starter.class);
   }
}
