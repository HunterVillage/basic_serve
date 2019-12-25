package github.leyan95.basic.controller;

import java.io.Serializable;

/**
 * @author wujianchuan
 */
public class ResponseBody implements Serializable {
    private static final long serialVersionUID = 3126753390350039582L;
    private boolean success;
    private String category;
    private Object data;
    private String title;
    private String message;
    private boolean resend;
    private String token;
    private boolean reLogin;

    public static ResponseBody success() {
        return new ResponseBody(true, CategoryTypes.SUCCESS);
    }

    public static ResponseBody info() {
        return new ResponseBody(true, CategoryTypes.INFO);
    }

    public static ResponseBody warning() {
        return new ResponseBody(true, CategoryTypes.WARING);
    }

    public static ResponseBody error() {
        return new ResponseBody(false, CategoryTypes.ERROR);
    }

    public ResponseBody title(String title) {
        this.title = title;
        return this;
    }

    public ResponseBody message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBody data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseBody resend() {
        this.resend = true;
        return this;
    }

    public ResponseBody token(String token) {
        this.token = token;
        return this;
    }

    public ResponseBody reLogin() {
        this.reLogin = true;
        return this;
    }

    private ResponseBody(boolean success, String category) {
        this.success = success;
        this.category = category;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCategory() {
        return category;
    }

    public Object getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public boolean isResend() {
        return resend;
    }

    public boolean getReLogin() {
        return reLogin;
    }
}
