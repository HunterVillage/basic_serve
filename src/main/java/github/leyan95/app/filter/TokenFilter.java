package github.leyan95.app.filter;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.annotation.Order;
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

/**
 * @author wujianchuan
 */
@Component
@Order
public class TokenFilter implements Filter {

    private final static String APP_FILTER_PATH = "/app/*";
    private final static String APP_FILTER_EXCLUDE_PATH = "/app/login";
    private final static PathMatcher PATH_MATCHER = new PathMatcher();
    private String tokenSecret;
    private String tokenHead;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        ResourceBundle resourceApplication = ResourceBundle.getBundle("application", Locale.US);
        tokenSecret = resourceApplication.getString("app.token.secret");
        tokenHead = resourceApplication.getString("app.token.head");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        String servletPath = ((RequestFacade) request).getServletPath();
        if (PATH_MATCHER.matching(APP_FILTER_PATH, servletPath) && !PATH_MATCHER.matching(APP_FILTER_EXCLUDE_PATH, servletPath)) {
            System.out.println(String.format("解析token %s", ((RequestFacade) request).getHeader("token")));
            System.out.println(tokenSecret);
            System.out.println(tokenHead);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
