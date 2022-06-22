package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {

    // @ResponseStatus가 있으면
    // ResponseStatusExceptionResolver(HTTP 상태 코드를 지정)가 동작함.
    // sendError(400) 를 호출

}
