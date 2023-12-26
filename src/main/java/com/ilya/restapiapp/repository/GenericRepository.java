package com.ilya.restapiapp.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T getById(ID id);

    boolean deleteById(ID id);

    T update(T t);

    T save(T t);

    List<T> getAll();

}
