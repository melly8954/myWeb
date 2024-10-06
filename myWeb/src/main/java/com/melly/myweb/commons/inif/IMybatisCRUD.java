package com.melly.myweb.commons.inif;

public interface IMybatisCRUD<T> {
    void insert(T dto);
    void update(T dto);
    void deleteById(Long id);
    T findById(Long id);
}
