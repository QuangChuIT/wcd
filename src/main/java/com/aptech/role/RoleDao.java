package com.aptech.role;

import com.aptech.common.GenericDao;

import java.util.List;
import java.util.Optional;

public class RoleDao implements GenericDao<Role> {

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public Optional<Role> getById(long id) {
        return Optional.empty();
    }

    @Override
    public void create(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(long id) {

    }
}
