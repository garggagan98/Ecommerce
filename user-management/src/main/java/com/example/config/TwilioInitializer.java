package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioInitializer {
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);
	private final TwilioConfig twilioConfiguration;

	@Autowired
	public TwilioInitializer(TwilioConfig twilioConfig) {
		this.twilioConfiguration = twilioConfig;
		Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
		LOGGER.info("Twilio Initialized ...with account sid {}", twilioConfiguration.getAccountSid());
	}
}
