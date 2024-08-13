package com.sparta.springtodo.service;

import com.sparta.springtodo.entity.Todo;
import com.sparta.springtodo.entity.User;
import com.sparta.springtodo.exception.NotFoundException;
import com.sparta.springtodo.exception.UnprocessableEntityException;
import com.sparta.springtodo.repository.TodoRepository;
import com.sparta.springtodo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public Todo getTodoById(Long todoId) throws NotFoundException {
        return this.todoRepository.findById(todoId).orElseThrow(NotFoundException::new);
    }

    public List<Todo> getTodos() {
        return this.todoRepository.findAll();
    }

    public List<Todo> getTodosByUpdateAt(LocalDateTime updateAt) {
        return this.todoRepository.findByUpdateAtBetween(updateAt.toLocalDate().atStartOfDay(), updateAt.toLocalDate().plusDays(1).atStartOfDay());
    }

    public List<Todo> getTodosByUserId(Long userId) {
        return this.todoRepository.findByUser_Id(userId);
    }

    public List<Todo> getTodosByUpdateAtAndUserId(LocalDateTime updateAt, Long userId) {
        return this.todoRepository.findByUpdateAtBetweenAndUser_Id(updateAt.toLocalDate().atStartOfDay(), updateAt.toLocalDate().plusDays(1).atStartOfDay(), userId);
    }

    public Todo createTodo(String content, String password, Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UnprocessableEntityException::new);
        Todo todo = new Todo(content, password, user);

        return this.todoRepository.save(todo);
    }
}
