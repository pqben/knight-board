package com.gbattag.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gbattag.api.BoardApiClient;
import com.gbattag.api.CommandsApiClient;
import com.gbattag.api.dto.BoardDto;
import com.gbattag.api.dto.BoardDto.ObstacleDto;
import com.gbattag.api.dto.CommandsDto;
import com.gbattag.api.mappers.BoardMapper;
import com.gbattag.api.mappers.CommandMapper;
import com.gbattag.api.mappers.ResultMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KnightBoardServiceTest {
    private KnightBoardService knightBoardService;
    private BoardApiClient boardApiClient;
    private CommandsApiClient commandsApiClient;
    private CommandExecutorService commandExecutorService;
    private BoardMapper boardMapper;
    private CommandMapper commandMapper;
    private ResultMapper resultMapper;
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;

    @BeforeEach
    public void setup() {
        boardApiClient = mock(BoardApiClient.class);
        commandsApiClient = mock(CommandsApiClient.class);
        commandExecutorService = new CommandExecutorService();
        boardMapper = new BoardMapper();
        commandMapper = new CommandMapper();
        resultMapper = new ResultMapper();
        objectMapper = mock(ObjectMapper.class);
        objectWriter = mock(ObjectWriter.class);

        knightBoardService = new KnightBoardService(boardApiClient,
                commandsApiClient,
                commandExecutorService,
                boardMapper,
                commandMapper,
                resultMapper,
                objectMapper
        );
    }

    @Test
    public void shouldSuccessfullyReturnTheBoard() throws JsonProcessingException {
        final BoardDto boardDto = new BoardDto(3, 2, List.of(new ObstacleDto(1, 0), new ObstacleDto(1, 1)));
        final CommandsDto commandsDto = new CommandsDto(List.of("START 0,0,NORTH", "ROTATE EAST"));
        final String resultDto = """
                {
                    "position" : {
                        "x" : 0,
                        "y" : 0,
                        "direction" : "EAST"
                    },
                    "status" : "SUCCESS"
                }
                """;

        when(boardApiClient.getBoard()).thenReturn(boardDto);
        when(commandsApiClient.getCommands()).thenReturn(commandsDto);
        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(any())).thenReturn(resultDto);

        String resultDtoString = knightBoardService.downloadBoardDataAndExecuteCommands();

        assertThat(resultDtoString).isEqualTo(resultDto);
    }

    @Test
    public void shouldThrowExceptionWhenBoardApiFails() throws JsonProcessingException {
        when(boardApiClient.getBoard()).thenThrow(new ArrayIndexOutOfBoundsException("Test Exception!"));

        assertThatThrownBy(() -> knightBoardService.downloadBoardDataAndExecuteCommands())
                .isInstanceOf(RuntimeException.class);
    }
}