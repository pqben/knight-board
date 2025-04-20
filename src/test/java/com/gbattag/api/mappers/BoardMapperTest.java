package com.gbattag.api.mappers;

import com.gbattag.api.dto.BoardDto;
import com.gbattag.api.dto.BoardDto.ObstacleDto;
import com.gbattag.model.Board;
import com.gbattag.model.BoardPosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoardMapperTest {
    private final BoardMapper boardMapper = new BoardMapper();

    @Test
    public void shouldMapSuccessfullyBoardDto() {
        final Board board = boardMapper.fromBoardDto(
                new BoardDto(
                        4,
                        3,
                        List.of(new ObstacleDto(1, 2),
                                new ObstacleDto(3, 1)
                        )
                )
        );

        assertThat(board.getRows()).isEqualTo(3);
        assertThat(board.getColumns()).isEqualTo(4);
        assertThat(board.isEmptyPosition(new BoardPosition(0, 0))).isTrue();
        assertThat(board.isEmptyPosition(new BoardPosition(2, 1))).isFalse();
        assertThat(board.isEmptyPosition(new BoardPosition(1, 3))).isFalse();
    }
}