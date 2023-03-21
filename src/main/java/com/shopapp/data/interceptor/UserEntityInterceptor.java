package com.shopapp.data.interceptor;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Component
public class UserEntityInterceptor implements Interceptor {

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        System.out.println("Hibernate interceptor intercepted load method");
        //the state param comes in as null....
        //Object[] newState = processFields(entity, state, propertyNames, "onLoad");
        return Interceptor.super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {

        System.out.println("Hibernate interceptor intercepted save method");
        //Object[] newState = processFields(entity, state, propertyNames, "onSave");
        return Interceptor.super.onSave(entity, id, state, propertyNames, types);
    }

    private List<String> getAnnotationFields(Object entity) {
        List<String> annotatedFields = new ArrayList<>();
        for (var field : entity.getClass().getDeclaredFields()) {
            if (!Objects.isNull(field.getAnnotation(LoadCounter.class))) {
                annotatedFields.add(field.getName());
            }
        }
        return annotatedFields;
    }

    private Object[] processFields(Object entity, Object[] state, String[] propertyNames, String type) {
        List<String> annotationFields = getAnnotationFields(entity);

        for (String field : annotationFields) {
            for (int i = 0; i < propertyNames.length; i++) {
                if (propertyNames[i].equals(field)) {
                    if (StringUtils.hasText(state[i].toString())) {
                        if ("onSave".equals(type) || "onLoad".equals(type)) {
                            state[i] = Integer.parseInt(state[i].toString()) + 1;
                        }
                    }
                }
            }
        }

        return state;
    }
}
