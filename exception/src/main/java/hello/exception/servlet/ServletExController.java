package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외발생");
        // Exception 의 경우 WAS는 서버 내부에서 처리할 수 없는 오류가 발생한 것으로 생각해서 HTTP 상태 코드 500을 반환함.
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        // sendError : 서블릿 컨테이너에게 오류가 발생했다는 점을 전달
        response.sendError(404, "404 오류!");
        // 이 메시지는 디폴트로 숨겨져있음. 나중에 자세히..
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
