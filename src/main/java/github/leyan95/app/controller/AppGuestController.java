package github.leyan95.app.controller;

import github.leyan95.app.filter.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wujianchuan
 */
@RestController
@RequestMapping("/app")
public class AppGuestController {

    @Autowired
    private TokenUtil tokenUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseBody login(@RequestBody Map<String, String> encodeAppUserInfo) {
        AppUser appUser = AppUser.newInstance()
                .setAvatar(encodeAppUserInfo.get("userName"))
                .setName("管理员")
                .setDepartmentName("质保科");
        String token = tokenUtil.generateToken(appUser);
        return ResponseBody.success()
                .title("登录成功")
                .message(String.format("%s 成功登录系统。", appUser.getName()))
                .data(appUser)
                .token(token);
    }
}
