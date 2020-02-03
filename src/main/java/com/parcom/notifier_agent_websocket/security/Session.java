package com.parcom.notifier_agent_websocket.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
class Session {

    private final HttpSession session;
    private final LocalDateTime expired;

}
