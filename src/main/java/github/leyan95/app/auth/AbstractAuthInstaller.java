package github.leyan95.app.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wujianchuan
 */
public abstract class AbstractAuthInstaller {

    private final Logger logger = LoggerFactory.getLogger(AbstractAuthInstaller.class);
    private AuthFactory authFactory = AuthFactory.getInstance();

    /**
     * 注册所有的权限
     */
    public abstract void install();

    /**
     * @param authId          权限标识
     * @param authName        权限名称
     * @param authDescription 权限描述
     */
    protected void install(String authId, String authName, String authDescription) {
        authFactory.register(new Auth(authId, authName, authDescription));
        logger.info("The auth of \"{}\" was install.", authId);
    }
}
