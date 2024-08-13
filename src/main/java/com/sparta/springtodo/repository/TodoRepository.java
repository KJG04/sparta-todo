package com.sparta.springtodo.repository;

import com.sparta.springtodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUpdateAtBetween(LocalDateTime start, LocalDateTime end);

    List<Todo> findByUser_Id(Long user_id);

    List<Todo> findByUpdateAtBetweenAndUser_Id(LocalDateTime start, LocalDateTime end, Long user_id);
}
