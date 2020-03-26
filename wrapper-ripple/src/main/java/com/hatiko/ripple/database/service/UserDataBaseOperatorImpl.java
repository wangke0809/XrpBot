package com.hatiko.ripple.database.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatiko.ripple.database.converter.UserConverter;
import com.hatiko.ripple.database.dto.UserDTO;
import com.hatiko.ripple.database.model.UserEntity;
import com.hatiko.ripple.database.repo.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataBaseOperatorImpl implements UserDataBaseOperator {

	private final UserRepo userRepo;
	private final ObjectMapper objectMapper;

	@Override
	public List<UserDTO> getAllUsers() {

		log.info("Getting all userDTO from db");

		return userRepo.findAll().stream().map(e -> UserConverter.toUserDTO(e)).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserByUsername(String username) {

		log.info("Getting userEntity by username | username : {}", username);

		UserEntity entity = userRepo.findOneByUsername(username);

		try {
			log.info("Response from db : {}", objectMapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		log.info("Transform of userEntity DTO userDTO");

		UserEntity userEntity = Optional.ofNullable(entity).orElseGet(UserEntity::new);

		return UserConverter.toUserDTO(userEntity);
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {

		UserEntity userEntity = UserConverter.toUserEntity(userDTO);

		try {
			log.info("Saving user to db : {}", objectMapper.writeValueAsString(userEntity));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		UserEntity entityResponse = userRepo.save(userEntity);

		try {
			log.info("Response entity from db after saveing : {}", objectMapper.writeValueAsString(entityResponse));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		UserDTO response = UserConverter.toUserDTO(entityResponse);

		return response;
	}

	@Override
	public Boolean checkLogIn(String username, String password) {

		if (Optional.ofNullable(username).isEmpty() || Optional.ofNullable(password).isEmpty()) {
			return Boolean.FALSE;
		}

		UserEntity userEntity = userRepo.findOneByUsername(username);

		log.info("Check for log in | username : {}, password : {}", username, password);

		Boolean status = Optional.ofNullable(userEntity).filter(e -> e.getPassword().equals(password)).isPresent();
		log.info("Login status : {}", status);

		return status;
	}

	@Override
	public Boolean checkRegistryStatus(String username) {

		List<UserEntity> users = userRepo.findAllByUsername(username);

		log.info("Check for user being registered by username = {}", username);

		users = users.stream().filter(e -> e.getUsername().equals(username)).collect(Collectors.toList());

		Boolean response = users.size() > 0;

		log.info("Registry state : {}", response);

		return response;
	}

	@Override
	public Boolean deleteUserByUsername(String username, String password) {

		if (!checkLogIn(username, password)) {
			return Boolean.FALSE;
		}

		UserEntity userToDelete = Optional.ofNullable(userRepo.findOneByUsername(username)).orElseGet(UserEntity::new);

		log.info("Checking if the user exists");

		if (userToDelete.getUsername() == null) {
			return Boolean.FALSE;
		}

		log.info("Deleting user with username : {}", username);

		userRepo.delete(userToDelete);

		return Boolean.TRUE;
	}
}
