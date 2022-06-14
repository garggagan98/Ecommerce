package com.example.error;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Represents Error Message Source configuration to fetch error message from
 * resource bundle.
 */
@Component
public class ErrorMessageSource {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:error-messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	protected MessageSource source = messageSource();

	public String getMessage(String errorCode) {
		return getMessage(errorCode, null);
	}

	public String getMessage(String errorCode, Object[] args) {
		return source.getMessage("error." + errorCode + ".message", args, LocaleContextHolder.getLocale());
	}
}
