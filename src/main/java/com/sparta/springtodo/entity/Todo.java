package com.sparta.springtodo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    User user;

    @Column
    LocalDateTime createAt;

    @Column
    LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @Builder
    public Todo(String content, User user) {
        this.content = content;
        this.user = user;
    }
}
