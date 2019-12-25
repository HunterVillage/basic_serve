package github.leyan95.basic.websocket;

import github.leyan95.basic.model.AppMessage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wujianchuan
 */
public class MessageBody implements Serializable {
    private static final long serialVersionUID = 8436652448592085001L;

    private static final String COMMON_MESSAGE = "CommonMessage";
    private static final String SYSTEM_ACTION_MESSAGE = "SystemActionMessage";

    private static final String MANDATORY_OFFLINE = "MandatoryOffline";

    private String type;
    private String cmd;
    private String uuid;
    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private String title;
    private String content;
    private Date sendTime;
    private Boolean unread = true;

    private MessageBody(String type) {
        this.type = type;
    }

    public static MessageBody appMessage(AppMessage appMessage) {
        return commonMessage()
                .uuid(appMessage.getUuid())
                .sender(appMessage.getSender(), appMessage.getSenderName())
                .receiver(appMessage.getReceiver(), appMessage.getReceiverName())
                .title(appMessage.getTitle())
                .content(appMessage.getContent())
                .sendTime(appMessage.getSendTime());
    }

    public static MessageBody commonMessage() {
        return new MessageBody(COMMON_MESSAGE);
    }

    public static MessageBody offLine() {
        return new MessageBody(SYSTEM_ACTION_MESSAGE).cmd(MANDATORY_OFFLINE).sendTime(new Date());
    }

    private MessageBody cmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public MessageBody uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public MessageBody sender(String senderAvatar, String senderName) {
        this.sender = senderAvatar;
        this.senderName = senderName;
        return this;
    }

    public MessageBody receiver(String receiverAvatar, String receiverName) {
        this.receiver = receiverAvatar;
        this.receiverName = receiverName;
        return this;
    }

    public MessageBody title(String title) {
        this.title = title;
        return this;
    }

    public MessageBody content(String content) {
        this.content = content;
        return this;
    }

    public MessageBody sendTime(Date sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getCmd() {
        return cmd;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public Boolean getUnread() {
        return unread;
    }
}
