FROM openjdk:8u181-jre-slim
COPY /build/libs/pc-notifier-agent-websocket.jar pc-notifier-agent-websocket.jar
ENTRYPOINT ["java",  "-jar","/pc-notifier-agent-websocket.jar"]
