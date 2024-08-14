package com.sparta.springtodo.controller;

import com.sparta.springtodo.dto.CreateTodoRequestDto;
import com.sparta.springtodo.dto.PageResponseDto;
import com.sparta.springtodo.dto.TodoResponseDto;
import com.sparta.springtodo.dto.UpdateTodoRequestDto;
import com.sparta.springtodo.entity.Todo;
import com.sparta.springtodo.mapper.PageTodoMapper;
import com.sparta.springtodo.mapper.TodoMapper;
import com.sparta.springtodo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<PageResponseDto<TodoResponseDto>> getTodos(@RequestParam(required = false) String updateAt, @RequestParam(required = false) Long userId, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        Integer iPage = Optional.ofNullable(page).orElse(0);
        Integer iSize = Optional.ofNullable(size).orElse(20);
        Optional<String> optionalUpdateAt = Optional.ofNullable(updateAt);
        Optional<Long> optionalUserId = Optional.ofNullable(userId);

        PageRequest pageRequest = PageRequest.of(iPage, iSize, Sort.Direction.DESC, "updateAt");

        Page<Todo> todos;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (optionalUpdateAt.isPresent() && optionalUserId.isPresent()) {
                LocalDate localDate = LocalDate.parse(optionalUpdateAt.get(), dateTimeFormatter);
                todos = this.todoService.getTodosByUpdateAtAndUserId(LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime()), optionalUserId.get(), pageRequest);
            } else if (optionalUpdateAt.isPresent()) {
                LocalDate localDate = LocalDate.parse(optionalUpdateAt.get(), dateTimeFormatter);
                todos = this.todoService.getTodosByUpdateAt(LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime()), pageRequest);
            } else if (optionalUserId.isPresent()) {
                todos = this.todoService.getTodosByUserId(optionalUserId.get(), pageRequest);
            } else {
                todos = this.todoService.getTodos(pageRequest);
            }
        } catch (DateTimeParseException e) {
            // 날짜 parse에 실패하면 BadRequest 생성
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "updateAt이 잘못된 형식입니다.");
        }
        Page<TodoResponseDto> todoResponseDtos = todos.map(TodoMapper.INSTANCE::toTodoResponseDto);
        PageResponseDto<TodoResponseDto> pageResponseDto = PageTodoMapper.INSTANCE.toPageResponseDto(todoResponseDtos);
        return ResponseEntity.ok(pageResponseDto);
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
