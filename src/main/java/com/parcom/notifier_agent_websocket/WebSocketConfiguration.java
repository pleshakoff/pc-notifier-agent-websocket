package com.parcom.notifier_agent_websocket;

import com.parcom.notifier_agent_websocket.security.WsHttpHandshakeInterceptor;
import com.parcom.notifier_agent_websocket.security.WsSessionChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration  implements WebSocketMessageBrokerConfigurer {


    private final WsHttpHandshakeInterceptor wsHttpHandshakeInterceptor;
    private final WsSessionChannelInterceptor wsSessionChannelInterceptor;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/pusher").setAllowedOrigins("*").addInterceptors(wsHttpHandshakeInterceptor);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(wsSessionChannelInterceptor);
    }


}