package github.leyan95.app.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wujianchuan
 */
public class AuthFactory {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthFactory.class);
    private final static Map<String, Auth> AUTH_POOL = new ConcurrentHashMap<>(16);
    private final static Map<String, Set<String>> AUTH_BUNDLES = new ConcurrentHashMap<>(8);
    private final static AuthFactory INSTANCE = new AuthFactory();

    private AuthFactory() {
    }

    public static AuthFactory getInstance() {
        return INSTANCE;
    }

    void register(Auth auth) {
        AUTH_POOL.put(auth.id, auth);
    }

    public void putBundle(String authId, String bundleId) {
        if (AUTH_POOL.get(authId) == null) {
            throw new AuthNullPointException(String.format("Auth identified as %s is not registered.", authId));
        }
        Set<String> bundles = AUTH_BUNDLES.getOrDefault(authId, new HashSet<>());
        AUTH_BUNDLES.putIfAbsent(authId, bundles);
        boolean success = bundles.add(bundleId);
        if (success) {
            LOGGER.info("The bundle of \"{}\" was put into the auth of \"{}\"", bundleId, authId);
        }
    }

    public Set<String> canBundles(List<String> authIds) {
        Set<String> bundles = new HashSet<>();
        authIds.forEach(authId -> bundles.addAll(AUTH_BUNDLES.get(authId)));
        return bundles;
    }

    public Map<String, Auth> getAllAuth() {
        Map<String, Auth> copy = new ConcurrentHashMap<>(AUTH_POOL.size());
        copy.putAll(AUTH_POOL);
        return copy;
    }
}
