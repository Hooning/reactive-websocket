package com.hoontime.reactivewebsocket.controller;

import com.hoontime.reactivewebsocket.entity.ChatMessage;
import com.hoontime.reactivewebsocket.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService service;

    public ChatMessageController(ChatMessageService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<List<ChatMessage>>> getMeasurementList() {
        Flux<ChatMessage> chatMessageFlux =  service.findAll();
        return chatMessageFlux
                .collectList()
                .map(messages -> ResponseEntity.status(HttpStatus.OK).body(messages))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
    }

    @PostMapping
    public Mono<ResponseEntity> createMessage(@RequestBody ChatMessage message) {
        Mono<ChatMessage> chatMessageMono =  service.createMessage(message);

        return chatMessageMono
                .map(chatMessage -> {
                    if (chatMessage != null) {
                        return ResponseEntity.status(HttpStatus.OK).body(chatMessage);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
                    }
                });
    }

}
