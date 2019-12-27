package github.leyan95.basic.model;

/**
 * @author wujianchuan
 */
public class DeviceInfo {
    private String serialNo;
    private String latestLoginUser;
    private Boolean online;

    private DeviceInfo() {
    }

    public static DeviceInfo newInstance() {
        return new DeviceInfo();
    }

    public DeviceInfo setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public DeviceInfo setLatestLoginUser(String latestLoginUser) {
        this.latestLoginUser = latestLoginUser;
        return this;
    }

    public DeviceInfo setOnline(Boolean online) {
        this.online = online;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getLatestLoginUser() {
        return latestLoginUser;
    }

    public Boolean getOnline() {
        return online;
    }
}
