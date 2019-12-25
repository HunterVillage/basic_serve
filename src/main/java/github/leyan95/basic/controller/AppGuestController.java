package github.leyan95.basic.controller;

import github.leyan95.basic.auth.AuthFactory;
import github.leyan95.basic.filter.TokenUtil;
import github.leyan95.basic.model.AppUser;
import github.leyan95.basic.model.AppUserPersistent;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wujianchuan
 */
@RestController
@RequestMapping("/basic")
public class AppGuestController {

    private final TokenUtil tokenUtil;

    private AuthFactory authFactory = AuthFactory.getInstance();

    public AppGuestController(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseBody login(@RequestBody Map<String, String> encodeAppUserInfo) {
        AppUserPersistent persistent = new AppUserPersistent();
        // TODO Password encode.
        AppUser appUser = persistent.findOne(encodeAppUserInfo.get("userName"), encodeAppUserInfo.get("password"));
        if (appUser == null) {
            return ResponseBody.error().title("登陆失败").message("用户名或密码不正确");
        }
        List authList = persistent.loadAuth(appUser.getUuid());
        Set bundles = authFactory.canBundles(authList);
        UserView userView = UserView.newInstance()
                .setAvatar(appUser.getAvatar())
                .setName(appUser.getName())
                .setAuthIds(authList)
                .setBundleIds(bundles);
        String token = tokenUtil.generateToken(userView);
        return ResponseBody.success()
                .title("登录成功")
                .message(String.format("%s 成功登录系统。", userView.getName()))
                .data(userView)
                .token(token);
    }
}
