package com.hatiko.ripple.telegram.bot.core.handler.navigation;

import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.core.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.core.dto.TelegramUpdate;
import com.hatiko.ripple.telegram.bot.core.handler.TelegramMessageHandler;
import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.core.service.KeyboardPreparator;
import com.hatiko.ripple.telegram.bot.core.service.ResponseMessageOperator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartMessageHandler implements TelegramMessageHandler {

	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final ResponseMessageOperator responseMessageOperator;

	@Override
	public void handle(TelegramUpdate telegramUpdate) {

		if (!telegramUpdate.getMessage().getText().startsWith(actionProperties.getButtonOperation().getStart())) {
			return;
		}

//		Integer messageId = xrpLongPollingBot.sendMessage(telegramUpdate.getMessage().getChat().getId(), text,
//				keyboardPreparator.getStartKeyboard());

		Integer messageId = responseMessageOperator.responseStart(telegramUpdate.getMessage().getFrom().getFirstName(),
				telegramUpdate.getMessage().getChat().getId());
	}
}
