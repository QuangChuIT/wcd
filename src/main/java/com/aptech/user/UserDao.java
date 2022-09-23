package com.aptech.user;

import com.aptech.common.GenericDao;
import com.aptech.utils.DatabaseUtil;
import com.aptech.utils.ResultToObject;
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
            users = ResultToObject.getInstance().getData(resultSet, User.class);
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
            List<User> users = ResultToObject.getInstance().getData(rs, User.class);
            if (users.size() > 0) {
                user = users.get(0);
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
        String sql = "update user set password = ?, email = ?, mobile = ?, address = ? where id = ?";
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getMobile());
            statement.setString(4, obj.getAddress());
            statement.setLong(5, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {

    }

    public User findUser(String username, String password) {
        return new User("quangcv", "123abc@A", "085467025", "quangcv@vnpt.vn", "Vinh Phuc");
    }
}
