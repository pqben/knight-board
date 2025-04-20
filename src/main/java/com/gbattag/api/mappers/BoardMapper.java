package com.gbattag.api.mappers;

import com.gbattag.api.dto.BoardDto;
import com.gbattag.model.Board;
import com.gbattag.model.BoardPosition;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BoardMapper {
    public Board fromBoardDto(BoardDto boardDto) {
        return new Board(boardDto.getHeight(), boardDto.getWidth(), fromPositionsDto(boardDto.getObstacles()));
    }

    private Collection<BoardPosition> fromPositionsDto(List<BoardDto.ObstacleDto> obstacles) {
        return obstacles.stream()
                .map(obstacleDto -> new BoardPosition(obstacleDto.getY(), obstacleDto.getX()))
                .toList();
    }
}
