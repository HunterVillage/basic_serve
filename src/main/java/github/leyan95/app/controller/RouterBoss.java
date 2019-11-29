package github.leyan95.app.controller;

import github.leyan95.app.annotation.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wujianchuan
 */
public class RouterBoss {
    private final Logger logger = LoggerFactory.getLogger(RouterBoss.class);
    private final static Map<String, RouterInfo> ROUTER_COMPANY = new ConcurrentHashMap<>(16);
    private final static RouterBoss INSTANCE = new RouterBoss();

    private RouterBoss() {
    }

    public static RouterBoss getInstance() {
        return INSTANCE;
    }

    public void register(Router routerAnnotation, String parentPath) {

        String[] subPaths = routerAnnotation.id();
        for (String subPath : subPaths) {
            String routerPath = parentPath + subPath;
            RouterInfo routerInfo = RouterInfo.newInstance()
                    .setPath(routerPath)
                    .setName(routerAnnotation.name())
                    .setDescription(routerAnnotation.description())
                    .setMethods(Arrays.asList(routerAnnotation.method()))
                    .setAuthIds(Arrays.asList(routerAnnotation.auth()));
            ROUTER_COMPANY.putIfAbsent(routerPath, routerInfo);
            logger.info("The router of \"{}\" was install.", routerPath);
        }
    }

    public RouterInfo getRouterInfo(String servletPath) {
        return ROUTER_COMPANY.get(servletPath);
    }
}
