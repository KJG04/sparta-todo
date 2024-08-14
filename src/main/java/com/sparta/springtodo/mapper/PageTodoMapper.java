package com.sparta.springtodo.mapper;

import com.sparta.springtodo.dto.PageResponseDto;
import com.sparta.springtodo.dto.TodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface PageTodoMapper {
    PageTodoMapper INSTANCE = Mappers.getMapper(PageTodoMapper.class);

    PageResponseDto<TodoResponseDto> toPageResponseDto(Page<TodoResponseDto> responseDtos);
}
