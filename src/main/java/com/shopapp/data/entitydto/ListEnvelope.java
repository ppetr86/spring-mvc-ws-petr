package com.shopapp.data.entitydto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListEnvelope<T> {

    private int size;
    private List<T> data;

    public ListEnvelope(List<T> data) {
        this.data = data;
        this.size = data.size();
    }
}
