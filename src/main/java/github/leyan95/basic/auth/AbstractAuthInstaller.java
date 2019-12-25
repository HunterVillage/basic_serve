package github.leyan95.basic.auth;

import github.leyan95.basic.model.AppAuth;
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
        authFactory.register(new AppAuth(authId, authName, authDescription));
        logger.info("The auth of \"{}\" was install.", authId);
    }
}
