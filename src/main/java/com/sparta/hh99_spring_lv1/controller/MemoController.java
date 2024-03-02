package com.sparta.hh99_spring_lv1.controller;

import com.sparta.hh99_spring_lv1.dto.MemoRequestDto;
import com.sparta.hh99_spring_lv1.dto.MemoResponseDto;
import com.sparta.hh99_spring_lv1.entity.Memo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/record")
public class MemoController {

    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/create")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO record (writer, password, title, writedDate, contents) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, memo.getWriter());
                    preparedStatement.setString(2, memo.getPassword());
                    preparedStatement.setString(3, memo.getTitle());
                    preparedStatement.setString(4, memo.getWritedDate());
                    preparedStatement.setString(5, memo.getContents());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long recordId= keyHolder.getKey().longValue();
        memo.setRecordId(recordId);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/read/all")
    public List<MemoResponseDto> getMemos() {
        // DB 조회
        String sql = "SELECT * FROM record ORDER BY writedDate DESC";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long recordId = rs.getLong("recordId");
                String writer = rs.getString("writer");
                String password = rs.getString("password");
                String title = rs.getString("title");
                String writedDate = rs.getString("writedDate");
                String contents = rs.getString("contents");
                return new MemoResponseDto(recordId, writer, password, title, writedDate, contents);
            }
        });
    }

    @PutMapping("/update/{recordId}")
    public Long updateMemo(@PathVariable Long recordId, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(recordId);
        if(memo != null) {
            // memo 내용 수정
            String sql = "UPDATE record SET writer = ?, password = ?, title = ?, writedDate = ?, contents = ? WHERE recordId = ?";
            jdbcTemplate.update(sql, requestDto.getWriter(), requestDto.getPassword(), requestDto.getTitle(), requestDto.getWritedDate(), requestDto.getContents(), recordId);

            return recordId;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/delete/{recordId}")
    public Long deleteMemo(@PathVariable Long recordId) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findById(recordId);
        if(memo != null) {
            // memo 삭제
            String sql = "DELETE FROM record WHERE recordId = ?";
            jdbcTemplate.update(sql, recordId);

            return recordId;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    private Memo findById(Long recordId) {
        // DB 조회
        String sql = "SELECT * FROM record WHERE recordId = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Memo memo = new Memo();
                memo.setWriter(resultSet.getString("writer"));
                memo.setPassword(resultSet.getString("password"));
                memo.setTitle(resultSet.getString("title"));
                memo.setWritedDate(resultSet.getString("writedDate"));
                memo.setContents(resultSet.getString("contents"));
                return memo;
            } else {
                return null;
            }
        }, recordId);
    }
}