package com.aptech.common;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    List<T> getAll();

    Optional<T> get(long id);

    void save(T obj);

    void update(T obj);

    void delete(long id);
}
