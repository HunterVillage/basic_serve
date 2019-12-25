package github.leyan95.basic.engine;

import github.leyan95.basic.auth.AuthContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wujianchuan
 */
@Configuration
public class BasicHttpLaunch implements ServletContextListener {
    private final AuthContext authContext;

    public BasicHttpLaunch(AuthContext authContext) {
        this.authContext = authContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.authContext.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        this.authContext.destroy();
    }
}
