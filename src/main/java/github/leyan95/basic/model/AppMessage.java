package github.leyan95.basic.model;

import java.util.Date;

/**
 * @author wujianchuan
 */
public class AppMessage {
    private static final long serialVersionUID = -2411370149789961506L;

    private String uuid;
    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private String title;
    private String content;
    private Boolean unread;
    private Date sendTime;
    private Date readTime;

    public AppMessage() {
        this.sendTime = new Date();
    }

    public AppMessage sender(String senderAvatar, String senderName) {
        this.sender = senderAvatar;
        this.senderName = senderName;
        return this;
    }

    public AppMessage receiver(String receiverAvatar, String receiverName) {
        this.receiver = receiverAvatar;
        this.receiverName = receiverName;
        return this;
    }

    public AppMessage title(String title) {
        this.title = title;
        return this;
    }

    public AppMessage content(String content) {
        this.content = content;
        return this;
    }

    public AppMessage sendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public AppMessage readTime(Date readTime) {
        this.readTime = readTime;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public Boolean getUnread() {
        return unread;
    }

    public void setUnread(Boolean unread) {
        this.unread = unread;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }
}
