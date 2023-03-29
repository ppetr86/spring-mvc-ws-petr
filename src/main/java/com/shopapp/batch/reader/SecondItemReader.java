package com.shopapp.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class SecondItemReader implements ItemReader<Integer> {
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    int i = 0;

    @Override
    public Integer read() throws Exception, ParseException, NonTransientResourceException {
        System.out.println("inside item reader");
        if (i < list.size())
            return list.get(i++);
        return null;
        //null stops execution of the reader
    }
}
