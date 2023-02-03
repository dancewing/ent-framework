package io.entframework.kernel.socket.feign;

import io.entframework.kernel.socket.api.SocketClientOperatorApi;
import io.entframework.kernel.socket.api.exception.SocketException;

public class SocketOperatorServiceWrapper implements SocketClientOperatorApi {
    private final SocketClientOperatorApi feignClient;

    public SocketOperatorServiceWrapper(SocketClientOperatorApi feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public void sendMsgOfUserSession(String msgType, String userId, Object msg) throws SocketException {
        feignClient.sendMsgOfUserSession(msgType, userId, msg);
    }
}
