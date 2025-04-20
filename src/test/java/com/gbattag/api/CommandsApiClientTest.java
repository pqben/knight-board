package com.gbattag.api;

import com.gbattag.api.dto.CommandsDto;
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
class CommandsApiClientTest {
    private static final String COMMANDS_API_URL = "https://domain/path/resource";

    private RestTemplate restTemplate;
    private CommandsApiClient commandsApiClient;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        commandsApiClient = new CommandsApiClient(COMMANDS_API_URL, restTemplate);
    }

    @Test
    public void shouldSuccessfullyReturnTheCommands() {
        CommandsDto expectedBoardDto = new CommandsDto(List.of("MOVE 4"));

        when(restTemplate.getForEntity(COMMANDS_API_URL, CommandsDto.class))
                .thenReturn(new ResponseEntity<>(expectedBoardDto, HttpStatus.OK));

        final CommandsDto commandsDto = commandsApiClient.getCommands();

        assertThat(commandsDto).isEqualTo(expectedBoardDto);
    }

    @Test
    public void shouldThrowAnExceptionIfApiFails() {
        when(restTemplate.getForEntity(COMMANDS_API_URL, CommandsDto.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThatThrownBy(() -> commandsApiClient.getCommands()).isInstanceOf(Exception.class);
    }
}