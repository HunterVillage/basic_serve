package github.leyan95.demo.controller;

import github.leyan95.app.annotation.Bundle;
import github.leyan95.app.annotation.Router;
import github.leyan95.app.controller.AppController;
import github.leyan95.demo.OrderAuthInstaller;

/**
 * @author wujianchuan
 */
@Bundle(id = "order", bundlePath = "/app/order_manage")
public class OrderController implements AppController {
    @Router(id = "/oder_detail", name = "查看单据", description = "保存单据描述", auth = OrderAuthInstaller.ORDER_READ)
    public String getOrder() {
        return "查看单据";
    }

    @Router(id = "/oder_save", name = "保存单据", description = "保存单据描述", auth = OrderAuthInstaller.ORDER_MANAGE)
    public String saveOrder() {
        return "保存单据";
    }
}
