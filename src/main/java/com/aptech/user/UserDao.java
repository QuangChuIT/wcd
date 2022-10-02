package com.aptech.user;

import com.aptech.common.JpaCrudDao;
import com.aptech.exception.UserException;
import com.aptech.role.Role;
import com.aptech.role.RoleDao;
import com.aptech.role.RoleDto;
import com.aptech.user.payload.UserDto;
import com.aptech.utils.AESUtil;
import com.aptech.utils.JPAUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDao extends JpaCrudDao<User, Long> {

    private static UserDao instance;
    private final static Logger LOGGER = LogManager.getLogger(UserDao.class);

    private UserDao() {
        super(User.class);
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public List<User> getAll() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            Query query = em.createQuery("select u from User u order by u.createdDate desc", User.class);
            users = query.getResultList();
            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    public List<UserDto> getUsers() {
        List<User> users = this.getAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDto convertToDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setModifiedDate(user.getModifiedDate());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setLockoutDate(user.getLockoutDate());
        userDto.setLastLoginDate(user.getLastLoginDate());
        userDto.setPhoto(user.getPhoto());
        userDto.setLastFailedLoginDate(user.getLastFailedLoginDate());
        List<RoleDto> roleDtos = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(role -> {
                roleDtos.add(RoleDao.getInstance().convertToDTO(role));
            });
        }
        userDto.setRoles(roleDtos);
        return userDto;
    }

    public User login(String username, String password) {
        User user = null;
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        try {
            Query query = em.createQuery("select u from User u where u.username =:username and u.password=:password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", AESUtil.encrypt(password));
            List<User> users = query.getResultList();
            if (users != null && users.size() > 0) {
                user = users.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("Error login", e);
        } finally {
            em.close();
        }
        return user;
    }

    public void deleteUsers(List<Long> ids) {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("delete from User where id in (:ids)");
            query.setParameter("ids", ids);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.error("Error login", e);
            throw new UserException("Delete users error " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
