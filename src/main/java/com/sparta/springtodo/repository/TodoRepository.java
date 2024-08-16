package com.sparta.springtodo.repository;

import com.sparta.springtodo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Page<Todo> findPageByUpdateAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Todo> findPageByUser_Id(Long user_id, Pageable pageable);

    Page<Todo> findPageByUpdateAtBetweenAndUser_Id(LocalDateTime start, LocalDateTime end, Long user_id, Pageable pageable);

    Optional<Todo> findByIdAndPassword(Long id, String password);
}
