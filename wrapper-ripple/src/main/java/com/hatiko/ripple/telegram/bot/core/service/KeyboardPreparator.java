package com.hatiko.ripple.telegram.bot.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.hatiko.ripple.telegram.bot.core.properties.ActionProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeyboardPreparator {

	private final ActionProperties actionProperties;
	private Map<String, KeyboardButton> buttons;

	@Getter
	private ReplyKeyboardMarkup startKeyboard;
	@Getter
	private ReplyKeyboardMarkup mainKeyboard;
	@Getter
	private ReplyKeyboardMarkup logInKeyboard;

	@PostConstruct
	public void prepareKeyboards() {
		createButtons();
		createStartKeyboard();
		createMainKeyboard();
		createLogInKeyboard();
	}

	private void createButtons() {

		buttons = new HashMap<String, KeyboardButton>();

		KeyboardButton helloButton = new KeyboardButton(actionProperties.getCommand().getHello());
		helloButton.setText(actionProperties.getButton().getHello());
		buttons.put("hello", helloButton);

		KeyboardButton helpButton = new KeyboardButton(actionProperties.getCommand().getHelp());
		helpButton.setText(actionProperties.getButton().getHelp());
		buttons.put("help", helpButton);

		KeyboardButton nextButton = new KeyboardButton(actionProperties.getCommand().getNext());
		nextButton.setText(actionProperties.getButton().getNext());
		buttons.put("next", nextButton);

		KeyboardButton logInButton = new KeyboardButton(actionProperties.getCommand().getLogIn());
		helpButton.setText(actionProperties.getButton().getLogIn());
		buttons.put("logIn", logInButton);

		KeyboardButton getBalanceButton = new KeyboardButton(actionProperties.getCommand().getGetBalance());
		getBalanceButton.setText(actionProperties.getButton().getGetBalance());
		buttons.put("getBalance", getBalanceButton);

		KeyboardButton generateMemoButton = new KeyboardButton(actionProperties.getCommand().getGenerateMemo());
		generateMemoButton.setText(actionProperties.getButton().getGenerateMemo());
		buttons.put("generateBalance", generateMemoButton);

		KeyboardButton getTransactionInfoButton = new KeyboardButton(
				actionProperties.getCommand().getGetTransactionInfo());
		getTransactionInfoButton.setText(actionProperties.getButton().getGetTransactionInfo());
		buttons.put("getTransactionInfo", getTransactionInfoButton);

		KeyboardButton withdrawButton = new KeyboardButton(actionProperties.getCommand().getWithdraw());
		withdrawButton.setText(actionProperties.getButton().getWithdraw());
		buttons.put("withdraw", withdrawButton);
	}

	private void createStartKeyboard() {
		
		startKeyboard = configKeyboard(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		
		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow firstRow = new KeyboardRow();
		firstRow.add(buttons.get("logIn"));
		
		KeyboardRow secondRow = new KeyboardRow();
		secondRow.add(buttons.get("next"));
		
		keyboard.add(firstRow);
		keyboard.add(secondRow);
		
		startKeyboard.setKeyboard(keyboard);
	}

	private void createMainKeyboard() {

	}

	private void createLogInKeyboard() {

	}

	private ReplyKeyboardMarkup configKeyboard(Boolean selective, Boolean resizeKeyboard, Boolean oneTimeKeyboard) {

		ReplyKeyboardMarkup configedKeyboard = new ReplyKeyboardMarkup();

		configedKeyboard.setSelective(selective);
		configedKeyboard.setResizeKeyboard(resizeKeyboard);
		configedKeyboard.setOneTimeKeyboard(oneTimeKeyboard);

		return configedKeyboard;
	}
}