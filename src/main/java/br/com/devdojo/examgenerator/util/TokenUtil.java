package br.com.devdojo.examgenerator.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import br.com.devdojo.examgenerator.custom.CustomURLEncoder;

public class TokenUtil {

	public String getTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return "";
		}
		List<Cookie> cookieList = Arrays.asList(request.getCookies());
		return getCookieByKey(cookieList, "token");
	}

	public boolean isExpirationTimeFromCookieValid(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return false;
		}
		List<Cookie> cookieList = Arrays.asList(request.getCookies());
		String expirationTime = getCookieByKey(cookieList, "expirationTime");
		return validateIfTimeNowIsBeforeTokenExpires(expirationTime);
	}

	private String getCookieByKey(List<Cookie> cookieList, String key) {
		return cookieList.stream().filter(cookie -> cookie.getName().equals(key)).map(Cookie::getValue).findFirst()
				.orElse("");
	}

	private boolean validateIfTimeNowIsBeforeTokenExpires(String expirationTime) {
		if (expirationTime.isEmpty()) {
			return false;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
				.withZone(ZoneId.of("UTC"));
		LocalDateTime tokenExpirationTime = LocalDateTime.parse(CustomURLEncoder.decodeUTF8(expirationTime), formatter);
		return LocalDateTime.now(ZoneId.of("UTC")).isBefore(tokenExpirationTime);
	}

}
