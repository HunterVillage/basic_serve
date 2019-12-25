package github.leyan95.basic.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.leyan95.basic.controller.ResponseBody;
import github.leyan95.basic.controller.RouterBoss;
import github.leyan95.basic.controller.RouterInfo;
import io.jsonwebtoken.Claims;
import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static github.leyan95.basic.filter.TokenUtil.CLAIM_KEY_AUTH;
import static github.leyan95.basic.filter.TokenUtil.CLAIM_KEY_AVATAR;

/**
 * @author wujianchuan
 */
@Configuration
public class AuthFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    private final static String APP_FILTER_PATH = "/basic/*";
    private final static String APP_FILTER_EXCLUDE_PATH = "/basic/login";
    private final static PathMatcher PATH_MATCHER = new PathMatcher();
    private String tokenHead;

    private final TokenUtil tokenUtil;

    public AuthFilter(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("auth filter init");
        tokenHead = tokenUtil.getTokenHead();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String servletPath = ((RequestFacade) request).getServletPath();
        if (PATH_MATCHER.matching(APP_FILTER_PATH, servletPath) && !PATH_MATCHER.matching(APP_FILTER_EXCLUDE_PATH, servletPath)) {
            String token = ((RequestFacade) request).getHeader(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith(tokenHead)) {
                reLogin(response, "Missing or invalid Authorization header");
                return;
            }
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
            String ownAuthIdsStr = (String) claims.get(CLAIM_KEY_AUTH);
            if (avatar == null) {
                reLogin(response, "Login user not found");
                return;
            }
            if (ownAuthIdsStr == null) {
                refuseWithMessage(response, "没有权限", "您没有访问权限");
                return;
            }
            String path = ((RequestFacade) request).getServletPath();
            RouterInfo routerInfo = RouterBoss.getInstance().getRouterInfo(path);
//            if (routerInfo == null) {
//                refuseWithMessage(response, "找不到地址", "对不起您访问的地址无效");
//                return;
//            }
            if (routerInfo != null) {
                List<String> ownAuthIds = Arrays.asList(ownAuthIdsStr.split(","));
                if (!routerInfo.authConfirm(ownAuthIds)) {
                    refuseWithMessage(response, "没有权限", "您没有访问权限");
                    return;
                }
            }
            request.setAttribute("sysLoginAvatar", avatar);
            chain.doFilter(request, response);
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

    private void refuseWithMessage(ServletResponse response, String title, String message) throws IOException {
        LOGGER.info(message);
        ResponseBody responseBody = ResponseBody.error().title(title).message(message);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }

    @Override
    public void destroy() {
    }
}
