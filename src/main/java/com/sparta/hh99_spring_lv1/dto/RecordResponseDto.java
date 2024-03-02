package com.sparta.hh99_spring_lv1.dto;

import com.sparta.hh99_spring_lv1.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecordResponseDto {
    private Long recordId;
    private String writer;
    private String password;
    private String title;
    private String writedDate;
    private String contents;

    public RecordResponseDto(Record memo) {
        this.recordId = memo.getRecordId();
        this.writer = memo.getWriter();
        this.password = memo.getPassword();
        this.title = memo.getTitle();
        this.writedDate = memo.getWritedDate();
        this.contents = memo.getContents();
    }
}