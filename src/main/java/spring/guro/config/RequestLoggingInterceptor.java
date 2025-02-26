// package spring.guro.config;

// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.util.ContentCachingRequestWrapper;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;

// @Component
// @Slf4j
// public class RequestLoggingInterceptor implements HandlerInterceptor{
//     @Override
//     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//         // ContentCachingRequestWrapper를 사용하여 request body를 여러번 읽을 수 있도록 함.
//         ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);

//         String httpMethod = requestWrapper.getMethod();
//         String uri = requestWrapper.getRequestURI();
//         String queryString = requestWrapper.getQueryString();
//         String requestInfo = String.format("%s %s%s", httpMethod, uri, queryString != null ? "?" + queryString : "");
//         log.info("Request: {}", requestInfo);

//          // Request Body 로깅 (선택 사항, 큰 요청 body는 성능에 영향)
//         byte[] requestBody = requestWrapper.getContentAsByteArray();
//         if (requestBody.length > 0) {
//             try {
//                 String requestBodyString = new String(requestBody, requestWrapper.getCharacterEncoding());
//                  // 민감 정보 마스킹 필요하면 여기서 처리
//                 log.info("Request Body: {}", requestBodyString);
//             } catch (Exception e) {
//                 log.error("Error reading request body",e); //예외 처리 추가
//             }
//         }
//         return true; // true를 반환해야 다음 인터셉터 또는 컨트롤러로 요청이 전달됨
//     }
// }
