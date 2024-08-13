package com.sparta.springtodo.mapper;

import com.sparta.springtodo.dto.TodoResponseDto;
import com.sparta.springtodo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoResponseDto toTodoResponseDto(Todo todo);
}
