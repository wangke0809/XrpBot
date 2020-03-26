package com.hatiko.ripple.database.rest.controller.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hatiko.ripple.database.rest.dto.response.StatusDTO;
import com.hatiko.ripple.database.service.XrpDatabaseOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("db/registry")
public class CheckRegistryStatusController {

	private final XrpDatabaseOperator userDataBaseOperator;
	
	@GetMapping
	public ResponseEntity<StatusDTO> checkRegistryStatus(@Valid @NotNull @RequestParam("username") String username){
		
		log.info("Check registry status for username");
		
		Boolean status = userDataBaseOperator.checkRegistryStatus(username);

		log.info("Response status from check registry status : {}", status);
		
		return ResponseEntity.ok(StatusDTO.builder().status(status).build());
	}
}
