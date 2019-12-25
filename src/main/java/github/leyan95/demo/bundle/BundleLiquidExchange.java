package github.leyan95.demo.bundle;

import github.leyan95.basic.annotation.Bundle;
import github.leyan95.basic.annotation.Router;
import github.leyan95.basic.controller.AppBundle;
import github.leyan95.basic.controller.ResponseBody;
import github.leyan95.demo.DemoAuthInstaller;

/**
 * @author wujianchuan
 */
@Bundle(id = "liquid_exchange", bundlePath = "/basic/liquid_exchange")
public class BundleLiquidExchange implements AppBundle {
    @Router(id = "/liquid_exchange_detail", name = "查看换液数据", auth = DemoAuthInstaller.LIQUID_EXCHANGE_READ)
    public ResponseBody getLiquidExchange() {
        return ResponseBody.success().data("查看换液数据");
    }

    @Router(id = "/liquid_exchange_save", name = "保存换液数据", auth = DemoAuthInstaller.LIQUID_EXCHANGE_MANAGE)
    public ResponseBody saveLiquidExchange() {
        return ResponseBody.success().data("保存换液数据");
    }
}
