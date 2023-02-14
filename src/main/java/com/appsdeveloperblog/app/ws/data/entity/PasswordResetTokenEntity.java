package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8051324316462829780L;

    private String token;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_uuid")
    private UserEntity userDetails;


}
