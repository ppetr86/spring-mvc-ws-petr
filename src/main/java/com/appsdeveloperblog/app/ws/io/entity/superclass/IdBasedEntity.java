package com.appsdeveloperblog.app.ws.io.entity.superclass;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;



}
