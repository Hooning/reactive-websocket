package com.hoontime.reactivewebsocket.service;

import com.hoontime.reactivewebsocket.entity.ChatMessage;
import com.hoontime.reactivewebsocket.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ChatMessageService {

    private final ChatMessageRepository repository;

    public ChatMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public Flux<ChatMessage> findAll() {
        return repository.findAll();
    }

    public Mono<ChatMessage> createMessage(ChatMessage message) {
        message.setCreatedAt(LocalDate.now());
        message.setNew(true);
        return repository.save(message);
    }

}
