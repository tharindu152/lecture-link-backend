package lk.ac.iit.lecture_link;

import lk.ac.iit.lecture_link.security.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

//    private final SecurityInterceptor securityInterceptor;
//
//    public WebAppConfig(SecurityInterceptor securityInterceptor) {
//        this.securityInterceptor = securityInterceptor;  
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(securityInterceptor);
//    }
}
