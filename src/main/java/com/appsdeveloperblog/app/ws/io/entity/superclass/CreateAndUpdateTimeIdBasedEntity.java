package com.appsdeveloperblog.app.ws.io.entity.superclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
public abstract class CreateAndUpdateTimeIdBasedEntity extends IdBasedEntity {

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime  createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

}
