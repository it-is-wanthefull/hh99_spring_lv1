package com.sparta.hh99_spring_lv1.entity;

import com.sparta.hh99_spring_lv1.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long recordId;
    private String writer;
    private String password;
    private String title;
    private String writedDate;
    private String contents;

    public Memo(MemoRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.writedDate = requestDto.getWritedDate();
        this.contents = requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto) {
        this.writer = requestDto.getWriter();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.writedDate = requestDto.getWritedDate();
        this.contents = requestDto.getContents();
    }
}