package com.security.expences.repository;

import com.security.expences.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
}
