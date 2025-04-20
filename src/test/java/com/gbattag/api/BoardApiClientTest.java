package com.gbattag.api;

import com.gbattag.api.dto.BoardDto;
import com.gbattag.api.dto.BoardDto.ObstacleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardApiClientTest {
    private static final String BOARD_API_URL = "https://domain/path/resource";

    private RestTemplate restTemplate;
    private BoardApiClient boardApiClient;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        boardApiClient = new BoardApiClient(BOARD_API_URL, restTemplate);
    }

    @Test
    public void shouldSuccessfullyReturnTheBoard() {
        BoardDto expectedBoardDto = new BoardDto(3, 2, List.of(new ObstacleDto(0, 0), new ObstacleDto(0, 1)));

        when(restTemplate.getForEntity(BOARD_API_URL, BoardDto.class))
                .thenReturn(new ResponseEntity<>(expectedBoardDto, HttpStatus.OK));

        final BoardDto boardDto = boardApiClient.getBoard();

        assertThat(boardDto).isEqualTo(expectedBoardDto);
    }

    @Test
    public void shouldThrowAnExceptionIfApiFails() {
        when(restTemplate.getForEntity(BOARD_API_URL, BoardDto.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThatThrownBy(() -> boardApiClient.getBoard()).isInstanceOf(Exception.class);
    }
}