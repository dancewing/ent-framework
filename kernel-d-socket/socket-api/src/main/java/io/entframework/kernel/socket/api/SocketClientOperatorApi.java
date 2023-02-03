package io.entframework.kernel.socket.api;

import io.entframework.kernel.socket.api.exception.SocketException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SocketClientOperatorApi {
    /**
     * 发送消息到指定用户的所有会话
     * <p>
     * 如果用户同一个消息类型建立了多个会话，则统一全部发送
     *
     * @param msgType 消息类型可参考{@link io.entframework.kernel.socket.api.enums}枚举类
     * @param userId  用户ID
     * @param msg     消息体
     * @date 2021/6/2 上午9:35
     **/
    @PostMapping (path = "/client/sys-socket/send-msg-to-user-session")
    void sendMsgOfUserSession(@RequestParam("msgType") String msgType, @RequestParam("userId") String userId, @RequestBody Object msg) throws SocketException;
}
