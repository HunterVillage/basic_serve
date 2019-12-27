package github.leyan95.basic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import github.leyan95.basic.annotation.Bundle;
import github.leyan95.basic.annotation.Router;
import github.leyan95.basic.model.AppMessage;
import github.leyan95.basic.model.AppMessagePersistent;
import github.leyan95.basic.websocket.MessageBody;
import github.leyan95.basic.websocket.DeviceControl;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wujianchuan
 */
@Bundle(id = "message", bundlePath = "/basic/message")
public class AppMessageBundle {

    @Router(id = "/list_own_message", name = "查看登录人所有消息")
    ResponseBody listOwnMessage(@RequestAttribute(value = "sysLoginAvatar") String sysLoginAvatar) {
        AppMessagePersistent appMessagePersistent = new AppMessagePersistent();
        List<AppMessage> appMessageList = appMessagePersistent.loadByReceiver(sysLoginAvatar);
        return ResponseBody.success().data(appMessageList);
    }

    @Router(id = "/read", name = "更新消息为已读状态")
    ResponseBody readMessage(@RequestParam(value = "uuid") String uuid) {
        AppMessagePersistent appMessagePersistent = new AppMessagePersistent();
        return ResponseBody.success().data(appMessagePersistent.read(uuid));
    }

    @Router(id = "/text_message", name = "普通消息测试")
    String sendTextMessage(@RequestParam(value = "avatar") String avatar, @RequestParam(value = "content") String content) throws JsonProcessingException {
        AppMessage appMessage = new AppMessage()
                .sender("BB", "白波")
                .receiver(avatar, avatar)
                .title("普通消息测试")
                .content(content);
        AppMessagePersistent appMessagePersistent = new AppMessagePersistent();
        appMessagePersistent.save(appMessage);
        MessageBody messageBody = MessageBody.appMessage(appMessage);
        DeviceControl.getInstance().sendMessage(messageBody);
        return "sendTextMessage";
    }

    @Router(id = "/sys_message", name = "测试系统动作消息 —— 强制下线")
    String sendSysMessage(@RequestParam(value = "avatar") String avatar) throws JsonProcessingException {
        MessageBody messageBody = MessageBody.offLine().receiver(avatar, avatar);
        DeviceControl.getInstance().sendMessage(messageBody);
        return "sendSysMessage";
    }
}
