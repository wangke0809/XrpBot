package com.hatiko.ripple.telegram.bot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hatiko.ripple.telegram.bot.XrpLongPollingBot;
import com.hatiko.ripple.telegram.bot.properties.ActionProperties;
import com.hatiko.ripple.telegram.bot.properties.ResponseMessageProperties;
import com.hatiko.ripple.wrapper.web.model.BalanceResponse;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseMessageOperatorImpl implements ResponseMessageOperator {

	private final XrpLongPollingBot xrpLongPollingBot;
	private final ActionProperties actionProperties;
	private final KeyboardPreparator keyboardPreparator;
	private final ResponseMessageProperties messageProperties;
	private final SessionService sessionService;

	public ResponseMessageOperatorImpl(@Lazy XrpLongPollingBot xrpLongPollingBot, ActionProperties actionProperties,
			KeyboardPreparator keyboardPreparator, ResponseMessageProperties messageProperties,
			@Lazy SessionService sessionService) {
		this.xrpLongPollingBot = xrpLongPollingBot;
		this.actionProperties = actionProperties;
		this.keyboardPreparator = keyboardPreparator;
		this.messageProperties = messageProperties;
		this.sessionService = sessionService;
	}

	@Override
	public Integer responseStart(String firstName, Long chatId) {

		log.info("Responsing Start operation | chatId : {}, firstName : {}", chatId, firstName);
		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getStart(), firstName),
				keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseHello(String firstName, Long chatId) {

		log.info("Responsing Hello operation | firstName : {}, chatId : {}", firstName, chatId);
		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getHello(), firstName),
				keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseMain(Long chatId) {

		Boolean logInStatus = sessionService.checkSessionExist(chatId);
		log.info("Responsing Main operation | chatId : {}, logInStatus : {}", chatId, logInStatus);
		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getMain(),
				keyboardPreparator.getMainKeyboard(logInStatus));
	}

	@Override
	public Integer responseNext(Long chatId) {

		log.info("Responsing Next operation | chatId : {}", chatId);
		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getNext(),
				keyboardPreparator.getMainKeyboard(Boolean.FALSE));
	}

	@Override
	public Integer responseHelp(Long chatId) {

		log.info("Responsing Help opeartiong | chatId : {}");
		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getHelp(), null);
	}

	@Override
	public Integer responseLogIn(Long chatId, Integer operationCounter, Boolean logInStatus) {

		log.info("Responsing LogIn operation | chatId : {}, logInStatus : {}", chatId, logInStatus);

		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getLogIn().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = messageProperties.getLogIn().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			if (logInStatus) {
				responseMessage = messageProperties.getLogIn().get(operationCounter);
			} else {
				responseMessage = messageProperties.getLogIn().get(operationCounter + 1);
			}
			return xrpLongPollingBot.sendMessage(chatId, responseMessage,
					keyboardPreparator.getMainKeyboard(logInStatus));
		}
		return responseErrorMessage(actionProperties.getMethodName().getLogIn(), chatId);
	}

	@Override
	public Integer responseLogOut(Long chatId) {

		log.info("Responsing LogOut operationg | chatId : {}", chatId);
		return xrpLongPollingBot.sendMessage(chatId, messageProperties.getLogOut(),
				keyboardPreparator.getStartKeyboard());
	}

	@Override
	public Integer responseRegister(Long chatId, Integer operationCounter, Boolean registerStatus) {

		log.info("Responsing Register operation | chatId : {}, registerStatus : {}", chatId, registerStatus);
		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getRegister().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = messageProperties.getRegister().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			responseMessage = messageProperties.getRegister().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(3)) {
			responseMessage = messageProperties.getRegister().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(4)) {
			if (registerStatus) {
				responseMessage = messageProperties.getRegister().get(operationCounter);
			} else {
				responseMessage = messageProperties.getRegister().get(operationCounter + 1);
			}
			return xrpLongPollingBot.sendMessage(chatId, responseMessage,
					keyboardPreparator.getMainKeyboard(registerStatus));
		}
		return responseErrorMessage(actionProperties.getMethodName().getLogIn(), chatId);
	}

	@Override
	public Integer responseGenerateMemo(String walletMemo, Long chatId) {

		Boolean logInStatus = sessionService.checkSessionExist(chatId);
		log.info("Responsing GenerateMemo operation | chatId : {}, walletMemo : {}, logInStatus : {}", chatId,
				walletMemo, logInStatus);
		return xrpLongPollingBot.sendMessage(chatId, walletMemo, keyboardPreparator.getMainKeyboard(logInStatus));
	}

	@Override
	public Integer responseGetBalance(Object responseObject, Long chatId, Integer operationCounter) {

		log.info("Responsing GetBalance operation | chatId : {}", chatId);
		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetBalance().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = String.format(messageProperties.getGetBalance().get(operationCounter),
					((BalanceResponse) responseObject).getAmount());

			Boolean logInStatus = sessionService.checkSessionExist(chatId);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage,
					keyboardPreparator.getMainKeyboard(logInStatus));
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetBalance(), chatId);
	}

	@Override
	public Integer responseGetLastTransactions(Object responseObject, Long chatId, Integer operationCounter) {

		log.info("Responsing GetLastTransaction operation | chatId : {}", chatId);
		String responseMessage = null;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetLastTransactions().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			responseMessage = messageProperties.getGetLastTransactions().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(2)) {
			List<TransactionResponse> transactions = (ArrayList<TransactionResponse>) responseObject;
			responseMessage = String.format(messageProperties.getGetLastTransactions().get(operationCounter),
					transactions.size());

			Boolean logInStatus = sessionService.checkSessionExist(chatId);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage,
					keyboardPreparator.getMainKeyboard(logInStatus));
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetLastTransactions(), chatId);
	}

	@Override
	public Integer responseWithdraw(Object responseObject, Long chatId, Integer operationCounter) {

		log.info("Responsing Withdraw operation | chatId : {}", chatId);
		
		if (operationCounter > -1 && operationCounter < 5) {
			return xrpLongPollingBot.sendMessage(chatId, messageProperties.getWithdraw().get(operationCounter), null);
		}
		return responseErrorMessage(actionProperties.getMethodName().getWithdraw(), chatId);
	}

	@Override
	public Integer responseGetTransactionInfo(Object responseObject, Long chatId, Integer operationCounter) {

		log.info("Responsing GetTranasctionInfo operation | chatId : {}", chatId);
		
		String responseMessage;
		if (operationCounter.equals(0)) {
			responseMessage = messageProperties.getGetTransactionInfo().get(operationCounter);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage, null);
		}
		if (operationCounter.equals(1)) {
			TransactionResponse response = (TransactionResponse) responseObject;

			responseMessage = String.format(messageProperties.getGetTransactionInfo().get(operationCounter),
					response.getTrxId(), response.getFrom(), response.getTo(), response.getMemo(),
					response.getAmount());

			Boolean logInStatus = sessionService.checkSessionExist(chatId);
			return xrpLongPollingBot.sendMessage(chatId, responseMessage,
					keyboardPreparator.getMainKeyboard(logInStatus));
		}
		return responseErrorMessage(actionProperties.getMethodName().getGetTransactionInfo(), chatId);
	}

	@Override
	public Integer responseErrorMessage(String operation, Long chatId) {

		log.info("Responsing Error operation | chatId : {}, operation : {}", chatId, operation);
		Boolean logInStatus = sessionService.checkSessionExist(chatId);
		return xrpLongPollingBot.sendMessage(chatId, String.format(messageProperties.getError(), operation),
				keyboardPreparator.getMainKeyboard(logInStatus));
	}

}
