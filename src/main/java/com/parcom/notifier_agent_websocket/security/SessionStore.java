package com.parcom.notifier_agent_websocket.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
class SessionStore {

    static final String SESSION_ID = "sessionId";

    @Value("${parcom.security.websocket.timeout}")
    Integer timeout;

    String put(HttpSession session) {
        String sessionId = session.getId();
        sessions.put(sessionId, new Session(session, LocalDateTime.now().plusSeconds(timeout)));
        log.info("Session {} is waiting for authorization",sessionId);
        return sessionId;
    }


    private ConcurrentHashMap<String,Session> sessions = new ConcurrentHashMap<>();

    void accept(String sessionId) {
        sessions.remove(sessionId);
        log.info("Session {} was accepted",sessionId);
    }

    void kill(String sessionId) {
        sessions.get(sessionId).getSession().invalidate();
        sessions.remove(sessionId);
        log.info("Session {} was killed",sessionId);
    }

    @Scheduled(fixedRate = 2000)
    public void cleanExpired(){
        log.debug("Clean expired");
        LocalDateTime now = LocalDateTime.now();
        sessions.values().stream().filter(session -> session.getExpired().isBefore(now)).
                forEach(session -> {
                            HttpSession current = session.getSession();
                            current.invalidate();
                            log.info("Session {} expired",current.getId());
                }
                );
        sessions.entrySet().removeIf(node -> node.getValue().getExpired().isBefore(now));
    }

}
