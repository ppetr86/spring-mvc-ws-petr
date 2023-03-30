package com.shopapp.batch.processor;

import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import com.shopapp.data.entitydto.out.AbstractIdBasedDtoOut;
import com.shopapp.data.entitydto.out.CurrencyEntityDtoOut;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Getter
public class Processors {

    private ItemProcessor<CurrencyEntityDtoIn, CurrencyEntity> currencyEntityDtoInCurrencyEntityItemProcessor = CurrencyEntity::new;
    private ItemProcessor<CurrencyEntity, CurrencyEntityDtoOut> currencyEntityCurrencyEntityDtoOutProcessor = CurrencyEntityDtoOut::new;
    private ItemProcessor<CurrencyEntityDtoIn, CurrencyEntityDtoOut> currencyEntityDtoInCurrencyEntityDtoOutItemProcessor = CurrencyEntityDtoOut::new;
    private ItemProcessor<IdBasedEntity, AbstractIdBasedDtoOut> idBasedEntityAbstractIdBasedDtoOutItemProcessor = item -> null;
    private ItemProcessor<Integer, Long> integerLongItemProcessor = item -> (long) item + 20;

}
