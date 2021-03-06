package com.hatiko.ripple.telegram.bot.dto.telegram;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class TelegramChat {

	private Long id;
	private LocalDateTime creationDate;
	private Boolean userChat;
	private Boolean groupChat;
	private Boolean channelChat;
	private Boolean superGroupChat;
	private TelegramUser user;
}
