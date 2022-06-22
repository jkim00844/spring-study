package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //  preHandle 에서 지정한 값을 postHandle, afterCompletion 에서 함께 사용하려면 어딘가에 담아두어야 한ek.
        //  LogInterceptor 도 싱글톤 처럼 사용되기 때문에 맴버변수를 사용하면 위험
        //  따라서 request 에 담아둠.
        request.setAttribute(LOG_ID, uuid);

        // @RequestMapping: HandlerMethod
        // 정적 리소스: ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }

        log.info("Interceptor | REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        // REQUEST [33311257-d649-4b66-aff7-4155ebda6b4f][/items][hello.login.web.item.ItemController#items(Model)]
        return true; //false 진행X
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Interceptor | postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        log.info("Interceptor | RESPONSE [{}][{}]", logId, requestURI);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
