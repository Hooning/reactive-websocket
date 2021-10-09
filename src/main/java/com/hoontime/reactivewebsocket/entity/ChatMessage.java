package com.hoontime.reactivewebsocket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("chat_message")
public class ChatMessage implements Persistable<String> {

    @Id
    @Column(value = "id")
    private String id = UUID.randomUUID().toString();

    @Column(value = "created_at")
    private LocalDate createdAt;

    @Column(value = "message")
    private String message;

    @Transient
    @JsonIgnore
    private boolean isNew;

    public ChatMessage(String message, boolean isNew) {
        this.message = message;
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", message='" + message + '\'' +
                ", isNew=" + isNew +
                '}';
    }

}
