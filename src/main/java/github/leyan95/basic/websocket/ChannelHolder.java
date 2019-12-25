package github.leyan95.basic.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wujianchuan
 * 包含某用户 [使用过的所有管道] 和 [待接收的消息]
 */
class ChannelHolder {
    private String avatar;
    private Map<String, Channel> channels;
    private List<String> messageBlock;

    ChannelHolder(String avatar) {
        this.avatar = avatar;
        channels = new ConcurrentHashMap<>(4);
        messageBlock = new ArrayList<>();
    }

    void putChannel(Channel channel) {
        String remoteAddress = channel.remoteAddress().toString();
        String ip = remoteAddress.substring(1, remoteAddress.indexOf(":"));
        channels.put(ip, channel);
    }

    List<Channel> getActiveChannel() {
        return channels.values().stream().filter(Channel::isOpen).collect(Collectors.toList());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getMessageBlock() {
        return messageBlock;
    }

    void cleanBlock(){
        this.messageBlock = new ArrayList<>();
    }

    void set2Block(String message) {
        this.messageBlock.add(message);
    }
}