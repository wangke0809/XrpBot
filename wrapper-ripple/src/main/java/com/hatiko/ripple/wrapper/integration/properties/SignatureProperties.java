package com.hatiko.ripple.wrapper.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("signature")
@Component
public class SignatureProperties {
	public String fileForSigning;
	public String pathToNode;
	public Long timeout;

}
