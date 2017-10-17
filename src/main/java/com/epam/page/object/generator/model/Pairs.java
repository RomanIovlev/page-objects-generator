package com.epam.page.object.generator.model;

import com.epam.commons.pairs.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Roman_Iovlev on 10/16/2017.
 */
public class Pairs extends ArrayList<Pair<String, String>> {

    public Pairs(String[] array,
         Function<String, String> value1func,
         Function<String, String> value2func) {
        for (String el : array)
            add(new Pair<>(value1func.apply(el), value2func.apply(el)));
    }

    public String first(Function<String, Boolean> condition) {
        for (Pair<String, String> pair : this) {
            if (condition.apply(pair.key)) {
                remove(pair);
                return pullValue(pair);
            }
        }
        return null;
    }

    public List<String> filter(Function<String, Boolean> condition) {
        List<String> result = new ArrayList<>();
        for (Pair<String, String> pair : this)
            if (condition.apply(pair.key))
                result.add(pullValue(pair));
        return result;
    }

    public <T> List<T> filterAndMap(Function<String, Boolean> condition,
             Function<Pair<String, String>, T> map) {
        List<T> result = new ArrayList<>();
        for (Pair<String, String> pair : this)
            if (condition.apply(pair.key))
                result.add(map.apply(pullPair(pair)));
        return result;
    }

    private String pullValue(Pair<String, String> pair) {
        remove(pair);
        return pair.value;
    }

    private Pair<String, String> pullPair(Pair<String, String> pair) {
        remove(pair);
        return pair;
    }
}
