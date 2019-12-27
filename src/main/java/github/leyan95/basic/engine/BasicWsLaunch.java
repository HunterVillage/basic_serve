package github.leyan95.basic.engine;

import github.leyan95.basic.config.BasicConfig;
import github.leyan95.basic.websocket.DeviceControl;
import github.leyan95.basic.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author wujianchuan
 */
@Configuration
public class BasicWsLaunch implements CommandLineRunner {
    private final BasicConfig basicConfig;

    @Autowired
    public BasicWsLaunch(BasicConfig basicConfig) {
        this.basicConfig = basicConfig;
    }

    private void wsRun() {
        int wsPort = basicConfig.getWsport();
        boolean singleOnline = basicConfig.getSingle();
        DeviceControl.getInstance().setSingleOnline(singleOnline);
        new WebSocketServer().run(wsPort);
    }

    @Override
    public void run(String... args) {
        this.wsRun();
    }
}
