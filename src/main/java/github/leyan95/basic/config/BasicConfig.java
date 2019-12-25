package github.leyan95.basic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wujianchuan
 */
@Component
@ConfigurationProperties(prefix = "basic")
public class BasicConfig {

    private Integer wsport;
    private Boolean single;

    public Integer getWsport() {
        return wsport;
    }

    public void setWsport(Integer wsport) {
        this.wsport = wsport;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }
}
