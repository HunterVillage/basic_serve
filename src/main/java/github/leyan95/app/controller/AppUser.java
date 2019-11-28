package github.leyan95.app.controller;

/**
 * @author wujianchuan
 */
public class AppUser {
    private String avatar;
    private String name;
    private String departmentUuid;
    private String departmentName;
    private String[] authIds;

    public static AppUser newInstance() {
        return new AppUser();
    }

    public String getAvatar() {
        return avatar;
    }

    public AppUser setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getName() {
        return name;
    }

    public AppUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public AppUser setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
        return this;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public AppUser setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public String[] getAuthIds() {
        return authIds;
    }

    public AppUser setAuthIds(String[] authIds) {
        this.authIds = authIds;
        return this;
    }
}
