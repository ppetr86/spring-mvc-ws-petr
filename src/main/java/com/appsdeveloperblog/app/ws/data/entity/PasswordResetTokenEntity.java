package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8051324316462829780L;

    @Column(nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user")
    private UserEntity userDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetTokenEntity that)) return false;
        return super.equalsId(o) || token.equals(that.token) && userDetails.equals(that.userDetails);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(token, userDetails);
    }
}
