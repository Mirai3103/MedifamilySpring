package sgu.j2ee.medifamily.exceptions;

import org.springframework.http.HttpStatus;

public class RequireLoginException extends BaseException {
	public RequireLoginException() {
		this("Bạn cần đăng nhập để thực hiện hành động này");
	}

	public RequireLoginException(String message) {
		super(message, HttpStatus.UNAUTHORIZED, "REQUIRE_LOGIN");
	}
}
