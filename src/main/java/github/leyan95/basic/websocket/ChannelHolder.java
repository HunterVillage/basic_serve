package github.leyan95.basic.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wujianchuan
 * 包含某用户 [使用过的所有管道] 和 [待接收的消息]
 */
class ChannelHolder {
    private String avatar;
    private Set<String> serialNoSet = new HashSet<>();
    private String latestSerialNo;
    private Map<String, Channel> channels;
    private List<String> messageBlock;

    ChannelHolder(String avatar) {
        this.avatar = avatar;
        channels = new ConcurrentHashMap<>(4);
        messageBlock = new ArrayList<>();
    }

    public String getAvatar() {
        return avatar;
    }

    public Set<String> getSerialNoSet() {
        return serialNoSet;
    }

    public String getLatestSerialNo() {
        return latestSerialNo;
    }

    ChannelHolder putChannel(Channel channel) {
        String remoteAddress = channel.remoteAddress().toString();
        String ip = remoteAddress.substring(1, remoteAddress.indexOf(":"));
        this.channels.put(ip, channel);
        return this;
    }

    ChannelHolder putSerialNo(String serialNo) {
        this.latestSerialNo = serialNo;
        this.serialNoSet.add(serialNo);
        return this;
    }

    List<Channel> getActiveChannel() {
        return channels.values().stream().filter(Channel::isOpen).collect(Collectors.toList());
    }


    List<String> getMessageBlock() {
        return messageBlock;
    }

    void cleanBlock() {
        this.messageBlock = new ArrayList<>();
    }

    void set2Block(String message) {
        this.messageBlock.add(message);
    }
}