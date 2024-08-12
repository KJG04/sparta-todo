package com.sparta.springtodo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @GetMapping("")
    public void getTodos(@RequestParam(required = false) String updateAt, @RequestParam(required = false) Long userId) {
    }

    @PostMapping("")
    public void create() {
    }

    @GetMapping("/{todoId}")
    public void getTodoById(@PathVariable String todoId) {
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodoById(@RequestHeader("password") String password, @PathVariable String todoId) {
    }

    @PutMapping("{todoId}")
    public void updateTodo(@PathVariable String todoId) {
    }
}
