package com.appsdeveloperblog.app.ws;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public final class DaoUtil {

    public static UUID generateId(final CrudRepository<?, UUID> dao) {
        return UUID.randomUUID();
    }


}
