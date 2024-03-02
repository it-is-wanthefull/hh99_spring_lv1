package com.sparta.hh99_spring_lv1.dto;

import lombok.Getter;

@Getter
public class RecordRequestDto {
    private String writer;
    private String password;
    private String title;
    private String writedDate;
    private String contents;
}