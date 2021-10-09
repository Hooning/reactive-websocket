package com.hoontime.reactivewebsocket.repository;

import com.hoontime.reactivewebsocket.entity.ChatMessage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessage, UUID> {
}
