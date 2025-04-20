package com.gbattag.api;

import com.gbattag.api.dto.BoardDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BoardApiClient {
    private final RestTemplate restTemplate;
    private final String boardApiUrl;

    public BoardApiClient(@Value("${BOARD_API}") String boardApiUrl, RestTemplate restTemplate) {
        this.boardApiUrl = boardApiUrl;
        this.restTemplate = restTemplate;
    }

    public BoardDto getBoard() {
        ResponseEntity<BoardDto> response = restTemplate.getForEntity(boardApiUrl, BoardDto.class);

        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            throw new RuntimeException("BoardApi returned " + response.getStatusCode());
    }
}
