package com.shopapp.shared.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EnvelopeCollectionOut<T> {

    private final List<T> values = new ArrayList<>();
    private int size;

    public EnvelopeCollectionOut(final List<T> values) {
        this.values.addAll(values);
        this.size = values.size();
    }

}
