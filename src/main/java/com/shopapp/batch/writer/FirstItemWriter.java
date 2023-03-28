package com.shopapp.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class FirstItemWriter implements ItemWriter<Long> {

    @Override
    public void write(Chunk<? extends Long> chunk) throws Exception {
        System.out.println("inside FirstItemWriter");
        chunk.forEach(System.out::println);
    }
}
