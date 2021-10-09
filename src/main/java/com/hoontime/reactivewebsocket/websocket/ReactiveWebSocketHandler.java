package com.hoontime.reactivewebsocket.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoontime.reactivewebsocket.entity.Message;
import com.hoontime.reactivewebsocket.service.ChatMessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

@Component("ReactiveWebSocketHandler")
class ReactiveWebSocketHandler implements WebSocketHandler {

    private ChatMessageService chatMessageService;

    public ReactiveWebSocketHandler(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    private static final ObjectMapper json = new ObjectMapper();

    private Flux<String> messageFlux = Flux.generate(sink -> {
        Message message = new Message(randomUUID().toString(), now().toString(), "Hi");
        try {
            sink.next(json.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            sink.error(e);
        }
    });

//    Flux<String> listenToAllMessages = Flux.generate(sink -> {
//        chatMessageService.findAll()
//                .collectList().map(messages -> {
//                    try {
//                        Message message = new Message(randomUUID().toString(), now().toString(), json.writeValueAsString(messages));
//                        sink.next(json.writeValueAsString(message));
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//                    return "";
//                });
//    });

    private final Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(1000L))
            .zipWith(messageFlux, (time, event) -> event);

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        System.out.println("webSocketSession: " + webSocketSession);
        return webSocketSession.send(intervalFlux
                .map(webSocketSession::textMessage))
                .and(webSocketSession.receive()
                        .map(WebSocketMessage::getPayloadAsText).log());
    }

}
