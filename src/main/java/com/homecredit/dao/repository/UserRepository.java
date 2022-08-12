package com.homecredit.dao.repository;

import com.homecredit.dao.model.User;
import com.homecredit.dao.model.view.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from v_user_view", nativeQuery = true)
    List<UserView> findAllWithRoles();

    List<User> findByRolesTitle(String title);
}
