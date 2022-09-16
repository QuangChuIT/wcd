package com.aptech.user;

import com.aptech.common.GenericDao;

import java.util.List;
import java.util.Optional;

public class UserDao implements GenericDao<User> {

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    @Override
    public void save(User obj) {

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(long id) {

    }

    public User findUser(String username, String password) {
        return new User("quangcv", "123abc@A", "085467025", "quangcv@vnpt.vn", "Vinh Phuc");
    }
}
