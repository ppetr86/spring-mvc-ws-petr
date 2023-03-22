package com.shopapp.data.entitydto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressCreditCardEntityCounterDto {

    private long userCount = 0;
    private long addressCount = 0;
    private long creditCardCount = 0;

    public UserAddressCreditCardEntityCounterDto() {
    }

    public UserAddressCreditCardEntityCounterDto(long userCount, long addressCount, long creditCardCount) {
        this.userCount = userCount;
        this.addressCount = addressCount;
        this.creditCardCount = creditCardCount;
    }

    public UserAddressCreditCardEntityCounterDto(long userCount, long addressCount) {
        this.userCount = userCount;
        this.addressCount = addressCount;
    }
}
