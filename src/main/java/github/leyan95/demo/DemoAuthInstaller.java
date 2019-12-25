package github.leyan95.demo;

import github.leyan95.basic.annotation.AuthInstaller;
import github.leyan95.basic.auth.AbstractAuthInstaller;

/**
 * @author wujianchuan
 */
@AuthInstaller
public class DemoAuthInstaller extends AbstractAuthInstaller {
    public static final String PREPARATION_READ = "preparation_read";
    public static final String PREPARATION_MANAGE = "preparation_manage";
    public static final String LIQUID_EXCHANGE_READ = "liquid_exchange_read";
    public static final String LIQUID_EXCHANGE_MANAGE = "liquid_exchange_manage";

    @Override
    public void install() {
        super.install(PREPARATION_READ, "查看制备", "描述");
        super.install(PREPARATION_MANAGE, "管理制备", "描述");

        super.install(LIQUID_EXCHANGE_READ, "查看换液", "描述");
        super.install(LIQUID_EXCHANGE_MANAGE, "管理换液", "描述");
    }
}
