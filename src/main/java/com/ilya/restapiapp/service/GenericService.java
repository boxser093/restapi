package com.ilya.restapiapp.service;

import java.util.List;

public interface GenericService <T, ID>{
    T getById(ID id);

    boolean deleteById(ID id);

    T update(T t);

    T create(T t);

    List<T> getAll();

}
