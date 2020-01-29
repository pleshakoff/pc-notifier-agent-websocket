package com.parcom.asyncdto;

import com.parcom.notifier_agent_websocket.notification.NotificationType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NotificationAgentDto  {

    @NotNull
    private NotificationType notificationType;

    @NotNull
    private String  title;

    private  String  message;

    private  @NotNull String idObjectSender;

    @NotNull
    private  Long idUserSender;

    @NotNull
    private  Long idUserReceiver;


}
