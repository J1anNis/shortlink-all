package org.example.shortlink.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("org.example.shortlink.admin.dao.mapper")
public class ShortLinkAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortLinkAdminApplication.class, args);
    }
}

/**
 * SpringApplication.run(...) 方法会：
 * 创建 Spring 应用上下文（ApplicationContext）。
 * 触发自动配置与组件扫描，初始化所有 Bean。
 * 启动嵌入式服务器（如 Tomcat，由 spring-boot-starter-web 自动配置）。
 * 项目启动后，Tomcat 会监听配置的端口（默认 8080，可通过 application.yml 配置为 8002），等待 HTTP 请求。
 */