package com.gbattag.api.dto;

import java.util.List;

public class CommandsDto {
    private List<String> commands;

    public CommandsDto() {
    }

    public CommandsDto(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
