package github.leyan95.basic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wujianchuan
 */
public class AppMessagePersistent {

    public List<AppMessage> loadByReceiver(String receiver) {
        //TODO Load messages which the receiver didn't read.
        return new ArrayList<>();
    }

    public void save(AppMessage appMessage) {
        //TODO Save app message.
    }

    public AppMessage read(String messageUuid) {
        //TODO Update the message as viewed.
        return new AppMessage();
    }
}
