package com.parcom.notifier_agent_websocket.notification;

import com.parcom.asyncdto.NotificationAgentDto;

public interface NotificationService {
    void send(NotificationAgentDto notificationAgentDto);
}
