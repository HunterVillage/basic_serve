package github.leyan95.demo;

import github.leyan95.app.annotation.AuthInstaller;
import github.leyan95.app.auth.AbstractAuthInstaller;

/**
 * @author wujianchuan
 */
@AuthInstaller
public class OrderAuthInstaller extends AbstractAuthInstaller {
    public static final String ORDER_READ = "order_read";
    public static final String ORDER_MANAGE = "order_manage";

    @Override
    public void install() {
        super.install(ORDER_READ, "查看单据", "描述");
        super.install(ORDER_MANAGE, "查看管理", "描述");
    }
}
