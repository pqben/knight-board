package com.gbattag.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbattag.api.BoardApiClient;
import com.gbattag.api.CommandsApiClient;
import com.gbattag.api.dto.BoardDto;
import com.gbattag.api.dto.CommandsDto;
import com.gbattag.api.dto.ResultDto;
import com.gbattag.api.mappers.BoardMapper;
import com.gbattag.api.mappers.CommandMapper;
import com.gbattag.api.mappers.ResultMapper;
import com.gbattag.model.Board;
import com.gbattag.model.Command;
import com.gbattag.model.KnightStatusAndCommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KnightBoardService {
    private static final Logger logger = LoggerFactory.getLogger(KnightBoardService.class);

    private final BoardApiClient boardApiClient;

    private final CommandsApiClient commandsApiClient;

    private final CommandExecutorService commandExecutorService;

    private final BoardMapper boardMapper;

    private final CommandMapper commandMapper;

    private final ResultMapper resultMapper;

    private final ObjectMapper objectMapper;

    public KnightBoardService(BoardApiClient boardApiClient,
                              CommandsApiClient commandsApiClient,
                              CommandExecutorService commandExecutorService,
                              BoardMapper boardMapper,
                              CommandMapper commandMapper,
                              ResultMapper resultMapper,
                              ObjectMapper objectMapper) {
        this.boardApiClient = boardApiClient;
        this.commandsApiClient = commandsApiClient;
        this.commandExecutorService = commandExecutorService;
        this.boardMapper = boardMapper;
        this.commandMapper = commandMapper;
        this.resultMapper = resultMapper;
        this.objectMapper = objectMapper;
    }

    public String downloadBoardDataAndExecuteCommands() {
        try {
            final BoardDto boardDto = boardApiClient.getBoard();
            final CommandsDto commandsDto = commandsApiClient.getCommands();

            final Board board = boardMapper.fromBoardDto(boardDto);
            final Collection<Command> commands = commandMapper.fromCommandsDto(commandsDto);

            final KnightStatusAndCommandResult result = commandExecutorService.executeCommands(board, commands);

            final ResultDto resultDto = resultMapper.fromCommandResult(result);

            return toJson(resultDto);
        } catch (Exception e) {
            logger.error("Unexpected error while consuming the APIs or executing the commands", e);
            throw new RuntimeException("Unexpected error while consuming the APIs or executing the commands", e);
        }
    }

    private String toJson(ResultDto resultDto) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
