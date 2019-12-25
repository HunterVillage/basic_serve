package github.leyan95.basic.auth;

import github.leyan95.basic.annotation.Bundle;
import github.leyan95.basic.annotation.Router;
import github.leyan95.basic.controller.AppBundle;
import github.leyan95.basic.controller.RouterBoss;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author wujianchuan
 */
@Component
public class AuthContext {

    private final List<AbstractAuthInstaller> installers;
    private final List<AppBundle> appControllers;

    private RouterBoss routerBoss = RouterBoss.getInstance();
    private AuthFactory authFactory = AuthFactory.getInstance();

    public AuthContext(List<AbstractAuthInstaller> installers, List<AppBundle> appControllers) {
        this.installers = installers;
        this.appControllers = appControllers;
    }

    public void init() {
        this.authInstall();
        this.controllerInstall();
        this.authFactory.persistAll();
    }

    private void authInstall() {
        this.installers.forEach(AbstractAuthInstaller::install);
    }

    private void controllerInstall() {
        appControllers.parallelStream().forEach(appController -> {
            Class clazz = appController.getClass();
            Bundle bundle = (Bundle) clazz.getAnnotation(Bundle.class);
            Arrays.asList(bundle.bundlePath())
                    .parallelStream()
                    .forEach(parentPath -> Arrays.asList(clazz.getDeclaredMethods())
                            .parallelStream()
                            .forEach(routerMethod -> {
                                Router routerAnnotation = routerMethod.getAnnotation(Router.class);
                                routerBoss.register(routerAnnotation, parentPath);
                                Arrays.asList(routerAnnotation.auth())
                                        .parallelStream()
                                        .forEach(authId -> authFactory.putBundle(authId, bundle.id()));

                            }));
        });
    }
}
