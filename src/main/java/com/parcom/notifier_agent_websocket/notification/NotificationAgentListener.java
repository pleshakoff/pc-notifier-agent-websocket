package com.parcom.notifier_agent_websocket.notification;

import com.parcom.asyncdto.NotificationAgentDto;
import com.parcom.security_client.AsyncUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class NotificationAgentListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${parcom.kafka.topic.notificationAgent}", groupId = "${parcom.kafka.group.notifierAgent}")
    public void listen(@Payload NotificationAgentDto notificationAgentDto, @Header("X-Auth-Token") String token) {
        log.info("Get message from broker");
         AsyncUserUtils.authByToken(token);
        notificationService.send(notificationAgentDto);
    }


}
