package com.parcom.notifier_agent_websocket.notification;

import com.parcom.asyncdto.NotificationAgentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    public static final String TOPIC_EVENTS = "/topic/notifications/";
    private final SimpMessagingTemplate template;


    @Override
    public void send(NotificationAgentDto notificationAgentDto) {
        template.convertAndSend(TOPIC_EVENTS +notificationAgentDto.getIdUserReceiver(),
                notificationAgentDto);
        log.info("Sent trough the websocket for user id \"{}\" ",notificationAgentDto.getIdUserReceiver());
        log.info("Type:  \"{}\" Title: \" {}\"", notificationAgentDto.getNotificationType(), notificationAgentDto.getTitle());
        log.info("Message:  \"{}\"", notificationAgentDto.getMessage());
    }



}
