package github.leyan95.basic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wujianchuan
 */
public class AppUserPersistent {

    public AppUser findOne(String avatar, String password) {
        //TODO Load user by avatar and password.
        AppUser appUser = new AppUser();
        appUser.setAvatar("ADMIN");
        appUser.setName("管理员");
        return appUser;
    }

    public List loadAuth(String uuid) {
        String sql = "SELECT T1.AUTH_ID  " +
                " FROM T_APP_AUTH T1 " +
                "    LEFT JOIN T_APP_ROLE_AUTH T2 ON T1.UUID = T2.AUTH_UUID " +
                "    LEFT JOIN T_APP_USER_ROLE T3 ON T2.ROLE_UUID = T3.ROLE_UUID " +
                " WHERE T3.USER_UUID = :UUID ";
        //TODO Load the user's authority.
        return new ArrayList();
    }
}
