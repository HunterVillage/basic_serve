package github.leyan95.app.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.leyan95.app.controller.ResponseBody;
import io.jsonwebtoken.Claims;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static github.leyan95.app.filter.TokenUtil.CLAIM_KEY_AUTH;
import static github.leyan95.app.filter.TokenUtil.CLAIM_KEY_AVATAR;

/**
 * @author wujianchuan
 */
@Component
@Order
public class TokenFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);

    private final static String APP_FILTER_PATH = "/app/*";
    private final static String APP_FILTER_EXCLUDE_PATH = "/app/login";
    private final static PathMatcher PATH_MATCHER = new PathMatcher();
    private String tokenHead;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        ResourceBundle resourceApplication = ResourceBundle.getBundle("application", Locale.US);
        tokenHead = resourceApplication.getString("app.token.head");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        String servletPath = ((RequestFacade) request).getServletPath();
        if (PATH_MATCHER.matching(APP_FILTER_PATH, servletPath) && !PATH_MATCHER.matching(APP_FILTER_EXCLUDE_PATH, servletPath)) {
            String token = ((RequestFacade) request).getHeader(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith(tokenHead)) {
                reLogin(response, "Missing or invalid Authorization header");
                return;
            }
            LOGGER.info("Token is {}", token);
            Claims claims = tokenUtil.getClaimsFromToken(token.replace(tokenHead, ""));
            if (claims == null) {
                reLogin(response, "Login status timed out");
                return;
            }
            if (tokenUtil.shouldRefresh(claims)) {
                refreshToken(response, claims);
                return;
            }
            String avatar = (String) claims.get(CLAIM_KEY_AVATAR);
            String[] authIds = (String[]) claims.get(CLAIM_KEY_AUTH);
            LOGGER.info("The avatar is {}", avatar);
            if (authIds != null) {
                LOGGER.info("The auth id is {}", String.join("", authIds));
            }
            if (avatar != null) {
                //TODO authority
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void refreshToken(ServletResponse response, Claims claims) throws IOException {
        LOGGER.info("The token should be refresh.");
        ResponseBody responseBody = ResponseBody.success().token(tokenUtil.refreshToken(claims)).resend();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    private void reLogin(ServletResponse response, String message) throws IOException {
        LOGGER.info(message);
        ResponseBody responseBody = ResponseBody.warning().reLogin();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
