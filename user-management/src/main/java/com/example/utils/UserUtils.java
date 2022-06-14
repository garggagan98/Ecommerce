package com.example.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.example.config.TwilioConfig;
import com.example.entity.User;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Component
@EnableAsync
public class UserUtils {

	private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

	@Autowired
	@Qualifier("gmail")
	private JavaMailSender mailSender;

	@Autowired
	private TwilioConfig config;

	@Async
	public void sendVerificationEmail(User user, String siteURL) {
		// send mail
		String to = user.getEmail();
		String from = "EcommerceWebsite@gmail.com";
		String subject = "Please verify your registration";

		MimeMessagePreparator preparator = mimeMessage -> {
			String content = "<!doctypehtml><meta charset=utf-8><meta content=\"ie=edge\"http-equiv=x-ua-compatible><title>Email Confirmation</title><meta content=\"width=device-width,initial-scale=1\"name=viewport><style>@media screen{@font-face{font-family:'Source Sans Pro';font-style:normal;font-weight:400;src:local('Source Sans Pro Regular'),local('SourceSansPro-Regular'),url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff')}@font-face{font-family:'Source Sans Pro';font-style:normal;font-weight:700;src:local('Source Sans Pro Bold'),local('SourceSansPro-Bold'),url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff')}}a,body,table,td{-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}table,td{mso-table-rspace:0;mso-table-lspace:0}img{-ms-interpolation-mode:bicubic}a[x-apple-data-detectors]{font-family:inherit!important;font-size:inherit!important;font-weight:inherit!important;line-height:inherit!important;color:inherit!important;text-decoration:none!important}div[style*=\"margin: 16px 0;\"]{margin:0!important}body{width:100%!important;height:100%!important;padding:0!important;margin:0!important}table{border-collapse:collapse!important}a{color:#1a82e2}img{height:auto;line-height:100%;text-decoration:none;border:0;outline:0}</style><body style=background-color:#e9ecef><div class=preheader style=display:none;max-width:0;max-height:0;overflow:hidden;font-size:1px;line-height:1px;color:#fff;opacity:0>A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.</div><table border=0 cellpadding=0 cellspacing=0 width=100%><tr><td align=center bgcolor=#e9ecef><!--[if (gte mso 9)|(IE)]><table border=0 cellpadding=0 cellspacing=0 width=600 align=center><tr><td align=center valign=top width=600><![endif]--><table border=0 cellpadding=0 cellspacing=0 width=100% style=max-width:600px><tr><td align=left bgcolor=#ffffff style=\"padding:36px 24px 0;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;border-top:3px solid #d4dadf\"><h1 style=margin:0;font-size:32px;font-weight:700;letter-spacing:-1px;line-height:48px>Confirm Your Email Address</h1></table><!--[if (gte mso 9)|(IE)]><![endif]--><tr><td align=center bgcolor=#e9ecef><!--[if (gte mso 9)|(IE)]><table border=0 cellpadding=0 cellspacing=0 width=600 align=center><tr><td align=center valign=top width=600><![endif]--><table border=0 cellpadding=0 cellspacing=0 width=100% style=max-width:600px><tr><td align=left bgcolor=#ffffff style=\"padding:24px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;line-height:24px\"><p>Dear [[name]],<p style=margin:0>Tap the button below to confirm your email address. If you didn't create an account with us, you can safely delete this email.<tr><td align=left bgcolor=#ffffff><table border=0 cellpadding=0 cellspacing=0 width=100%><tr><td align=center bgcolor=#ffffff style=padding:12px><table border=0 cellpadding=0 cellspacing=0><tr><td align=center bgcolor=#1a82e2 style=border-radius:6px><a href=[[URL]] style=\"display:inline-block;padding:16px 36px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;color:#fff;text-decoration:none;border-radius:6px\"target=_blank>Verify</a></table></table><tr><td align=left bgcolor=#ffffff style=\"padding:24px;font-family:'Source Sans Pro',Helvetica,Arial,sans-serif;font-size:16px;line-height:24px;border-bottom:3px solid #d4dadf\"><p style=margin:0>Cheers,<br>Ecommerce Management Team </table><!--[if (gte mso 9)|(IE)]><![endif]--></table>";
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setFrom(from, "Ecommerce Team");
			message.setSubject(subject);
			content = content.replace("[[name]]", user.getEmail());
			String verifyURL = siteURL + "/user/verify?code=" + user.getVerificationCode();
			content = content.replace("[[URL]]", verifyURL);
			message.setText(content, true);
		};
		mailSender.send(preparator);
		logger.info("Email sent successfully To {}, with Subject {}", to, subject);
	}

	@Async
	public void sendVerificationSMS(User user, String messageBody) {
		// send SMS
		PhoneNumber from = new PhoneNumber(config.getTrialNumber());
		PhoneNumber to = new PhoneNumber(user.getPhoneNumber());
		MessageCreator creator = Message.creator(to, from, messageBody);
		creator.create();
	}

	public static String getSiteURL(HttpServletRequest request) {
		return request.getRequestURL().toString().replace(request.getServletPath(), "");
	}
}
