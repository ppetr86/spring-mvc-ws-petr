package com.shopapp.batch.writer;

import com.shopapp.data.entitydto.CurrencyEntityDtoIn;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class SecondItemWriter implements ItemWriter<CurrencyEntityDtoIn> {

    @Override
    public void write(Chunk<? extends CurrencyEntityDtoIn> chunk) throws Exception {
        System.out.println("inside SecondItemWriter");
        for(var each : chunk){
            System.out.println(each);
        }
    }
}
