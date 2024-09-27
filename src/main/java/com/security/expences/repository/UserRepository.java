package com.security.expences.repository;

import com.security.expences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByUsername(String username);

    List<User> findAllByBandNameLikeOrBandDescriptionLike(String t1, String t2);
}
