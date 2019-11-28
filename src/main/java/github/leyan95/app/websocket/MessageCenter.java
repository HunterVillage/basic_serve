package github.leyan95.app.websocket;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author wujianchuan
 */
public class MessageCenter {
    private static final MessageCenter INSTANCE = new MessageCenter();
    private static final ChannelCenter CHANNEL_CENTER = ChannelCenter.getInstance();

    private MessageCenter() {
    }

    public static MessageCenter getInstance() {
        return INSTANCE;
    }

    public void sendTo(String avatar, String message) {
        CHANNEL_CENTER.getActiveHolder(avatar).parallelStream().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(message)));
    }

    public void sendToAll(String message) {
        CHANNEL_CENTER.getAllActiveHolder().parallelStream().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(message)));
    }
}
