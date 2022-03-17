package com.example.Login.DAO;

import com.example.Login.entity.AppRole;
import com.example.Login.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AppRoleDAO {

    @Autowired
    private EntityManager entityManager;

    public List<AppRole> getRoleNames(Long userId){
        try {
            String sql = "select ur.appRole.roleName from" + UserRole.class.getName() + " ur " +
                    "where ur.appUser.userId = :userId";
            Query query = entityManager.createQuery(sql, String.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        }catch (NoResultException e)
        {
            return null;
        }


    }
}
