package com.sparta.springtodo.service;

import com.sparta.springtodo.entity.Todo;
import com.sparta.springtodo.entity.User;
import com.sparta.springtodo.repository.TodoRepository;
import com.sparta.springtodo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public Todo getTodoById(Long todoId) throws ResponseStatusException {
        return this.todoRepository.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 할 일을 찾을 수 없습니다."));
    }

    public Page<Todo> getTodos(Pageable pageable) {
        return this.todoRepository.findAll(pageable);
    }

    public Page<Todo> getTodosByUpdateAt(LocalDateTime updateAt, Pageable pageable) {
        return this.todoRepository.findPageByUpdateAtBetween(updateAt.toLocalDate().atStartOfDay(), updateAt.toLocalDate().plusDays(1).atStartOfDay(), pageable);
    }

    public Page<Todo> getTodosByUserId(Long userId, Pageable pageable) {
        return this.todoRepository.findPageByUser_Id(userId, pageable);
    }

    public Page<Todo> getTodosByUpdateAtAndUserId(LocalDateTime updateAt, Long userId, Pageable pageable) {
        return this.todoRepository.findPageByUpdateAtBetweenAndUser_Id(updateAt.toLocalDate().atStartOfDay(), updateAt.toLocalDate().plusDays(1).atStartOfDay(), userId, pageable);
    }

    public Todo createTodo(String content, String password, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "해당하는 유저를 찾을 수 업습니다."));
        Todo todo = new Todo(content, password, user);

        return this.todoRepository.save(todo);
    }

    public void deleteTodo(Long todoId, String password) {
        Todo todo = this.todoRepository.findByIdAndPassword(todoId, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 할 일을 찾을 수 없습니다."));
        this.todoRepository.delete(todo);
    }

    public Todo updateTodo(Long todoId, String password, String content, Long _userId) {
        Todo todo = this.todoRepository.findByIdAndPassword(todoId, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 할 일을 찾을 수 없습니다."));
        User user = todo.getUser();
        Optional<Long> optionalUserId = Optional.ofNullable(_userId);

        if (optionalUserId.isPresent()) {
            Long userId = optionalUserId.get();
            user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."));
        }

        todo.setContent(Optional.ofNullable(content).orElse(todo.getContent()));
        todo.setUser(user);

        return this.todoRepository.save(todo);
    }
}
