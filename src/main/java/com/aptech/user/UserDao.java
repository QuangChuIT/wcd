package com.aptech.user;

import com.aptech.common.GenericDao;
import com.aptech.exception.UserException;
import com.aptech.utils.AESUtil;
import com.aptech.utils.DatabaseUtil;
import com.aptech.utils.ResultToObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserDao implements GenericDao<User> {
    private final static String GET_DATA = "select * from user order by created_date desc";
    private final static String GET_USER_DETAIL = "select * from user where id = ?";
    private final static String UPDATE_USER = "update user set name = ?, email = ?, mobile = ?, status = ?, photo = ?, modified_date = ? where id = ?";
    private final static String INSERT_USER = "insert into user(username, password, name, mobile, email, status) values (?,?,?,?,?,?)";
    private final static String DELETE_USER = "delete from user where id = ?";
    private final static String LOGIN = "select * from user where username = ? and password = ?";
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
            LOGGER.error("Error get users", e);
        }
        return users;
    }

    @Override
    public Optional<User> get(long id) {

        User user = null;
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(GET_USER_DETAIL);
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
            LOGGER.error("Get user error", e);
            throw new UserException("Get user error: " + e.getMessage());
        }

        return Optional.ofNullable(user);
    }

    @Override
    public void save(User obj) {
        Connection connection = DatabaseUtil.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, obj.getUsername());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getName());
            statement.setString(4, obj.getMobile());
            statement.setString(5, obj.getEmail());
            statement.setInt(6, obj.getStatus());
            statement.executeUpdate();
            connection.commit();
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(statement);
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            LOGGER.error("Error create user", e);
            throw new UserException("Create user error: " + e.getMessage());
        }
    }

    @Override
    public void update(User obj) {
        Connection connection = DatabaseUtil.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setString(3, obj.getMobile());
            statement.setInt(4, obj.getStatus());
            statement.setTimestamp(6, new Timestamp(new Date().getTime()));
            statement.setString(5, obj.getPhoto());
            statement.setLong(7, obj.getId());
            statement.executeUpdate();
            connection.commit();
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(statement);

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            LOGGER.error("Update user error", e);
            throw new UserException("Update user error: " + e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, id);
            statement.executeUpdate();
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(statement);
        } catch (Exception e) {
            LOGGER.error("Delete user error", e);
            throw new UserException("Delete user error: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        User user = null;
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(LOGIN);
            statement.setString(1, username);
            statement.setString(2, AESUtil.encrypt(password));
            ResultSet rs = statement.executeQuery();
            List<User> users = ResultToObject.getInstance().getData(rs, User.class);
            if (users.size() > 0) {
                user = users.get(0);
            }
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeConnection(connection);
            DatabaseUtil.getInstance().closeObject(statement);
        } catch (Exception e) {
            LOGGER.error("Error login", e);
        }
        return user;
    }
}
