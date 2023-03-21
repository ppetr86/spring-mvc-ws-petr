package com.shopapp.data.interceptor;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

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
