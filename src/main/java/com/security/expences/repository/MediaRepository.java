package com.security.expences.repository;

import com.security.expences.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    List<Media> findAllByUserId(int id);
}
