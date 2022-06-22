package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        // 이 컨트롤러에서 발생한 에러는 이 메소드 호출됨
        // ExceptionResolver가 호출해줌
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        // 예외를 메서드 파라미터에 넣어주면 어노테이션 옆에 선언안해도 ok
        log.error("[exceptionHandler]", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        // RuntimeException의 부모가 Exception이니까 이게 호출
        log.error("[exceptionHandler]", e);
        return new ErrorResult("EX", "내부 오류");
    }
}
