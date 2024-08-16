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

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 200)
    String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    User user;

    @Column(nullable = false)
    LocalDateTime createAt;

    @Column(nullable = false)
    LocalDateTime updateAt;

    @Builder
    public Todo(String content, String password, User user) {
        this.content = content;
        this.password = password;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
