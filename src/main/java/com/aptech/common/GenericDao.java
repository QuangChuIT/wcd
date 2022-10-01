package com.aptech.common;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Serializable > {
    List<T> getAll();

    Optional<T> getById(long id);

    void create(T entity);

    void update(T entity);

    void delete(long id);
}
