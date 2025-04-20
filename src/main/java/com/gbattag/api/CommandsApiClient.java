package com.gbattag.api;

import com.gbattag.api.dto.CommandsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommandsApiClient {
    private final RestTemplate restTemplate;
    private final String commandsApiUrl;

    public CommandsApiClient(@Value("${COMMANDS_API}") String commandsApiUrl, RestTemplate restTemplate) {
        this.commandsApiUrl = commandsApiUrl;
        this.restTemplate = restTemplate;
    }

    public CommandsDto getCommands() {
        ResponseEntity<CommandsDto> response = restTemplate.getForEntity(commandsApiUrl, CommandsDto.class);

        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            throw new RuntimeException("CommandsApi returned " + response.getStatusCode());
    }
}
