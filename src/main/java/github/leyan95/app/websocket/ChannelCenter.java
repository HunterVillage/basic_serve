package github.leyan95.app.websocket;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wujianchuan
 */
class ChannelCenter {
    private static final Map<String, ChannelHolder> CHANNEL_POOL = new ConcurrentHashMap<>(60);
    private static final ChannelCenter INSTANCE = new ChannelCenter();

    private ChannelCenter() {
    }

    static ChannelCenter getInstance() {
        return INSTANCE;
    }

    void register(String avatar, Channel channel) {
        ChannelHolder channelHolder = CHANNEL_POOL.putIfAbsent(avatar, new ChannelHolder(avatar));
        if (channelHolder == null) {
            channelHolder = CHANNEL_POOL.get(avatar);
        }
        channelHolder.putChannel(channel);
    }

    List<Channel> getActiveHolder(String avatar) {
        List<Channel> channels = new ArrayList<>();
        ChannelHolder channelHolder = CHANNEL_POOL.get(avatar);
        if (channelHolder != null) {
            channels = channelHolder.getActiveChannel();
        }
        return channels;
    }

    List<Channel> getAllActiveHolder() {
        List<ChannelHolder> channelHolders = new ArrayList<>(CHANNEL_POOL.values());
        return channelHolders.stream().map(ChannelHolder::getActiveChannel).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private static class ChannelHolder {
        private String avatar;
        private Map<String, Channel> channels;
        List<Message> readBlock;
        List<Message> sendBlock;

        ChannelHolder(String avatar) {
            this.avatar = avatar;
            channels = new ConcurrentHashMap<>(4);
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

        public List<Message> getReadBlock() {
            return readBlock;
        }

        public void setReadBlock(List<Message> readBlock) {
            this.readBlock = readBlock;
        }

        public List<Message> getSendBlock() {
            return sendBlock;
        }

        public void setSendBlock(List<Message> sendBlock) {
            this.sendBlock = sendBlock;
        }
    }

    private static class Message {
        private String collegeAvatar;
        private String title;
        private String content;

        public Message(String collegeAvatar, String title, String content) {
            this.collegeAvatar = collegeAvatar;
            this.title = title;
            this.content = content;
        }

        public String getCollegeAvatar() {
            return collegeAvatar;
        }

        public void setCollegeAvatar(String collegeAvatar) {
            this.collegeAvatar = collegeAvatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
