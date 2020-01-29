package com.parcom.notifier_agent_websocket.notification;

import com.parcom.asyncdto.NotificationAgentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    @Override
    public void send(NotificationAgentDto notificationAgentDto) {
            log.info("Send ");
    }



}
