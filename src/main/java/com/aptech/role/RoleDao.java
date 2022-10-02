package com.aptech.role;

import com.aptech.common.JpaCrudDao;
import com.aptech.utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RoleDao extends JpaCrudDao<Role, Long> {

    private static RoleDao instance;

    private RoleDao() {
        super(Role.class);
    }

    public static RoleDao getInstance() {
        if (instance == null) {
            instance = new RoleDao();
        }
        return instance;
    }

    @Override
    public List<Role> getAll() {
        EntityManager em = JPAUtil.getFactory().createEntityManager();
        List<Role> roles = new ArrayList<>();
        try {
            Query query = em.createQuery("select r from Role r", Role.class);
            roles = query.getResultList();
            if (roles == null) {
                roles = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return roles;
    }

    public RoleDto convertToDTO(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(roleDto.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());
        roleDto.setDetail(role.getDetail());
        return roleDto;
    }
}
