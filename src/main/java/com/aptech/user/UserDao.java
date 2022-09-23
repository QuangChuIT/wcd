package com.aptech.user;

import com.aptech.common.GenericDao;
import com.aptech.utils.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements GenericDao<User> {
    private final static String GET_DATA = "select * from user";
    private static UserDao instance;
    private final static Logger LOGGER = LogManager.getLogger(UserDao.class);

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
                Long id = resultSet.getLong("id");
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
            DatabaseUtil.getInstance().closeObject(statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> get(long id) {
        String sql = "select * from user where id = ?";
        User user = null;
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User();
                String username = rs.getString("username");
                String password = rs.getString("password");
                String mobile = rs.getNString("mobile");
                String email = rs.getString("email");
                String address = rs.getString("address");
                user.setId(id);
                user.setUsername(username);
                user.setAddress(address);
                user.setEmail(email);
                user.setPassword(password);
                user.setMobile(mobile);
            }
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(rs);
            DatabaseUtil.getInstance().closeObject(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
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
