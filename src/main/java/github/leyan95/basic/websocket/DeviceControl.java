package github.leyan95.basic.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.leyan95.basic.model.DeviceInfo;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wujianchuan
 */
public class DeviceControl {
    private static final DeviceControl INSTANCE = new DeviceControl();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    private ChannelCenter channelCenter;

    private DeviceControl() {
        this.channelCenter = ChannelCenter.getInstance();
    }

    public static DeviceControl getInstance() {
        return INSTANCE;
    }

    public void setSingleOnline(boolean singleOnline) {
        this.channelCenter.setSingleOnline(singleOnline);
    }

    public void sendMessage(MessageBody messageBody) throws JsonProcessingException {
        String avatar = messageBody.getReceiver();
        String messageJson = OBJECT_MAPPER.writeValueAsString(messageBody);
        List<Channel> channels = this.channelCenter.getAllActiveChannel(avatar);
        if (channels.size() <= 0) {
            this.channelCenter.getChannelHolder(avatar).set2Block(messageJson);
        }
        channels.parallelStream().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(messageJson)));
    }

    public List<DeviceInfo> getAllDevice() {
        return this.channelCenter.getAllChannelHolder().stream()
                .map(
                        channelHolder -> DeviceInfo.newInstance()
                                .setSerialNo(channelHolder.getLatestSerialNo())
                                .setLatestLoginUser(channelHolder.getAvatar())
                                .setOnline(channelHolder.getActiveChannel().size() > 0)
                )
                .collect(Collectors.toList());
    }
}
