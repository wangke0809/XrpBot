package com.hatiko.ripple.telegram.bot.database.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hatiko.ripple.telegram.bot.database.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findOneByUsername(String username);
	List<UserEntity> findAllByUsername(String username);
}
