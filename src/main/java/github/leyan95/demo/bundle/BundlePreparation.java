package github.leyan95.demo.bundle;

import github.leyan95.basic.annotation.Bundle;
import github.leyan95.basic.annotation.Router;
import github.leyan95.basic.controller.AppBundle;
import github.leyan95.basic.controller.ResponseBody;
import github.leyan95.demo.DemoAuthInstaller;

/**
 * @author wujianchuan
 */
@Bundle(id = "preparation", bundlePath = "/basic/preparation")
public class BundlePreparation implements AppBundle {
    @Router(id = "/preparation_detail", name = "查看制备数据", auth = DemoAuthInstaller.PREPARATION_READ)
    public ResponseBody getPreparation() {
        return ResponseBody.success().data("查看制备数据");
    }

    @Router(id = "/preparation_save", name = "保存制备数据", auth = DemoAuthInstaller.PREPARATION_MANAGE)
    public ResponseBody savePreparation() {
        return ResponseBody.success().data("保存制备数据");
    }
}
