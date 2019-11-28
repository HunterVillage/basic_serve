package github.leyan95.app.launch;

import github.leyan95.app.annotation.Bundle;
import github.leyan95.app.annotation.Router;
import github.leyan95.app.auth.AbstractAuthInstaller;
import github.leyan95.app.auth.AuthFactory;
import github.leyan95.app.controller.AppController;
import github.leyan95.app.controller.RouterBoss;
import github.leyan95.app.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author wujianchuan
 */
@Component
@Order(-10)
public class AppLaunch implements CommandLineRunner {

    @Autowired
    private List<AbstractAuthInstaller> installers;
    @Autowired
    private List<AppController> appControllers;

    private RouterBoss routerBoss = RouterBoss.getInstance();
    private AuthFactory authFactory = AuthFactory.getInstance();

    @Override
    public void run(String... args) throws Exception {
        this.authInstall();
        this.controllerInstall();
        this.wsRun();
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

    private void wsRun() throws Exception {
        ResourceBundle resourceApplication = ResourceBundle.getBundle("application", Locale.US);
        int wsPort = Integer.parseInt(resourceApplication.getString("app.wsport"));
        new WebSocketServer().run(wsPort);
    }
}
