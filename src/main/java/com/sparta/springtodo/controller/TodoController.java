package com.sparta.springtodo.controller;

import com.sparta.springtodo.dto.CreateTodoRequestDto;
import com.sparta.springtodo.dto.TodoResponseDto;
import com.sparta.springtodo.entity.Todo;
import com.sparta.springtodo.exception.BadRequestException;
import com.sparta.springtodo.mapper.TodoMapper;
import com.sparta.springtodo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto>> getTodos(@RequestParam(required = false) String updateAt, @RequestParam(required = false) Long userId) {
        Optional<String> optionalUpdateAt = Optional.ofNullable(updateAt);
        Optional<Long> optionalUserId = Optional.ofNullable(userId);

        List<Todo> todos;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (optionalUpdateAt.isPresent() && optionalUserId.isPresent()) {
                LocalDate localDate = LocalDate.parse(optionalUpdateAt.get(), dateTimeFormatter);
                todos = this.todoService.getTodosByUpdateAtAndUserId(LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime()), optionalUserId.get());
            } else if (optionalUpdateAt.isPresent()) {
                LocalDate localDate = LocalDate.parse(optionalUpdateAt.get(), dateTimeFormatter);
                todos = this.todoService.getTodosByUpdateAt(LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime()));
            } else if (optionalUserId.isPresent()) {
                todos = this.todoService.getTodosByUserId(optionalUserId.get());
            } else {
                todos = this.todoService.getTodos();
            }
        } catch (DateTimeParseException e) {
            // 날짜 parse에 실패하면 BadRequest 생성
            throw new BadRequestException();
        }

        List<TodoResponseDto> todoResponseDtos = todos.stream().map(TodoMapper.INSTANCE::toTodoResponseDto).toList();
        return ResponseEntity.ok(todoResponseDtos);
    }

    @PostMapping("")
    public ResponseEntity<TodoResponseDto> create(@RequestBody CreateTodoRequestDto createTodoRequestDto) {
        Todo todo = this.todoService.createTodo(createTodoRequestDto.getContent(), createTodoRequestDto.getPassword(), createTodoRequestDto.getUserId());
        TodoResponseDto todoResponseDto = TodoMapper.INSTANCE.toTodoResponseDto(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoResponseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long todoId) {
        Todo todo = this.todoService.getTodoById(todoId);
        TodoResponseDto todoResponseDto = TodoMapper.INSTANCE.toTodoResponseDto(todo);
        return ResponseEntity.ok(todoResponseDto);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity deleteTodoById(@PathVariable Long todoId, @RequestHeader("password") String password) {
        this.todoService.deleteTodo(todoId, password);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{todoId}")
    public void updateTodo(@PathVariable String todoId, @RequestHeader("password") String password, @RequestBody UpdateTodoRequestDto updateTodoRequestDto) {
    }
}
