package com.hatiko.ripple.wrapper.integration.blockchain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RippleTransactionsRequest {

	@JsonProperty("method")
	private String method;
	
	@JsonProperty("params")
	private List<Param> params;
	
	@Getter
	@Setter
	@Builder
	public static class Param{
		
		@JsonProperty("account")
		private String account;
		
		@JsonProperty("ledger_index_min")
		private Long ledgerIndexMin;
	}
}
