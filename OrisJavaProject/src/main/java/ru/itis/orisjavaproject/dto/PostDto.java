package ru.itis.orisjavaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private UUID userId;

}