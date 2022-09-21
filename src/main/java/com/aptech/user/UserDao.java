package com.aptech.user;

import com.aptech.common.GenericDao;
import com.aptech.utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements GenericDao<User> {
    private final static String GET_DATA = "select * from user";
    private static UserDao instance;

    private UserDao() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_DATA);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                Long id = resultSet.getLong(0);
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);
                String mobile = resultSet.getNString(3);
                String email = resultSet.getString(4);
                String address = resultSet.getString(5);
                user.setId(id);
                user.setUsername(username);
                user.setAddress(address);
                user.setEmail(email);
                user.setPassword(password);
                user.setMobile(mobile);
                users.add(user);
            }
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(resultSet);
            DatabaseUtil.getInstance().closeObject(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
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
