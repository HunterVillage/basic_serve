package github.leyan95.app.filter;

/**
 * @author wujianchuan
 */
public class InvalidTokenException extends NullPointerException {
    private static final long serialVersionUID = -5907298648965756967L;

    public InvalidTokenException(String s) {
        super(s);
    }
}
