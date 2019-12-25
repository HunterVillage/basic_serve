package github.leyan95.basic.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wujianchuan
 */
public class ChannelCenter {
    private static final Map<String, ChannelHolder> CHANNEL_POOL = new ConcurrentHashMap<>(60);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    private static final ChannelCenter INSTANCE = new ChannelCenter();
    private boolean singleOnline = false;

    public static ChannelCenter getInstance() {
        return INSTANCE;
    }

    public void setSingleOnline(boolean singleOnline){
        this.singleOnline = singleOnline;
    }

    void register(String avatar, Channel newChannel) {
        ChannelHolder channelHolder = CHANNEL_POOL.putIfAbsent(avatar, new ChannelHolder(avatar));
        if (channelHolder == null) {
            channelHolder = CHANNEL_POOL.get(avatar);
        } else {
            if (singleOnline) {
                for (Channel activeChannel : channelHolder.getActiveChannel()) {
                    try {
                        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(OBJECT_MAPPER.writeValueAsString(MessageBody.offLine()));
                        activeChannel.writeAndFlush(textWebSocketFrame);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        channelHolder.putChannel(newChannel);
        resendBlockMessage(channelHolder, newChannel);
    }

    public void send(MessageBody messageBody) throws JsonProcessingException {
        String avatar = messageBody.getReceiver();
        String messageJson = OBJECT_MAPPER.writeValueAsString(messageBody);
        List<Channel> channels = new ArrayList<>();
        ChannelHolder channelHolder = CHANNEL_POOL.get(avatar);
        if (channelHolder != null) {
            channels = channelHolder.getActiveChannel();
        } else {
            CHANNEL_POOL.putIfAbsent(avatar, new ChannelHolder(avatar));
        }
        if (channels.size() <= 0) {
            CHANNEL_POOL.get(avatar).set2Block(messageJson);
        }
        channels.parallelStream().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(messageJson)));
    }

    private void resendBlockMessage(ChannelHolder channelHolder, Channel newChannel) {
        List<String> blockMessages = channelHolder.getMessageBlock();
        if (blockMessages.size() > 0) {
            blockMessages.parallelStream().forEach(message -> newChannel.writeAndFlush(new TextWebSocketFrame(message)));
            channelHolder.cleanBlock();
        }
    }

    List<Channel> getAllActiveHolder() {
        List<ChannelHolder> channelHolders = new ArrayList<>(CHANNEL_POOL.values());
        return channelHolders.stream().map(ChannelHolder::getActiveChannel).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
