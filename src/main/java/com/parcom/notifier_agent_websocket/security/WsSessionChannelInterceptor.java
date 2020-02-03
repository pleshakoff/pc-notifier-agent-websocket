package com.parcom.notifier_agent_websocket.security;

import com.parcom.exceptions.AccessDeniedParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.security_client.AsyncUserUtils;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class WsSessionChannelInterceptor implements ChannelInterceptor {


    private final SessionStore sessionStore;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info("Channel Interceptor {}",accessor.getCommand());
        if (Objects.equals(accessor.getCommand(), StompCommand.CONNECT))
        {
          String sessionId = Optional.ofNullable(accessor.getSessionAttributes()).map(stringObjectMap -> stringObjectMap.
                    get(SessionStore.SESSION_ID)).map(Objects::toString).orElse(null);
           try {
               List<String> token = accessor.getNativeHeader(UserUtils.X_AUTH_TOKEN);
               if (token ==null || token.size() == 0) {
                   throw  new AccessDeniedParcomException();
               }
               AsyncUserUtils.authByToken(token.get(0));
               sessionStore.accept(sessionId);

           }
           catch (Exception e)
           {
               sessionStore.kill(sessionId);
               throw e;
           }
        }
        return message;
    }
}