package com.appsdeveloperblog.app.ws.data.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

//@Configuration
//@AllArgsConstructor
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    UserEntityInterceptor interceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", interceptor);
    }
}
