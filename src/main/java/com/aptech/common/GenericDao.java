package com.aptech.common;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Serializable > {
    List<T> getAll();

    Optional<T> getById(long id);

    void create(T o);

    void update(T o);

    void delete(T o);
}
