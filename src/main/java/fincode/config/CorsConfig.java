package fincode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zlj
 * @date 2023/5/23
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")// 设置允许跨域的路径
                .allowedOrigins("*")// 允许的源地址
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许发送凭据（如cookies）

        // 可以根据需要添加更多的CORS配置
        registry.addMapping("/**")
                .allowedOriginPatterns("*")// 设置允许跨域的路径
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true); // 允许发送凭据（如cookies）
    }
}
