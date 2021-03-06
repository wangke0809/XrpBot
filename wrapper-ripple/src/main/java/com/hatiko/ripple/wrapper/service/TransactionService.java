package com.hatiko.ripple.wrapper.service;

import java.util.List;

import com.hatiko.ripple.wrapper.integration.cryptopayment.dto.LastProcessedTransactionData;
import com.hatiko.ripple.wrapper.web.model.TransactionResponse;

/**
 * The interface provides 3 methods to work with CryptoPament MS
 * Its purpose is sending unprocessed transactions to CryptoPayment MS
 */
public interface TransactionService {

	/**
	 * Gets unprocessed transactions that appeared after specialized variable
	 * @param last processed sequence
	 * @return Transaction Dtos that appear to be unprocessed
	 */
	List<TransactionResponse> getNewBlockchainTransactionsToProcess(Long lastSequence);
	
	/**
	 * Gets the last processed sequence from CryptoPayment MS
	 * @return
	 */
	LastProcessedTransactionData getLastProcessedTransactionData();

	/**
	 * Sends unprocessed transactions to CryptoPayment MS
	 * @param Trnsaction Dtos that should be processed by CryptoPayment MS
	 */
	void sendNewTransactionsList(List<TransactionResponse> transactions);

	List<TransactionResponse> getLastTransactions(String walletAddress, Long numOfTransactions);

}