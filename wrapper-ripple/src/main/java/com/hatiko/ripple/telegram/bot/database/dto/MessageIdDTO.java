package com.hatiko.ripple.telegram.bot.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageIdDTO {
	
	private Long cahtId;
	private Integer lastDeleted;
	private Integer lastSent;
}
