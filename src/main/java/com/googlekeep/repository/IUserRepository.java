package com.googlekeep.repository;

import com.googlekeep.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserDetails, Integer> {
    Optional<UserDetails> findByEmailID(String emilID);
}
