package com.rabbitmq.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericConverter<S, T> implements Converter<List<S>, List<T>> {

    @Override
    public List<T> convert(List<S> sources) {
        List<T> targetList = new ArrayList<T>();
        for (S source : sources) {
            T target = convertItem(source);
            targetList.add(target);
        }
        return targetList;
    }

    protected abstract T convertItem(S source);
}
