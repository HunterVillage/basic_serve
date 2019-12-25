package github.leyan95.basic.controller;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author wujianchuan
 */
public class RouterInfo {

    private String path;
    private String name;
    private String description;
    private List<RequestMethod> methods;
    private List<String> authIds;

    public static RouterInfo newInstance() {
        return new RouterInfo();
    }

    public RouterInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public RouterInfo setName(String name) {
        this.name = name;
        return this;
    }

    public RouterInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public RouterInfo setMethods(List<RequestMethod> methods) {
        this.methods = methods;
        return this;
    }

    public RouterInfo setAuthIds(List<String> authIds) {
        this.authIds = authIds;
        return this;
    }

    public boolean authConfirm(List<String> heldAuth) {
        if (this.authIds.size() <= 0) {
            return true;
        } else {
            for (String authId : this.authIds) {
                if (heldAuth.contains(authId)) {
                    return true;
                }
            }
            return false;
        }

    }
}
