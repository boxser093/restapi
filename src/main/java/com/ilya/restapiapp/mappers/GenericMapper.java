package com.ilya.restapiapp.mappers;

import java.util.List;

public interface GenericMapper<S,R> {
    R map(S s);
    List<R> map(List<S> s);
}
