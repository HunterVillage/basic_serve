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

    static ChannelCenter getInstance() {
        return INSTANCE;
    }

    void setSingleOnline(boolean singleOnline) {
        this.singleOnline = singleOnline;
    }

    void register(String avatar, String serialNo, Channel newChannel) {
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
        resendBlockMessage(channelHolder.putChannel(newChannel).putSerialNo(serialNo), newChannel);
    }

    private void resendBlockMessage(ChannelHolder channelHolder, Channel newChannel) {
        List<String> blockMessages = channelHolder.getMessageBlock();
        if (blockMessages.size() > 0) {
            blockMessages.parallelStream().forEach(message -> newChannel.writeAndFlush(new TextWebSocketFrame(message)));
            channelHolder.cleanBlock();
        }
    }

    ChannelHolder getChannelHolder(String avatar) {
        return CHANNEL_POOL.get(avatar);
    }

    List<ChannelHolder> getAllChannelHolder() {
        return (List<ChannelHolder>) CHANNEL_POOL.values();
    }

    List<Channel> getAllActiveChannel(String avatar) {
        List<Channel> channels = new ArrayList<>();
        ChannelHolder channelHolder = getChannelHolder(avatar);
        if (channelHolder != null) {
            channels = channelHolder.getActiveChannel();
        } else {
            CHANNEL_POOL.putIfAbsent(avatar, new ChannelHolder(avatar));
        }
        return channels;
    }

    List<Channel> getAllActiveChannel() {
        List<ChannelHolder> channelHolders = new ArrayList<>(CHANNEL_POOL.values());
        return channelHolders.stream().map(ChannelHolder::getActiveChannel).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
