package com.shopapp.data.hibernatelisteners;

import com.shopapp.data.interceptor.EncryptedString;
import com.shopapp.service.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@AllArgsConstructor
public abstract class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    public void decrypt(Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity), this::isFieldEncrypted);
    }

    public void encrypt(Object[] state, String[] propertyNames, Object entity) {
        ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames), this::isFieldEncrypted);
    }

    public int getPropertyIndex(String name, String[] properties) {
        for (int i = 0; i < properties.length; i++) {
            if (name.equals(properties[i])) {
                return i;
            }
        }

        //should never get here...
        throw new IllegalArgumentException("Property not found: " + name);
    }

    public boolean isFieldEncrypted(Field field) {
        return AnnotationUtils.findAnnotation(field, EncryptedString.class) != null;
    }

    private void decryptField(Field field, Object entity) {
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        ReflectionUtils.setField(field, entity, encryptionService.decrypt(value.toString()));
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames) {
        int idx = getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[idx];
        state[idx] = encryptionService.encrypt(currentValue.toString());
    }
}
