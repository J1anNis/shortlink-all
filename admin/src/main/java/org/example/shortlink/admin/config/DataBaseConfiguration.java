package org.example.shortlink.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 标识这是一个配置类，Spring会自动扫描并加载
public class DataBaseConfiguration {

    /**
     * 分页插件
     *
     * @return MyBatis-Plus拦截器对象
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        // 1. 创建MyBatis-Plus的拦截器容器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2. 创建MySQL分页拦截器并添加到容器中
        // PaginationInnerInterceptor是MyBatis-Plus提供的分页处理器
        // DbType.MYSQL指定数据库类型为MySQL，分页插件会根据数据库类型生成对应的分页SQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 3. 返回配置好的拦截器容器
        return interceptor;
    }
}
